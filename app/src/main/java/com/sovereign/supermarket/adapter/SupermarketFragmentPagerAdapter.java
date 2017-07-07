package com.sovereign.supermarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sovereign.supermarket.fragment.SupermarketHomeFragment;
import com.sovereign.supermarket.fragment.SupermarketHomeFragment2;

public class SupermarketFragmentPagerAdapter extends FragmentPagerAdapter {

    private String numero_mesa;
    private String tabTitles[] =
            new String[] { "Intro", "List of items", "Shopping Cart"};
    final int PAGE_COUNT = tabTitles.length;

    public SupermarketFragmentPagerAdapter(FragmentManager fm, String numero_mesa) {
        super(fm);
        this.numero_mesa = numero_mesa;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch(position) {
            case 0:
                f = SupermarketHomeFragment.newInstance(numero_mesa);
                break;
            case 1:
            case 2:
                f = SupermarketHomeFragment2.newInstance(numero_mesa);
                break;
        }

        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}