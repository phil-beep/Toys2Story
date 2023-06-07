package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Button btnStart = findViewById(R.id.startButton);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
       Story.toys = new ArrayList<>();
        TextView tv = findViewById(R.id.bulletView);
        tv.setText(Html.fromHtml("&#8226; Draw your toys<br>&#8226; Let AI analyze them<br>&#8226; Poetize a story<br>&#8226; and paint illustrations"));
    }

    @Override
    public void onBackPressed() {
        // Do nothing to prevent back button navigation
    }
}
