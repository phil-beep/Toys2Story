package com.example.toys2story;

import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Requests extends AsyncTask<String, Void, String> {

    private static final String TAG = "PostRequestAsyncTask";

    private String url;
    private String postData;
    private String creds;
    private Callback callback;

    public Requests(String url, String creds, String postData, Callback callback) {
        this.url = url;
        this.postData = postData;
        this.callback = callback;
        this.creds = creds;
    }

    public interface Callback {
        void onRequestComplete(String response);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", creds);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            Log.wtf(TAG, "Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.wtf(TAG, "Error closing stream: " + e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            callback.onRequestComplete(response);
        } catch (Exception e){
            Log.e("Error", e.toString());
        }
    }
}