package com.two_two.android_ocr_example.domain.tess;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.two_two.android_ocr_example.R;

import java.io.File;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class TessInitializator {

    private static final String TAG = "TessInitializator";

    private boolean isEngineReady = false;
    private String lastLangCode;
    private String lastLangName;
    private int ocrEngineMode = TessBaseAPI.OEM_TESSERACT_ONLY;
    private TessBaseAPI tessAPI;
    private Context context;

    public TessInitializator(Context context, TessBaseAPI tessAPI) {
        this.context = context;
        this.tessAPI = tessAPI;
    }

    /**
     * Requests initialization of the OCR engine with the given parameters.
     *
     * @param languageCode Three-letter ISO 639-3 language code for OCR
     * @param languageName Name of the language for OCR, for example, "English"
     */
    public void initTessOcrEngine(@NonNull String languageCode, @NonNull String languageName, final Callback callback) {
        if (isEngineReady && languageCode.equals(lastLangCode) && languageName.equals(lastLangName)) {
            //already initialized
            callback.onInitialized();
            return;
        }
        initOcrEngineForce(languageCode, languageName, callback);
    }

    private void initOcrEngineForce(@NonNull String languageCode, @NonNull String languageName, final Callback callback) {
        isEngineReady = false;
        lastLangCode = languageCode;
        lastLangName = languageName;

        // If we have a language that only runs using Cube, then set the ocrEngineMode to Cube
        if (ocrEngineMode != TessBaseAPI.OEM_CUBE_ONLY) {
            for (String s : TessHelper.CUBE_REQUIRED_LANGUAGES) {
                if (s.equals(languageCode)) {
                    ocrEngineMode = TessBaseAPI.OEM_CUBE_ONLY;
                }
            }
        }

        // If our language doesn't support Cube, then set the ocrEngineMode to Tesseract
        if (ocrEngineMode != TessBaseAPI.OEM_TESSERACT_ONLY) {
            boolean cubeOk = false;
            for (String s : TessHelper.CUBE_SUPPORTED_LANGUAGES) {
                if (s.equals(languageCode)) {
                    cubeOk = true;
                }
            }
            if (!cubeOk) {
                ocrEngineMode = TessBaseAPI.OEM_TESSERACT_ONLY;
            }
        }
        File storageRoot = getStorageDirectory(context, callback);

        // Start AsyncTask to install language data and init OCR
        if (storageRoot != null) {
            new TessInitAsyncTask(context, tessAPI, languageCode, languageName, ocrEngineMode) {
                @Override
                protected void onPostExecute(Boolean isSuccess) {
                    isEngineReady = true;
                    callback.onInitialized();
                }
            }.execute(storageRoot.toString());
        }
    }


    /**
     * Finds the proper location on the SD card where we can save files.
     */
    private File getStorageDirectory(Context context, Callback callback) {
        //Log.d(TAG, "getStorageDirectory(): API level is " + Integer.valueOf(android.os.Build.VERSION.SDK_INT));

        String state = null;
        try {
            state = Environment.getExternalStorageState();
        } catch (RuntimeException e) {
            Log.e(TAG, "Is the SD card visible?", e);
            callback.onError(context.getString(R.string.error_external_storage_unavailable_not_visible));
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            // We can read and write the media
            //    	if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) > 7) {
            // For Android 2.2 and above

            try {
                return context.getExternalFilesDir(Environment.MEDIA_MOUNTED);
            } catch (NullPointerException e) {
                // We get an error here if the SD card is visible, but full
                Log.e(TAG, "External storage is unavailable");
                callback.onError(context.getString(R.string.error_external_storage_full));
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            Log.e(TAG, "External storage is read-only");
            callback.onError(context.getString(R.string.error_external_storage_read_only));
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            // to know is we can neither read nor write
            Log.e(TAG, "External storage is unavailable");
            callback.onError(context.getString(R.string.error_external_storage_unavailable));
        }
        return null;
    }

    public interface Callback {
        void onError(String message);

        void onInitialized();
    }
}
