package com.ksintership.kozhushanmariia.activity;


import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ksintership.kozhushanmariia.R;
import com.ksintership.kozhushanmariia.fragments.FragmentCatalog;
import com.ksintership.kozhushanmariia.fragments.FragmentSaved;
import com.ksintership.kozhushanmariia.utils.adapter.ViewPagerAdapter;


public class ThirdActivity extends BaseActivity {


    private FragmentCatalog fragmentCatalog;
    private FragmentSaved fragmentSaved;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        initViews();

        fragmentCatalog = FragmentCatalog.newInstance();
        fragmentSaved = new FragmentSaved();

        adapter.addFragment(fragmentCatalog, "Watched");
        adapter.addFragment(fragmentSaved, "Pinned");

        viewPager.setAdapter(adapter);

        createToolbarWithBackBtn(getString(R.string.app_name));

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        getToolbar().setTitle(adapter.getPageTitle(0));

        setListeners();

    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

    }

    private void setListeners() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getToolbar().setTitle(adapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}

