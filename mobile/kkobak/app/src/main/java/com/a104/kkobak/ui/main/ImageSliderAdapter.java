package com.a104.kkobak.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a104.kkobak.R;
import com.a104.kkobak.data.retrofit.model.ChallengeAllDataRes;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {
    private Context context;
    private List<ChallengeAllDataRes> allData;

    final int RUNNING = 1;
    final int WALKING = 2;
    final int MEDITATION = 3;
    final int DRINK_WATER = 4;
    final int EAT_NUTRIENTS = 5;
    final int STAND_UP = 6;
    final int ATTEND = 7;

    public ImageSliderAdapter(Context context, List<ChallengeAllDataRes> allData) {
        this.context = context;
        this.allData = allData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindTopSlider(allData.get(position));
        holder.bindBottomSlider(allData.get(position));
    }

    @Override
    public int getItemCount() {
        return (allData.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTitle;
        private TextView mLeft;
        private TextView mRight;
        private ImageView mImg;

        private ImageView mWatch;
        private TextView mContent;
        private TextView mGoal;
        private TextView mNowCnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageSlider);
            mTitle = itemView.findViewById(R.id.topTitle);
            mLeft = itemView.findViewById(R.id.leftLetter);
            mRight = itemView.findViewById(R.id.rightLetter);
            mImg = itemView.findViewById(R.id.middleImg);

            mWatch = itemView.findViewById(R.id.itemWatch);
            mContent = itemView.findViewById(R.id.itemContent);
            mGoal = itemView.findViewById(R.id.itemGoal);
            mNowCnt = itemView.findViewById(R.id.itemNowCnt);
        }

        public void bindTopSlider(ChallengeAllDataRes data) {
            Glide.with(context).load(data.getImgUrl()).into(mImageView);
            mTitle.setText(data.getTitle());
            mRight.setText("~" + data.getEndTime());

            if (data.isDone())
                Glide.with(context).load(R.drawable.done).into(mImg);
            else
                Glide.with(context).load(R.drawable.yet).into(mImg);

            switch (data.getDetailCategoryId()) {
                case RUNNING:
                    mLeft.setText("달리기");
                    break;
                case WALKING:
                    mLeft.setText("걷기");
                    break;
                case MEDITATION:
                    mLeft.setText("명상");
                    break;
                case DRINK_WATER:
                    mLeft.setText("물 마시기");
                    break;
                case EAT_NUTRIENTS:
                    mLeft.setText("비타민 먹기");
                    break;
                case STAND_UP:
                    mLeft.setText("스트레칭");
                    break;
                case ATTEND:
                    mLeft.setText("출석");
                    break;
            }
        }

        public void bindBottomSlider(ChallengeAllDataRes data) {
            mContent.setText(data.getContents());
            if (!data.isWatch())
                mWatch.setVisibility(View.INVISIBLE);

            switch (data.getDetailCategoryId()) {
                case RUNNING:
                case WALKING:
                    mGoal.setText("목표: " + data.getGoal() + "m");
                    mNowCnt.setVisibility(View.INVISIBLE);
                    break;
                case MEDITATION:
                    mGoal.setText("목표: " + data.getGoal() + "분");
                    mNowCnt.setVisibility(View.INVISIBLE);
                    break;
                case DRINK_WATER:
                case EAT_NUTRIENTS:
                case STAND_UP:
                    mGoal.setText("목표횟수: " + data.getGoal() + "회");
                    mNowCnt.setText("현재횟수: " + data.getCnt() + "회");
                    break;
                case ATTEND:
                    mGoal.setVisibility(View.INVISIBLE);
                    mNowCnt.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    }
}
