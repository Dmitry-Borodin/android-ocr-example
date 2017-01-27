package com.two_two.android_ocr_example.ui.markers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.two_two.android_ocr_example.R;
import com.two_two.android_ocr_example.ui.ocr.OcrActivity;

import butterknife.BindView;

public class MarksRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.marks_go_to_ocr_button)
    Button gotoOcrButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_recognition);
        initViews();
    }

    private void initViews() {
        gotoOcrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarksRecognitionActivity.this, OcrActivity.class);
                startActivity(intent);
            }
        });
    }
}
