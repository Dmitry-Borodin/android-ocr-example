package com.two_two.android_ocr_example.ui;

import android.graphics.Bitmap;

import com.two_two.android_ocr_example.data.ImageRepository;
import com.two_two.android_ocr_example.di.DependencyProvider;
import com.two_two.android_ocr_example.domain.LanguageCodeHelper;
import com.two_two.android_ocr_example.domain.tess.TessInitializator;
import com.two_two.android_ocr_example.domain.tess.TessRecognizer;
import com.two_two.android_ocr_example.utils.Constants;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;
    private ImageRepository imageRepository;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        imageRepository = DependencyProvider.getImageRepository();
    }

    @Override
    public void onViewResumed() {
        startInitOcrEngine();
        view.showAnalyzedImage(imageRepository.getPictureDrawableForOcr());
    }

    @Override
    public void onViewDestroyed() {
        view = null;
    }

    private void startInitOcrEngine() {
        LanguageCodeHelper languageCodeHelper = DependencyProvider.getLanguageCodeHelper();

        String languageCode = Constants.DEFAULT_LANGUAGE_CODE;
        String languageName = languageCodeHelper.getOcrLanguageName(languageCode);
        initOcrEngine(languageCode, languageName);
    }

    /**
     * @param languageCode Three-letter ISO 639-3 language code for OCR
     * @param languageName Name of the language for OCR, for example, "English"
     */
    private void initOcrEngine(String languageCode, String languageName) {
        if (view != null) {
            view.showInitTessProgressBar();
        }
        TessInitializator initializator = DependencyProvider.getTessInitializator();
        initializator.initTessOcrEngine(languageCode, languageName, new TessInitializator.Callback() {
            @Override
            public void onError(String message) {
                if (view != null) {
                    view.dismissProgressBar();
                    view.showError(message);
                }
            }

            @Override
            public void onInitialized() {
                if (view != null) {
                    view.dismissProgressBar();
                    recognizePicture();
                }
            }
        });
    }


    private void recognizePicture() {
        if (view != null) {
            view.showRecognizingProgressBar();
        }
        Bitmap picture = imageRepository.getPictureBitmapForOcr();
        DependencyProvider.getTessRecognizer().inspectFromBitmapAsync(picture, new TessRecognizer.RecognizedCallback() {
            @Override
            public void onRecognized(String result) {
                if (view != null) {
                    view.dismissProgressBar();
                    view.showRecognizedText(result);
                }
            }
        });
    }
}
