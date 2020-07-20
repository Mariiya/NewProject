package com.ksintership.kozhushanmariia.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.Log;
import android.view.View;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.utils.Constants;

public class SecondActivity extends BaseActivity {

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
        createToolbarWithBackBtn(getString(R.string.title_activity_second));
        setOnAction();

    }

    private void setViewsId() {

    }

    private void setOnAction() {

    }



}