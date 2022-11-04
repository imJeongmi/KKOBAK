package com.example.kkobak.ui.notifications;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kkobak.R;
import com.example.kkobak.ui.main.MainActivity;

import java.sql.Array;
import java.util.ArrayList;

public class MyChallengeAdapter extends BaseAdapter {
    ArrayList<MyChallengeCard> items = new ArrayList<>();
    Context context;

    public void addItem(MyChallengeCard item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        System.out.println(context);
        MyChallengeCard item = items.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_challenge_item, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.challengeItemImg);
        TextView title = convertView.findViewById(R.id.challengeItemTitle);
        TextView startTime = convertView.findViewById(R.id.challengeItemStartTime);
        TextView endTime = convertView.findViewById(R.id.challengeItemEndTime);
        TextView watch = convertView.findViewById(R.id.challengeItemWatch);

        String testImgUrl = "https://static.wikia.nocookie.net/pokemon/images/3/3c/%EB%9D%BC%EC%9D%B4%EC%B8%84_%EA%B3%B5%EC%8B%9D_%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8.png/revision/latest?cb=20170405000124&path-prefix=ko";
        String imgUrl = item.getImgUrl();
        if ("".equals(imgUrl) || imgUrl == null || "string".equals(imgUrl))    imgUrl = testImgUrl;
        else if (imgUrl.contains("google")) imgUrl = testImgUrl;

        Glide.with(context).load(imgUrl).into(img);
        title.setText(item.getTitle());
        startTime.setText(item.getStartTime());
        endTime.setText(item.getEndTime());
        watch.setText(item.isWatch() ? "워치O" : "워치X");

        return (convertView);
    }
}
