package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToyListActivity extends Activity {

    Button btnCreateStory;
    String imageUrl = "https://i.imgur.com/NYcnYOK.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_list);

        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Story.toys);
        lv.setAdapter(adapter);

        btnCreateStory = findViewById(R.id.createStoryButton);
        btnCreateStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCreateStory.setEnabled(false);
                //requestStoryScript();
                fakeRequestStoryScript();
            }
        });
    }

    public void fakeRequestStoryScript() {
        String jsonString = "{\"id\":\"cmpl-7OiCBgKKm0inQxei74ln0FcnoCK7V\",\"object\":\"text_completion\",\"created\":1686124115,\"model\":\"text-davinci-003\",\"choices\":[{\"text\":\"\\n\\nOnce upon a sunny day\\nA little boy went out to play\\nHe brought his faithful Teddy bear\\nAnd a ball to throw up in the air\\nA passing plane flew far away\\nHe waved to it, then quickly ran to play!\",\"index\":0,\"logprobs\":null,\"finish_reason\":\"stop\"}],\"usage\":{\"prompt_tokens\":32,\"completion_tokens\":51,\"total_tokens\":83}}";
        callbackStoryScript(jsonString);
    }

    public void requestStoryScript() {
        String postData = Story.createStoryBody();
        Requests r = new Requests(Credentials.OPENAPI_TEXT_COMPLETION_URL, Credentials.OPENAPI_KEY, postData, new Requests.Callback() {
            @Override
            public void onRequestComplete(String response) {
                callbackStoryScript(response);
            }
        });
        r.execute();
    }

    public void callbackStoryScript(String response) {
        try {
            Log.wtf("Response:", response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray choicesArray = jsonObject.getJSONArray("choices");
            JSONObject choiceObject = choicesArray.getJSONObject(0);
            String text = choiceObject.getString("text");
            int index = text.indexOf("\n\n");
            text = text.substring(index + 2);
            String[] substrings = text.split("\n");

            Story.storyScript = new ArrayList<>();
            for (int i = 0; i < substrings.length; i += 2) {
                if (i + 1 < substrings.length) {
                    String joinedLine = substrings[i] + "\n" + substrings[i + 1];
                    Story.storyScript.add(joinedLine);
                }
            }

            //requestStoryImage();
            fakeRequestStoryImage();
        } catch (Exception e) {
            Log.e("callbackStoryScript", e.toString());
            btnCreateStory.setEnabled(true);
        }
    }

    public void fakeRequestStoryImage() {
        Log.e("Test", Story.storyScript.toString());
        requestImageDownloader();
    }

    public void requestStoryImage() {
        String postData = Story.createImageBody();
        Requests r = new Requests(Credentials.OPENAPI_IMAGE_GENERATOR_URL, Credentials.OPENAPI_KEY, postData, new Requests.Callback() {
            @Override
            public void onRequestComplete(String response) {
                callbackStoryImage(response);
            }
        });
        r.execute();
    }

    public void callbackStoryImage(String response) {
        try {
            Log.wtf("Response:", response);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject firstElement = dataArray.getJSONObject(0);
            String imageUrl = firstElement.getString("url");
            Log.e("URL", imageUrl);
            this.imageUrl = imageUrl;
            requestImageDownloader();
        } catch (Exception e) {
            Log.e("callbackStoryImage", e.toString());
            btnCreateStory.setEnabled(true);
        }
    }

    public void requestImageDownloader() {
        ImageDownloader imageDownloader = new ImageDownloader(getApplicationContext(), new ImageDownloader.Callback() {
            @Override
            public void onRequestComplete() {
                callbackImageDownloader();
            }
        });
        imageDownloader.execute(imageUrl);
    }

    public void callbackImageDownloader() {
        Intent intent = new Intent(ToyListActivity.this, StoryBookActivity.class);
        startActivity(intent);
    }
}