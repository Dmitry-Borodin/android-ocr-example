package com.two_two.android_ocr_example.domain.tess;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class TessRecognizer {

    private TessBaseAPI tessAPI;
    private Bitmap previousBitmap;
    private String recognizedText;

    public TessRecognizer(TessBaseAPI tessBaseAPI) {
        this.tessAPI = tessBaseAPI;
    }

    public void inspectFromBitmapAsync(@NonNull final Bitmap bitmap, final RecognizedCallback callback) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String[] objects) {
                return inspectFromBitmap(bitmap);
            }

            @Override
            protected void onPostExecute(String text) {
                callback.onRecognized(text);
            }
        }.execute();
    }

    public String inspectFromBitmap(@NonNull final Bitmap bitmap) {
        if (checkCache(bitmap)) return recognizedText;
        String text = getRecognizedString(bitmap);
        setCache(bitmap, text);
        return text;
    }

    private String getRecognizedString(@NonNull Bitmap bitmap) {
        tessAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        tessAPI.setImage(bitmap);
        return tessAPI.getUTF8Text();
    }

    private boolean checkCache(@NonNull Bitmap bitmap) {
        if (bitmap.equals(previousBitmap)) {
            return true;
        }
        return false;
    }

    private void setCache(@NonNull Bitmap bitmap, String text) {
        previousBitmap = bitmap;
        recognizedText = text;
    }

    public interface RecognizedCallback {
        void onRecognized(String result);
    }


}
