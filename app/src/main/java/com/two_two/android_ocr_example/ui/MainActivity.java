package com.two_two.android_ocr_example.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.two_two.android_ocr_example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.main_activity_image_view)
    ImageView imageView;
    @BindView(R.id.main_activity_text_result)
    TextView textResultView;

    private ProgressDialog progressDialog;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onViewResumed();

    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public void showInitTessProgressBar() {
        dismissProgressBar();
        final String message = getString(R.string.progress_bar_initializing_tess_message);
        showProgressDialog(message);
    }

    @Override
    public void showRecognizingProgressBar() {
        dismissProgressBar();
        final String message = getString(R.string.progress_bar_recognizing_image_message);
        showProgressDialog(message);
    }

    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_bar_loading_title));
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void showRecognizedText(String text) {
        textResultView.setText(text);
    }

    @Override
    public void showAnalyzedImage(Drawable image) {
        imageView.setImageDrawable(image);
    }

    @Override
    public void dismissProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * Displays an error message progressDialog box to the user on the UI thread.
     *
     * @param message The error message to be displayed
     */
    @Override
    public void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_dialog_title)
                .setMessage(message)
                .setOnCancelListener(new FinishListener(this))
                .setPositiveButton(R.string.dialog_ok_button, new FinishListener(this))
                .show();
    }
}
