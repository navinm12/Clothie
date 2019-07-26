package io.jawware.noty;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroSlider extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    ViewPager viewPager;
    TextView[] dots;
    LinearLayout dotsLayout;

    Button startText;
    LinearLayout startLayout;
    PreManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        prefManager = new PreManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        viewPager = (ViewPager) findViewById(R.id.introview);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        startLayout = (LinearLayout) findViewById(R.id.startlayout);
        startText = (Button) findViewById(R.id.starttext);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.canScrollVertically(1);

        addBottomDots(0);
        startLayout.setVisibility(View.INVISIBLE);
        startText.setVisibility(View.INVISIBLE);

        startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroSlider.this,Login.class));
                finish();
            }
        });
        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLayout.callOnClick();
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setPadding(0,-60,0,-10);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

        addBottomDots(i);

        if (i == 2) {

            startLayout.setVisibility(View.VISIBLE);
            startText.setVisibility(View.VISIBLE);

        } else {
            startLayout.setVisibility(View.INVISIBLE);
            startText.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return Slide1.newInstance("FirstFragment", "Instance 1");
                case 1: return Slide2.newInstance("SecondFragment", "Instance 1");
                case 2: return Slide3.newInstance("ThirdFragment"," Instance 1");
                default: return Slide3.newInstance("DefaultFragment", "Default");
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}