package com.two_two.android_ocr_example.di;

import android.content.Context;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.two_two.android_ocr_example.data.ImageRepository;
import com.two_two.android_ocr_example.domain.App;
import com.two_two.android_ocr_example.domain.LanguageCodeHelper;
import com.two_two.android_ocr_example.domain.tess.TessInitializator;
import com.two_two.android_ocr_example.domain.tess.TessRecognizer;

/**
 * Provides simple thread-risky singletons. For now it's ok.
 *
 * @author Dmitry Borodin on 2017-01-01.
 */

public class DependencyProvider {

    private static TessInitializator tessInitializator;
    private static TessBaseAPI tessBaseAPI;
    private static TessRecognizer tessRecognizer;
    private static ImageRepository imageRepository;
    private static LanguageCodeHelper languageCodeHelper;

    public static TessInitializator getTessInitializator() {
        if (tessInitializator == null) {
            tessInitializator = new TessInitializator(getAppContext(), getTessBaseAPI());
        }
        return tessInitializator;
    }

    private static TessBaseAPI getTessBaseAPI() {
        if (tessBaseAPI == null) {
            tessBaseAPI = new TessBaseAPI();
        }
        return tessBaseAPI;
    }

    public static TessRecognizer getTessRecognizer() {
        if (tessRecognizer == null) {
            tessRecognizer = new TessRecognizer(getTessBaseAPI());
        }
        return tessRecognizer;
    }

    private static Context getAppContext() {
        return App.getInstance().getApplicationContext();
    }

    public static ImageRepository getImageRepository() {
        if (imageRepository == null) {
            imageRepository = new ImageRepository(getAppContext());
        }
        return imageRepository;
    }

    public static LanguageCodeHelper getLanguageCodeHelper() {
        if (languageCodeHelper == null) {
            languageCodeHelper = new LanguageCodeHelper(getAppContext());
        }
        return languageCodeHelper;
    }
}
