package com.two_two.android_ocr_example.ui;

import android.graphics.drawable.Drawable;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public interface MainActivityContract {
    interface View {
        void showRecognizedText(String text);
        void showAnalyzedImage(Drawable image);
        void dismissProgressBar();
        void showInitTessProgressBar();
        void showRecognizingProgressBar();
        void showError(String errorMessage);
    }

    interface Presenter {
        void onViewResumed();
        void onViewDestroyed();
    }
}
