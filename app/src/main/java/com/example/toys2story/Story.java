package com.example.toys2story;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Story {
    public static final int STORYBOARDS = 3;
    public static ArrayList<String> toys = new ArrayList<>();
    public static Bitmap bitmap;
    private static final String AI_STORY_PROMPT = "Write a " + STORYBOARDS * 2 +"-line children's story with a rhyming pattern. Each pair of lines should rhyme. Include the following objects: ";
    private static final String AI_IMAGE_PROMPT = "Draw the following story in a children's book art style: ";
    public static ArrayList<String> storyScript;
   private static final int MAX_TOKENS = 100;
    public Story() {}

    public static String createStoryBody() {

        String prompt = AI_STORY_PROMPT + arrayToString(toys);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "text-davinci-003");
            jsonObject.put("prompt", prompt);
            jsonObject.put("max_tokens", MAX_TOKENS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String postData = jsonObject.toString();

        return postData;
    }

    private static String arrayToString(ArrayList<String> toys) {
        String toyString = toys.toString();
        toyString = toyString.replace("[", "");
        toyString = toyString.replace("]", "");
        return toyString;
    }

    public static String createImageBody() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("prompt", AI_IMAGE_PROMPT + storyScript.toString());
            jsonObject.put("n", 1);
            jsonObject.put("size", "256x256");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String postData = jsonObject.toString();
        return postData;
    }
}
