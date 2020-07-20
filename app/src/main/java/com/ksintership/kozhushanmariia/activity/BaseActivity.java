package com.ksintership.kozhushanmariia.activity;

import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.ksintership.kozhushanmariia.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;


    public void createToolbarWithBackBtn(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void createBaseToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setLogo(R.drawable.ic_nice_btn);
        View logo = toolbar.getChildAt(1);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, getResources().getText(R.string.app_information), Toast.LENGTH_LONG).show();
            }
        });
        toolbar.setSubtitle(getResources().getText(R.string.app_subtitle));
    }

}
