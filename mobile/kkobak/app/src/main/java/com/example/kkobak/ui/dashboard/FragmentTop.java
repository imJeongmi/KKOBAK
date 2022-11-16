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

import com.bumptech.glide.Glide;
import com.example.kkobak.R;

import butterknife.ButterKnife;

public class FragmentTop extends Fragment {
    static final String ARG_TRAVEL = "ARG_TRAVEL";
    ChallengeInfo infoData;

    ImageView image;
    TextView title;
    ImageView doneImg;
    TextView leftLetter;
    TextView rightLetter;

    final int RUNNING = 1;
    final int WALKING = 2;
    final int MEDITATION = 3;
    final int DRINK_WATER = 4;
    final int EAT_NUTRIENTS = 5;
    final int STAND_UP = 6;
    final int ATTEND = 7;

    public static FragmentTop newInstance(ChallengeInfo travel) {
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
        doneImg = getActivity().findViewById(R.id.doneImg);
        leftLetter = getActivity().findViewById(R.id.leftLetter);
        rightLetter = getActivity().findViewById(R.id.rightLetter);

        Bundle args = getArguments();
        if (args != null) {
            infoData = args.getParcelable(ARG_TRAVEL);
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

        if (image == null)          image = getActivity().findViewById(R.id.image);
        if (title == null)          title = getActivity().findViewById(R.id.title);
        if (doneImg == null)        doneImg = getActivity().findViewById(R.id.doneImg);
        if (leftLetter == null)     leftLetter = getActivity().findViewById(R.id.leftLetter);
        if (rightLetter == null)    rightLetter = getActivity().findViewById(R.id.rightLetter);

        if (infoData != null) {
            Glide.with(this).load(infoData.getImgUrl()).into(image);
            title.setText(infoData.getTitle());

            if (infoData.isDone())
                Glide.with(this).load(R.drawable.done).into(doneImg);
            else
                Glide.with(this).load(R.drawable.yet).into(doneImg);

            switch (infoData.getDetailCategoryId()) {
                case RUNNING:
                    leftLetter.setText("달리기");
                    break;
                case WALKING:
                    leftLetter.setText("걷기");
                        break;
                case MEDITATION:
                        leftLetter.setText("명상");
                        break;
                case DRINK_WATER:
                    leftLetter.setText("물 마시기");
                    break;
                case EAT_NUTRIENTS:
                    leftLetter.setText("비타민 먹기");
                    break;
                case STAND_UP:
                    leftLetter.setText("스트레칭");
                    break;
                case ATTEND:
                    leftLetter.setText("출석");
                    break;
            }
            rightLetter.setText("~" + infoData.getEndTime());
//            image.setImageResource(infoData.getImage());
//            title.setText(infoData.getName());
        }

    }

    @SuppressWarnings("unchecked")
    private void startInfoActivity(View view, ChallengeInfo travel) {
        FragmentActivity activity = getActivity();
        ActivityCompat.startActivity(activity,
                InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                activity,
                                new Pair<>(view, getString(com.example.kkobak.R.string.transition_image)))
                        .toBundle());
    }
}
