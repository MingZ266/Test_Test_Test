package com.tai.TestTestTest;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DIYAnimActivity extends AppCompatActivity {

    Button alpha, scale, translate, rotate, set;
    MyImageView ChristmasGirl;
    Animation alphaAnim, scaleAnim, translateAnim, rotateAnim, setAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_anim);

        initView();
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChristmasGirl.startAnimation(alphaAnim);
            }
        });

        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChristmasGirl.startAnimation(scaleAnim);
            }
        });

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChristmasGirl.startAnimation(translateAnim);
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChristmasGirl.startAnimation(rotateAnim);
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChristmasGirl.startAnimation(setAnim);
            }
        });
    }

    void initView() {
        alpha         = findViewById(R.id.alpha);
        scale         = findViewById(R.id.scale);
        translate     = findViewById(R.id.translate);
        rotate        = findViewById(R.id.rotate);
        set           = findViewById(R.id.set);
        ChristmasGirl = findViewById(R.id.ChristmasGirl);
        alphaAnim     = AnimationUtils.loadAnimation(this, R.anim.diy_anim_alpha_anim);
        scaleAnim     = AnimationUtils.loadAnimation(this, R.anim.diy_anim_scale_anim);
        translateAnim = AnimationUtils.loadAnimation(this, R.anim.diy_anim_translate_anim);
        rotateAnim    = AnimationUtils.loadAnimation(this, R.anim.diy_anim_rotate_anim);
        setAnim       = AnimationUtils.loadAnimation(this, R.anim.diy_anim_set_anim);
    }
}
