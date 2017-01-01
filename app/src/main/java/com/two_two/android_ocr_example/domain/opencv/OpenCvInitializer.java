package com.two_two.android_ocr_example.domain.opencv;

import android.content.Context;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class OpenCvInitializer {

    private static final String TAG = "OpenCvInitializer";

    public static void initOpenCv(Context activityContext) {
        BaseLoaderCallback mLoaderCallback = getBaseLoaderCallbackForInitOpenCv(activityContext);
        //initialize OpenCV manager
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, activityContext, mLoaderCallback);
    }

    private static BaseLoaderCallback getBaseLoaderCallbackForInitOpenCv(Context context) {
        return new BaseLoaderCallback(context) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                        Log.i(TAG, "OpenCV Manager Connected");
                        //from now onwards, you can use OpenCV API
                        break;
                    case LoaderCallbackInterface.INIT_FAILED:
                        Log.i(TAG, "Init Failed");
                        break;
                    case LoaderCallbackInterface.INSTALL_CANCELED:
                        Log.i(TAG, "Install Cancelled");
                        break;
                    case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
                        Log.i(TAG, "Incompatible Version");
                        break;
                    case LoaderCallbackInterface.MARKET_ERROR:
                        Log.i(TAG, "Market Error");
                        break;
                    default:
                        Log.i(TAG, "OpenCV Manager Install");
                        super.onManagerConnected(status);
                        break;
                }
            }
        };
    }
}
