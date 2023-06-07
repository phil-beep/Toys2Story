package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

        TextView tv = findViewById(R.id.textView);
        Log.e("Hallo", "get Page, Size" + Story.storyScript.size());
        tv.setText(Story.storyScript.get(page));

        Button btnNext = findViewById(R.id.nextButton);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Hallo", "onClick");

                if(page < Story.storyScript.size() - 1) {
                    page++;
                    tv.setText(Story.storyScript.get(page));
                    Log.e("Hallo", "<");
                } else if(page == Story.storyScript.size() - 1) {
                    Log.e("Hallo", "==");
                    ImageView iv = findViewById(R.id.imageView);
                    iv.setVisibility(View.GONE);
                    tv.setText("The End!");
                    tv.setTextSize(30);
                    btnNext.setText("Restart");
                    page++;
                } else {
                    Log.e("Hallo", "else");
                    Intent intent = new Intent(StoryBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
