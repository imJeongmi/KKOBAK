package com.example.kkobak.ui.dashboard;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ChallengeViewPagerAdapter extends ExpandingViewPagerAdapter {
    List<ChallengeInfo> travels;

    public ChallengeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        travels = new ArrayList<>();
    }

    public void addAll(List<ChallengeInfo> travels){
//        if (this.travels == null)
//            this.travels = new ArrayList<>();
//        Log.d("data size", "addAll: " + travels.size());
        this.travels.addAll(travels);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println(">>> pos: "+ position);
        ChallengeInfo travel = travels.get(position);
        return ChallengeExpandingFragment.newInstance(travel);
    }

    @Override
    public int getCount() {
        return travels.size();
    }
}
