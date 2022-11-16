package com.example.kkobak.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kkobak.ui.dashboard.fragments.ExpandingFragment;

public class ChallengeExpandingFragment extends ExpandingFragment {
    static final String ARG_TRAVEL = "ARG_TRAVEL";
    ChallengeInfo travel;

    public static ChallengeExpandingFragment newInstance(ChallengeInfo travel){
        ChallengeExpandingFragment fragment = new ChallengeExpandingFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRAVEL, travel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            travel = args.getParcelable(ARG_TRAVEL);
        }
    }

    /**
     * include TopFragment
     * @return
     */
    @Override
    public Fragment getFragmentTop() {
        return FragmentTop.newInstance(travel);
    }

    /**
     * include BottomFragment
     * @return
     */
    @Override
    public Fragment getFragmentBottom() {
        return FragmentBottom.newInstance();
    }
}
