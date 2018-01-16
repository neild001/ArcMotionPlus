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
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oeri.arcmotionplus.sample.R;

public class SceneExampleActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private Scene scene1;
    private Scene scene2;
    private Scene scene3;

    private TransitionSet transitionSet;
    private ArcMotionPlus arcMotionPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.select_scene);
        radioGroup.setOnCheckedChangeListener(this);

        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_2, this);
        scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_3, this);

        transitionSet = (TransitionSet) TransitionInflater.from(this).inflateTransition(R.transition.change_bounds);

        //Get the ArcMotionPlus defined in the transition XML
        arcMotionPlus = (ArcMotionPlus) transitionSet.getTransitionAt(0).getPathMotion();

        final TextView angleText = (TextView) findViewById(R.id.angleText);
        CheckBox reflectedPath = (CheckBox) findViewById(R.id.reflected);

        reflectedPath.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                arcMotionPlus.setReflectedArc(isChecked);
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
                angleText.setText(String.valueOf(progress + 1));
                arcMotionPlus.setArcAngle(progress + 1);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.select_scene_1: {
                TransitionManager.go(scene1,transitionSet);
                break;
            }
            case R.id.select_scene_2: {
                TransitionManager.go(scene2,transitionSet);
                break;
            }
            case R.id.select_scene_3: {
                TransitionManager.go(scene3,transitionSet);
                break;
            }
        }
    }
}
