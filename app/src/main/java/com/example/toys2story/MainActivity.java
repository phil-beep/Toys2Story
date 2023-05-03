package com.example.toys2story;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tf = (TextView) findViewById(R.id.textView);
        DrawingView dv = (DrawingView) findViewById(R.id.drawingView);

        Button btn = (Button) findViewById(R.id.analyzeButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: analyze drawing
            }
        });

        ImageButton delete = (ImageButton) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete path
            }
        });
    }
}