package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;


import static org.junit.Assert.*;

/**
 * Created by Peter on 2017/12/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginTest {
    @Test
    public void test1(){
        Activity activity = Robolectric.setupActivity(Login.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        Button signup = (Button) activity.findViewById(R.id.signupbtn);

        signup.performClick();
        Intent expectedIntent = new Intent(activity, Message.class);
        Intent realIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(expectedIntent.getComponent(), realIntent.getComponent());
    }
    @Test
    public void test2(){
        Activity activity = Robolectric.setupActivity(Login.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        Button skip = (Button) activity.findViewById(R.id.signinlater);

        skip.performClick();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent realIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(expectedIntent.getComponent(), realIntent.getComponent());
    }
}