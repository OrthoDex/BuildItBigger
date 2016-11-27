package com.udacity.gradle.builditbigger;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

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
            joke = new EndpointsAsyncTask(mainActivity).execute(mainActivity).get(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            fail("Timed out");
        } finally {
            assertThat(joke).isNotNull();
            assertThat(joke.length()).isNotEqualTo(0);
        }
    }
}