package com.two_two.android_ocr_example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.two_two.android_ocr_example.R;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class ImageRepository {

    private static final int imageIdToAnalyze = R.drawable.text_on_image;
    private Context context;
    private Bitmap bitmap;

    public ImageRepository(Context context) {
        this.context = context;
    }

    public void setMostRecentPicture(Bitmap bitmap) {
        if (this.bitmap != null) {
            this.bitmap.recycle();
        }
        this.bitmap = bitmap;
    }

    public Bitmap getPictureBitmapForOcr() {
        if (bitmap != null) {
            return bitmap;
        }
        return bitmap = BitmapFactory.decodeResource(context.getResources(), imageIdToAnalyze);
    }
}
