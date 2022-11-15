package com.example.kkobak.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kkobak.R;

import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    private static final String EXTRA_TRAVEL = "EXTRA_TRAVEL";
    ImageView image;
    TextView title;

    public static Intent newInstance(Context context, Travel travel) {
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

        Travel travel = getIntent().getParcelableExtra(EXTRA_TRAVEL);
        if (travel != null) {
            image.setImageResource(travel.getImage());
            title.setText(travel.getName());
        }
    }
}
