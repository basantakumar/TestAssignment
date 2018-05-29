package com.android.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.android.test.activity.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2 {

    Activity mActivity;

    public ExampleInstrumentedTest()
    {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();

    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.test", appContext.getPackageName());
    }

    @Test
    public void test_readJsonFileFromAsset()
    {
        try{
            AssetManager assetManager = mActivity.getAssets();
            InputStream ims = assetManager.open("test.txt");
            assertNotNull(ims);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
