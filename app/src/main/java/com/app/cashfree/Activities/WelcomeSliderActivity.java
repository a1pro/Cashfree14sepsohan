package com.app.cashfree.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.cashfree.Fragments.WelcomeFragment1;
import com.app.cashfree.Fragments.WelcomeFragment2;
import com.app.cashfree.Fragments.WelcomeFragment3;
import com.app.cashfree.R;

import java.util.HashMap;
import java.util.Map;

public class WelcomeSliderActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout dotsLayout;
    private NavController navController;
    private ViewPager view_pager;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    MyPagerAdapter myPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slider);
        view_pager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        layouts = new int[]{
                R.layout.slider_one,
                R.layout.slider_two,
                R.layout.slider_three};

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(myPagerAdapter);
        view_pager.addOnPageChangeListener(viewPagerPageChangeListener);
        addBottomDots(0);
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {

            } else {

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onClick(View view) {

    }




    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        private MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) WelcomeSliderActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;
        private Map<Integer, Fragment> fragments = new HashMap<>();

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    WelcomeFragment1 fragment = (WelcomeFragment1) fragments.get(position);
                    if (fragment == null) {
                        fragment = new WelcomeFragment1();
                        fragments.put(position, fragment);
                    }
                    return fragment;
                case 1:
                    WelcomeFragment2 fragment2 = (WelcomeFragment2) fragments.get(position);
                    if (fragment2 == null) {
                        fragment2 = new WelcomeFragment2();
                        fragments.put(position, fragment2);
                    }
                    return fragment2;
                case 2:
                    WelcomeFragment3 fragment3 = (WelcomeFragment3) fragments.get(position);
                    if (fragment3 == null) {
                        fragment3 = new WelcomeFragment3();
                        fragments.put(position, fragment3);
                    }
                    return fragment3;

                default:

                    return null;
            }

        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive2);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(WelcomeSliderActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
}