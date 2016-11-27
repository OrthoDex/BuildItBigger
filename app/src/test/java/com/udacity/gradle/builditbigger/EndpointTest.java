package com.udacity.gradle.builditbigger;

import android.content.Intent;

import com.example.randomjokes.JokeActivity;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=23)
public class EndpointTest {

    String joke = null;
    MainActivity mainActivity;

    @Before
    public void setupTest(){
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testCloudEndPoint() throws Exception {
        try {
            joke = mainActivity.new EndpointsAsyncTask().execute(mainActivity).get(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            fail("Timed out");
        } finally {
            assertThat(joke).isNotNull();
            assertThat(joke.length()).isNotEqualTo(0);
        }
    }

    @Test
    public void testJokeActivityLaunch() throws Exception {

        mainActivity.findViewById(R.id.joke_button).performClick();

        Intent expectedIntent = new Intent(mainActivity, JokeActivity.class);
        assertThat(shadowOf(mainActivity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }

}