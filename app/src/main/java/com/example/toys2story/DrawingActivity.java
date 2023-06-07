package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DrawingActivity extends Activity {

    private boolean analyzed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        analyzed = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        TextView tv = findViewById(R.id.textView);
        tv.setText("Draw your toy! " + (Story.toys.size() + 1) + "/" + Story.STORYBOARDS);

        Button btnDelete = (Button) findViewById(R.id.deleteButton);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawingView dv = findViewById(R.id.drawingView);
                dv.resetDrawing();
            }
        });

        Button btnAnalyze = (Button) findViewById(R.id.analyzeButton);
        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Story.toys.size() < Story.STORYBOARDS) {
                    if (!analyzed) {
                        analyzed = true;
                        Analyzer.analyze();
                        tv.setText("That's a " + Story.toys.get(Story.toys.size() - 1));
                        btnAnalyze.setText("Use " + Story.toys.size() + "/" + Story.STORYBOARDS);
                        btnDelete.setVisibility(View.INVISIBLE);

                    } else {
                        Intent intent = new Intent(DrawingActivity.this, DrawingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(DrawingActivity.this, ToyListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}