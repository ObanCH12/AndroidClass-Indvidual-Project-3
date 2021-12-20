package com.example.individualproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelect extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        findViewById(R.id.level1).setOnClickListener(this::levelSelect);
        findViewById(R.id.level2).setOnClickListener(this::levelSelect);
        findViewById(R.id.level3).setOnClickListener(this::levelSelect);
        findViewById(R.id.level4).setOnClickListener(this::levelSelect);
        findViewById(R.id.level5).setOnClickListener(this::levelSelect);
        findViewById(R.id.level6).setOnClickListener(this::levelSelect);
    }

    private void levelSelect(View view) {
        if (view.getId() == R.id.level1) {
            startActivity(new Intent(LevelSelect.this, BeginnerLevel1.class));
        }
        if (view.getId() == R.id.level2) {
            startActivity(new Intent(LevelSelect.this, BeginnerLevel2.class));
        }
        if (view.getId() == R.id.level3) {
            startActivity(new Intent(LevelSelect.this, BeginnerLevel3.class));
        }

    }
}