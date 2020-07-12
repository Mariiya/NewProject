package com.ksintership.kozhushanmariia.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.utils.Constants;


public class MainActivity extends AppCompatActivity {

    private AppCompatEditText inputTextField;
    private AppCompatButton sendTextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewsId();
        setOnAction();
    }

    private void setViewsId() {
        inputTextField = findViewById(R.id.text_input_main_activity);
        sendTextBtn = findViewById(R.id.btn_send_main_activity);

    }

    private void setOnAction() {
        inputTextField.setTextColor(getResources().getColor(R.color.colorPrimary));
        sendTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendTextBtn();
            }
        });
    }


    private void handleSendTextBtn() {
        Editable getText = inputTextField.getText();
        if (TextUtils.isEmpty(getText)) {
            Toast.makeText(MainActivity.this, getResources().getText(R.string.empty_text_field_error), Toast.LENGTH_LONG).show();
        } else {

            Intent sendTextIntent = new Intent(MainActivity.this, SecondActivity.class);
            sendTextIntent.putExtra(Constants.EXTRA_SEND_TEXT, getText.toString());
            setResult(RESULT_OK, sendTextIntent);
            startActivityForResult(sendTextIntent, Constants.REQUEST_CODE);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE) {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_LONG).show();
            }
        } else inputTextField.setText("");

    }


}