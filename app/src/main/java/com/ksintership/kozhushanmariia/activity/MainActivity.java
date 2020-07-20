package com.ksintership.kozhushanmariia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.ksintership.kozhushanmariia.R;

import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.fragments.FragmentChooser;



public class MainActivity extends BaseActivity {

private FragmentChooser fragmentChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewsId();
        setOnAction();
        createBaseToolbar(getString(R.string.app_name));
        fragmentChooser = (FragmentChooser) getSupportFragmentManager().findFragmentById(R.id.fragment_one);

    }
    private void setViewsId() {


    }

    private void setOnAction() {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE) {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_LONG).show();
            }
        }

    }

}