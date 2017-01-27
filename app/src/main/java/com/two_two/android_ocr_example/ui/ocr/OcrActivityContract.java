package com.two_two.android_ocr_example.ui.ocr;

import android.graphics.Bitmap;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public interface OcrActivityContract {
    interface View {
        void showRecognizedText(String text);
        void showAnalyzedImage(Bitmap image);
        void dismissProgressBar();
        void showInitTessProgressBar();
        void showRecognizingProgressBar();
        void showError(String errorMessage);
    }

    interface Presenter {
        void onViewResumed();
        void onViewDestroyed();
        void onNewImageTaken(Bitmap newimage);
    }
}
