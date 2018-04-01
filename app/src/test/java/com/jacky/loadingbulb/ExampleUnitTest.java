package com.jacky.loadingbulb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void math_sin() {
        float x = (float) Math.sin(30*1.0f / (180.0 * Math.PI));
        System.out.println(x);

         x = (float) Math.toDegrees(Math.PI);
        System.out.println(x);

        x = (float) Math.toRadians(30);
        System.out.println(x);

        x = (float) Math.atan(1);
        System.out.println(Math.toDegrees(x));

    }

}