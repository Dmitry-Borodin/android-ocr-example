package com.two_two.android_ocr_example.domain.tess;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.two_two.android_ocr_example.data.ImageRepository;
import com.two_two.android_ocr_example.ui.MainActivity;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class TessRecognizer {

    private TessBaseAPI tessAPI;

    public TessRecognizer(TessBaseAPI tessBaseAPI) {
        this.tessAPI = tessBaseAPI;
    }

    public String inspectFromBitmap(final Bitmap bitmap) {
        tessAPI.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        tessAPI.setImage(bitmap);
        String text = tessAPI.getUTF8Text();
        return text;
    }

    public void inspectFromBitmapAsync(final Bitmap bitmap, final RecognizedCallback callback) {
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


    public interface RecognizedCallback {
        void onRecognized(String result);
    }


}
