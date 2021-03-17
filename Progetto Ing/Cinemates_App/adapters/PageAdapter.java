package com.example.cinemates.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.cinemates.fragments.DescriptionFragment;
import com.example.cinemates.fragments.ReviewFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.numTabs=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position){
            case 0:
                f = new DescriptionFragment();
                break;
            case 1:
                f = new ReviewFragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return numTabs;
    }

}
