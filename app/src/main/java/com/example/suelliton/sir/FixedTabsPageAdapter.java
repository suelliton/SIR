package com.example.suelliton.sir;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by suelliton on 14/10/2017.
 */

public class FixedTabsPageAdapter extends FragmentPagerAdapter {
    public FixedTabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentRecycler();
            case 1:
                return new FragmentGrafico();
            case 2:
                return new FragmentRecycler();
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Dispositivos";
            case 1:
                return "Graficos";
            case 2:
                return "Localização";
            default:
                return null;
        }
    }
}
