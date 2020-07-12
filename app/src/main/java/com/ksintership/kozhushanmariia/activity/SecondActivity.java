package com.ksintership.kozhushanmariia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.utils.Constants;

public class SecondActivity extends AppCompatActivity {

    private final static String LOG_TAG = SecondActivity.class.getSimpleName();
    private AppCompatButton okBtn;
    private AppCompatButton cancelBtn;
    private AppCompatTextView inputText;
    String inputStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setViewsId();
        if (getIntent().getExtras() != null) {
            inputStr = getIntent().getStringExtra(Constants.EXTRA_SEND_TEXT);
            inputText.setText(inputStr);
        }

        setOnAction();
    }

    private void setViewsId() {
        cancelBtn = findViewById(R.id.btn_cancel_2_activity);
        okBtn = findViewById(R.id.btn_ok_2_activity);
        inputText = findViewById(R.id.imported_text_from_main_activity);
    }

    private void setOnAction() {

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCancelTextBtn();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOkTextBtn();
            }
        });
    }

    private void handleCancelTextBtn() {
        finish();
    }

    private void handleOkTextBtn() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_ANSWER, Constants.REQUEST_CODE);
        setResult(RESULT_OK, intent);

        finish();
    }

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }
}