package com.ksintership.kozhushanmariia.activity;

import android.os.Bundle;

import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.fragments.FragmentViewer;
import com.ksintership.kozhushanmariia.utils.Constants;

public class SecondActivity extends BaseActivity {

    private FragmentViewer fragmentViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        int resIdToDisplay = getIntent().getIntExtra(Constants.KEY_IM_ID, -1);
        String name = getIntent().getStringExtra(Constants.KEY_NM_ID);
        int year = getIntent().getIntExtra(Constants.KEY_YR_ID, 0);
        String genre = getIntent().getStringExtra(Constants.KEY_GR_ID);

        String description = getIntent().getStringExtra(Constants.KEY_DC_ID);
        double rating = getIntent().getDoubleExtra(Constants.KEY_RT_ID, 0);
        fragmentViewer = (FragmentViewer) getSupportFragmentManager().findFragmentById(R.id.fragment_two);
        fragmentViewer.displayResource(resIdToDisplay, name, genre, rating, year, description);
        createToolbarWithBackBtn(name);
    }

    private void setViewsId() {

    }

    private void setOnAction() {

    }


}