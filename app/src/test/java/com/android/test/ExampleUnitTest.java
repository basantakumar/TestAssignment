package com.android.test;

import android.app.Activity;
import android.content.res.AssetManager;
import android.test.ActivityInstrumentationTestCase2;

import com.android.test.activity.MainActivity;

import org.junit.Test;

import java.io.InputStream;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends ActivityInstrumentationTestCase2 {

    Activity mActivity;

public ExampleUnitTest()
{
    super(MainActivity.class);
}
    public void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();

    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
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