package com.example.kkobak.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.kkobak.R;

import butterknife.ButterKnife;

public class FragmentTop extends Fragment {
    static final String ARG_TRAVEL = "ARG_TRAVEL";
    Travel travel;

    ImageView image;
    TextView title;

    public static FragmentTop newInstance(Travel travel) {
        Bundle args = new Bundle();
        FragmentTop fragmentTop = new FragmentTop();
        args.putParcelable(ARG_TRAVEL, travel);
        fragmentTop.setArguments(args);
        return fragmentTop;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        image = getActivity().findViewById(R.id.image);
        title = getActivity().findViewById(R.id.title);

        Bundle args = getArguments();
        if (args != null) {
            travel = args.getParcelable(ARG_TRAVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_front, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (image == null)  image = getActivity().findViewById(R.id.image);
        if (title == null)  title = getActivity().findViewById(R.id.title);

        if (travel != null) {
            image.setImageResource(travel.getImage());
            title.setText(travel.getName());
        }

    }

    @SuppressWarnings("unchecked")
    private void startInfoActivity(View view, Travel travel) {
        FragmentActivity activity = getActivity();
        ActivityCompat.startActivity(activity,
                InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                new Pair<>(view, getString(com.example.kkobak.R.string.transition_image)))
                        .toBundle());
    }
}
