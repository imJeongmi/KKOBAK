package com.example.kkobak.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;

import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    private static final String EXTRA_TRAVEL = "EXTRA_TRAVEL";
    ImageView image;
    TextView title;

    ChallengeInfo infoData;

    public static Intent newInstance(Context context, ChallengeInfo travel) {
        Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra(EXTRA_TRAVEL, travel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        image = findViewById(R.id.image);
        title = findViewById(R.id.title);

        infoData = getIntent().getParcelableExtra(EXTRA_TRAVEL);
        if (infoData != null) {
            Toast.makeText(this, "info: " + infoData.getDetailCategoryId(), Toast.LENGTH_SHORT).show();
            image.setImageResource(infoData.getImage());
            title.setText(infoData.getName());
        }
    }
}
