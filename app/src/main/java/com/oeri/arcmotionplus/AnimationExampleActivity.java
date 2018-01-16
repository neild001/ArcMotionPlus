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

package com.oeri.arcmotionplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oeri.arcmotionplus.sample.R;
import com.oeri.arcmotionplus.views.AnimationWidget;

public class AnimationExampleActivity extends AppCompatActivity {

    private AnimationWidget animationWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView angleText = (TextView) findViewById(R.id.angleText);
        animationWidget = (AnimationWidget) findViewById(R.id.animationWidget);

        CheckBox reflectedPath = (CheckBox) findViewById(R.id.reflected);
        reflectedPath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                animationWidget.setUseReflectedPaths(isChecked);
            }
        });

        SeekBar arcAngle =(SeekBar) findViewById(R.id.arcAngle);
        arcAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar view, int progress, boolean fromUser) {
                animationWidget.setArcAngle(progress + 1);
                angleText.setText(String.valueOf(progress + 1));
            }
        });

    }

    public void clickAnimate(View v) {
        animationWidget.startAnimation();
    }

}
