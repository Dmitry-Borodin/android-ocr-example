package com.two_two.android_ocr_example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.two_two.android_ocr_example.R;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class ImageRepository {

    private static final int imageIdToAnalyze = R.drawable.text_on_image;
    private Context context;

    public ImageRepository(Context context) {
        this.context = context;
    }

    public Bitmap getPictureBitmapForOcr() {
        return BitmapFactory.decodeResource(context.getResources(), imageIdToAnalyze);
    }

    public Drawable getPictureDrawableForOcr() {
        return context.getResources().getDrawable(imageIdToAnalyze, context.getTheme());
    }
}
