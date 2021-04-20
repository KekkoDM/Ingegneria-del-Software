package com.example.cinemates.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cinemates.fragments.FollowNotificationsFragment;
import com.example.cinemates.fragments.GeneralNotificationsFragment;

public class NotificationAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private String[] title = {"Generale", "Richieste di collegamento"};

    public NotificationAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GeneralNotificationsFragment();
                break;
            case 1:
                fragment = new FollowNotificationsFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
