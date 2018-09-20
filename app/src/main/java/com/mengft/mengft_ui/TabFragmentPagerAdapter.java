package com.mengft.mengft_ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mengft on 2018/5/2.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 4;

    private TabFragmentHome tabFragmentHome = null;
    private TabFragmentCourse tabFragmentCourse = null;
    private TabFragmentCommunity tabFragmentCommunity = null;
    private TabFragmentPersonalCenter tabFragmentPersonalCenter = null;

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        tabFragmentHome = new TabFragmentHome();
        tabFragmentCourse = new TabFragmentCourse();
        tabFragmentCommunity = new TabFragmentCommunity();
        tabFragmentPersonalCenter = new TabFragmentPersonalCenter();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = tabFragmentHome;
                break;
            case MainActivity.PAGE_TWO:
                fragment = tabFragmentCourse;
                break;
            case MainActivity.PAGE_THREE:
                fragment = tabFragmentCommunity;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = tabFragmentPersonalCenter;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
