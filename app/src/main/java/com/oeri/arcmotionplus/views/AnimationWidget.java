/*
 * Copyright (C) 2016 Neil Davies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.oeri.arcmotionplus.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.oeri.arcmotionplus.CubicBezierArc;


public class AnimationWidget extends View  {

    private int width;
    private int height;
    private int x1 = 150, y1 = 400;
    private int x2 = 300, y2 = 100;
    private int ballX, ballY;
    private int frameStrokeWidth = 4;
    private final Paint circlePaint = new Paint();
    private final Paint animatedCirclePaint = new Paint();
    private final Paint framePaint = new Paint();
    private final Paint curvePaint = new Paint();
    private final Paint linePaint = new Paint();
    private RectF frame = new RectF();

    private int touchCircleRadius = 10;
    private int animatedCircleRadius = 6;
    private int translateOffset = 40;

    private float arcAngle = 60.0f;
    private Path path = new Path();
    private Path reflectedPath = new Path();
    private Path reversePath = new Path();
    private Path reflectedReversePath = new Path();
    private boolean useRefletctedPaths = false;


    private CubicBezierArc cubicBezierArc;
    private ObjectAnimator animator;

    private enum AnimationState {
        START,
        END
    }
    private AnimationState state = AnimationState.START;
    private boolean animating = false;

    public AnimationWidget(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AnimationWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AnimationWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    private void init(Context context, AttributeSet attrs, int defStyle) {
        final Resources res = getResources();
        float density = res.getDisplayMetrics().density;
        frameStrokeWidth = (int) (frameStrokeWidth * density);
        touchCircleRadius = (int) (touchCircleRadius * density);
        animatedCircleRadius =(int) (animatedCircleRadius * density);

        @SuppressWarnings("deprecation")
        int circleColor = res.getColor(android.R.color.darker_gray);
        @SuppressWarnings("deprecation")
        int animatedCircleColor = res.getColor(com.oeri.arcmotionplus.sample.R.color.colorAccent);
        @SuppressWarnings("deprecation")
        int frameColor = res.getColor(android.R.color.black);
        @SuppressWarnings("deprecation")
        int lineColor = res.getColor(android.R.color.black);

        framePaint.setColor(frameColor);
        framePaint.setAntiAlias(true);
        framePaint.setStyle(Paint.Style.STROKE);

        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);

        animatedCirclePaint.setColor(animatedCircleColor);
        animatedCirclePaint.setAntiAlias(true);

        curvePaint.setColor(lineColor);
        curvePaint.setAntiAlias(true);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth() - translateOffset *2 - frameStrokeWidth;
        height = getHeight() - translateOffset *2 - frameStrokeWidth;
        frame.set(frameStrokeWidth /2, frameStrokeWidth /2, getWidth() - frameStrokeWidth /2, getHeight()- frameStrokeWidth /2);
        canvas.drawRoundRect(frame, 30,30, framePaint);

        //Draggable circles to set start and end points
        canvas.drawCircle(x1, y1, touchCircleRadius, circlePaint);
        canvas.drawCircle(x2, y2, touchCircleRadius, circlePaint);

        //Draw circle to animated between start and end points
        canvas.drawCircle(ballX, ballY, animatedCircleRadius, animatedCirclePaint);

        //Line between start and end points
        canvas.drawLine(x1, y1, x2, y2, linePaint);

        //Arcs between start and end points
        canvas.drawPath(path, curvePaint);
        canvas.drawPath(reflectedPath, curvePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        //Some rough calculations to get decent start and end positions.
        y1 = height - 150;
        x1 = 150;
        x2 = width - 150;
        y2 = 150;

        ballX = x1;
        ballY = y1;
        calculatePaths();
    }

    private void updateOnTouch(MotionEvent event) {
        if (animating) {
            return;
        }
        int xPos = (int) event.getX();
        int yPos = (int) event.getY();

        calculatePaths();

        //Are we touching the first control point?
        if (Math.pow((xPos - x1),2) +  Math.pow((yPos - y1),2) < Math.pow(touchCircleRadius +60,2)) {
            if((xPos > 0) && (xPos <= width)) {
                x1 = (int) event.getX();
                invalidate();
            }
            if (yPos > 0 && yPos <= height) {
                y1 = (int) event.getY();
                invalidate();
            }

            if(state == AnimationState.START) {
                ballX = x1;
                ballY = y1;
            }

            return;
        }

        //Are we touch the second control point?
        if (Math.pow((xPos - x2),2) +  Math.pow((yPos - y2),2) < Math.pow(touchCircleRadius +60 ,2)) {
            if((xPos > 0) && (xPos <= width)) {
                x2 = (int) event.getX();
                invalidate();
            }

            if (yPos > 0 && yPos <= height) {
                y2 = (int) event.getY();
                invalidate();
            }

            if(state == AnimationState.END) {
                ballX = x2;
                ballY = y2;
            }
        }
    }

    private void calculatePaths() {
        //The forward path goes from start to end.
        cubicBezierArc = new CubicBezierArc(arcAngle, x1, y1, x2, y2);
        path.reset();
        path.moveTo(x1, y1);
        path.cubicTo(cubicBezierArc.getControlPoint1().x, cubicBezierArc.getControlPoint1().y,
                cubicBezierArc.getControlPoint2().x, cubicBezierArc.getControlPoint2().y,
                x2, y2);

        reflectedPath.reset();
        reflectedPath.moveTo(x1, y1);
        reflectedPath.cubicTo(cubicBezierArc.getReflectedControlPoint1().x, cubicBezierArc.getReflectedControlPoint1().y,
                cubicBezierArc.getReflectedControlPoint2().x, cubicBezierArc.getReflectedControlPoint2().y,
                x2, y2);

    }

    private void calculateReversePaths() {
        //The Reverse path goes from the end point to the start point.
        cubicBezierArc = new CubicBezierArc(arcAngle, x2, y2, x1, y1);
        reversePath.reset();
        reversePath.moveTo(x2, y2);
        reversePath.cubicTo(cubicBezierArc.getControlPoint1().x, cubicBezierArc.getControlPoint1().y,
                cubicBezierArc.getControlPoint2().x, cubicBezierArc.getControlPoint2().y,
                x1, y1);

        reflectedReversePath.reset();
        reflectedReversePath.moveTo(x2, y2);
        reflectedReversePath.cubicTo(cubicBezierArc.getReflectedControlPoint1().x, cubicBezierArc.getReflectedControlPoint1().y,
                cubicBezierArc.getReflectedControlPoint2().x, cubicBezierArc.getReflectedControlPoint2().y,
                x1, y1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateOnTouch(event);
                break;
            case MotionEvent.ACTION_MOVE:
                updateOnTouch(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        calculatePaths();
        invalidate();
    }

    public void setUseReflectedPaths(boolean useReflectedPaths) {
        this.useRefletctedPaths = useReflectedPaths;
    }

    /*
     * Method used by ObjectAnimator to animator over path
     */
    @SuppressWarnings("unused")
    public void setBallPosition(int x, int y) {
        ballX = x;
        ballY = y;
    }

    private void createAnimation() {
        if (state == AnimationState.START) {
            if(!useRefletctedPaths) {
                animator = ObjectAnimator.ofMultiInt(this, "ballPosition", path).setDuration(1000);
            } else {
                animator = ObjectAnimator.ofMultiInt(this, "ballPosition", reflectedPath).setDuration(1000);
            }
        } else {
            calculateReversePaths();
            if(!useRefletctedPaths) {
                animator = ObjectAnimator.ofMultiInt(this, "ballPosition", reversePath).setDuration(1000);
            } else {
                animator = ObjectAnimator.ofMultiInt(this, "ballPosition", reflectedReversePath).setDuration(1000);
            }
        }
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (state == AnimationState.START) {
                    state = AnimationState.END;
                } else {
                    state = AnimationState.START;
                }
                animating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void startAnimation() {
        createAnimation();
        animator.start();
    }

}


