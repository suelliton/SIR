package com.example.suelliton.sir;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.suelliton.sir.NodeAdapter.nameClicado;

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
                return new FragmentPrevisao();
            case 3:
                return new FragmentGastos();
            default:
                return null;

        }
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Nós";
            case 1:
                return "Histórico";
            case 2:
                return "Previsão";
            case 3:
                return "Gastos";
            default:
                return null;
        }
    }
}
