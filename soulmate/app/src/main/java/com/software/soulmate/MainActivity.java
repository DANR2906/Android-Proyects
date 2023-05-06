package com.software.soulmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieHeartButton;

    private LottieAnimationView lottieHeartFly;

    private TextView phraseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottieHeartButton = findViewById(R.id.lottieHeartButton);
        lottieHeartFly = findViewById(R.id.lottieHeartFly);
        phraseText = findViewById(R.id.phraseText);

        lottieHeartButton.playAnimation();
        lottieHeartButton.setOnClickListener(v -> {
            lottieHeartButton.cancelAnimation();
            lottieHeartButton.setVisibility(View.GONE);
            lottieHeartFly.playAnimation();
            lottieHeartFly.setVisibility(View.VISIBLE);
            phraseText.setText(Utils.getRandomPhrase());
            phraseText.setVisibility(View.VISIBLE);
        });


        lottieHeartFly.setOnClickListener(v -> {
            lottieHeartFly.cancelAnimation();
            lottieHeartFly.setVisibility(View.GONE);
            lottieHeartButton.playAnimation();
            lottieHeartButton.setVisibility(View.VISIBLE);
            phraseText.setVisibility(View.GONE);
        });
    }
}