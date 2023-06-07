package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StoryBookActivity extends Activity {

    private int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        page = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storybook);

        ImageView iv = findViewById(R.id.imageView);
        iv.setImageBitmap(Story.bitmap);

        TextView tv = findViewById(R.id.textView);
        String text = Story.storyScript.get(page);
        text = text.replace("\n", "\n\n");
        tv.setText(text);

        Button btnNext = findViewById(R.id.nextButton);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page < Story.storyScript.size() - 1) {
                    page++;
                    tv.setText(Story.storyScript.get(page));
                } else if(page == Story.storyScript.size() - 1) {
                    ImageView iv = findViewById(R.id.imageView);
                    iv.setVisibility(View.GONE);
                    tv.setText("The End!");
                    tv.setTextSize(30);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setTypeface(Typeface.SERIF, Typeface.ITALIC);
                    btnNext.setText("Restart");
                    page++;
                } else {
                    Intent intent = new Intent(StoryBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do nothing to prevent back button navigation
    }
}
