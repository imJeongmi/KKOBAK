package com.example.kkobak.ui.dashboard;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class TravelViewPagerAdapter extends ExpandingViewPagerAdapter {
    List<Travel> travels;

    public TravelViewPagerAdapter(FragmentManager fm) {
        super(fm);
        travels = new ArrayList<>();
    }

    public void addAll(List<Travel> travels){
//        if (this.travels == null)
//            this.travels = new ArrayList<>();
//        Log.d("data size", "addAll: " + travels.size());
        this.travels.addAll(travels);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Travel travel = travels.get(position);
        return TravelExpandingFragment.newInstance(travel);
    }

    @Override
    public int getCount() {
        return travels.size();
    }
}
