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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class CubicBezierArcTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCalculationHor179() {

        //Given
        float angle = 179.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, 0f,0f,100f,0f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(0.5,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(-66,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(99.4,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(-66,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(0.5,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(66,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(99.4,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(66,0.1)));
    }

    @Test
    public void testCalculationVert60() {

        //Given
        float angle = 60.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, 0f,0f,0f,100f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(17.8,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(30.9,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(17.8,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(69.0,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(-17.8,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(30.9,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(-17.8,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(69.0,0.1)));

    }

    @Test
    public void testCalculation90_2() {
        //Given
        float angle = 90.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, 50f,50f,150f,150f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(105.2,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(50.0,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(150.0,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(94.7,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(50.0,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(105.2,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(94.7,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(150.0,0.1)));
    }


    @Test
    public void testCalculation45Line_2() {
        //Given
        float angle = 45.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, 61.3f,70.4f,-81f,129.2f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(23.5,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(108.0,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(-27.6,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(129.2,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(7.9,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(70.3,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(-43.2,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(91.5,0.1)));

    }

    @Test
    public void testCalculation90() {
        //Given
        float angle = 90.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, -61.3f,-70.4f,-181f,-100.2f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(-102.5,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(-45.5,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(-156.1,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(-58.9,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(-86.1,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(-111.6,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(-139.7,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(-125.0,0.1)));
    }

    @Test
    public void testCalculation90SmallLine() {
        //Given
        float angle = 90.0f;

        //When
        CubicBezierArc cubicBezierArc = new CubicBezierArc(angle, 0f,0f,5f,5f);

        //Then
        assertThat((double)cubicBezierArc.getControlPoint1().x,is(closeTo(2.7,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint1().y,is(closeTo(0.0,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().x,is(closeTo(5.0 ,0.1)));
        assertThat((double)cubicBezierArc.getControlPoint2().y,is(closeTo(2.2,0.1)));

        assertThat((double)cubicBezierArc.getReflectedControlPoint1().x,is(closeTo(0.0,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint1().y,is(closeTo(2.7,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().x,is(closeTo(2.2,0.1)));
        assertThat((double)cubicBezierArc.getReflectedControlPoint2().y,is(closeTo(5.0,0.1)));

    }

    @Test
    public void testPointsSame() {
        float angle = 90.0f;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Start and end points cannot be the same");
        new CubicBezierArc(angle, 1f,1f, 1f,1f);
    }

    @Test
    public void testAngleTooSmall() {
        float angle = 0.0f;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Arc angle must be between 1 and 179 degrees");
        new CubicBezierArc(angle, 1f,1f, 100f,100f);
    }

    @Test
    public void testAngleTooLarge() {
        float angle = 180.0f;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Arc angle must be between 1 and 179 degrees");
        new CubicBezierArc(angle, 0f,0f,100f,100f);
    }

}
