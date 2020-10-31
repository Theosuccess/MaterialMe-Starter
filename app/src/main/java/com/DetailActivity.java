package com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.materialme.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //initialize the ImageView and title TextView in onCreate()
        TextView sportsTitle = findViewById(R.id.titleDetail);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);
        //Get the title from the incoming Intent and set it to the TextView:
        sportsTitle.setText(getIntent().getStringExtra("title"));
        //Use Glide to load the image into the ImageView:
        Glide.with(this).load(getIntent().getIntExtra("image_resource",
                0)).into(sportsImage);
    }
}