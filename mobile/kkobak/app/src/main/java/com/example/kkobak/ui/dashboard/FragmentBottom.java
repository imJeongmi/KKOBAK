package com.example.kkobak.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kkobak.R;

import butterknife.ButterKnife;

public class FragmentBottom extends Fragment {
    static final String ARG_TRAVEL = "ARG_TRAVEL";
    ChallengeInfo infoData;

    TextView bottomContents;

    public static FragmentBottom newInstance() {
        return new FragmentBottom();
    }

    public static FragmentBottom newInstance(ChallengeInfo travel) {
        Bundle args = new Bundle();
        FragmentBottom fragmentBottom = new FragmentBottom();
        args.putParcelable(ARG_TRAVEL, travel);
        fragmentBottom.setArguments(args);
        return fragmentBottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bottomContents = getActivity().findViewById(R.id.bottomContents);

        Bundle args = getArguments();
        if (args != null) {
            infoData = args.getParcelable(ARG_TRAVEL);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (bottomContents == null)     bottomContents = getActivity().findViewById(R.id.bottomContents);

//        Toast.makeText(getActivity(), "" + infoData, Toast.LENGTH_SHORT).show();
        if (infoData != null) {
            bottomContents.setText(infoData.getContents());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.example.kkobak.R.layout.fragment_bottom, container, false);
    }
}