package com.example.toys2story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrawingActivity extends Activity {

    private boolean analyzed = false;
    TextView tv;
    Button btnAnalyze;
    Button btnDelete;
    DrawingView dv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        analyzed = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        tv = findViewById(R.id.textView);
        tv.setText("Draw your toy! " + (Story.toys.size() + 1) + "/" + Story.STORYBOARDS);

        dv = findViewById(R.id.drawingView);

        btnDelete = findViewById(R.id.deleteButton);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.resetDrawing();
            }
        });

        btnAnalyze = findViewById(R.id.analyzeButton);
        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Story.toys.size() < Story.STORYBOARDS) {
                    if(!dv.coords.isEmpty()) {
                        if (!analyzed) {
                            analyzed = true;
                            btnAnalyze.setEnabled(false);
                            btnDelete.setEnabled(false);
                            requestAnalyze();
                        } else {
                            Intent intent = new Intent(DrawingActivity.this, DrawingActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast tst = Toast.makeText(getApplicationContext(), "Canvas is empty!", Toast.LENGTH_SHORT);
                        tst.show();
                    }
                } else {
                    Intent intent = new Intent(DrawingActivity.this, ToyListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void requestAnalyze() {

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray(dv.coords);
            jsonObject.put("drawing", jsonArray);
            jsonObject.put("dots_connected", false);
            jsonObject.put("client", "hololens");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String postData = jsonObject.toString();

        Requests r = new Requests(Credentials.EXPLORAR_URL, "", postData, new Requests.Callback() {
            @Override
            public void onRequestComplete(String response) {
                callback(response);
            }
        });
        r.execute();
    }

    public void callback(String response) {
        btnAnalyze.setEnabled(true);
        btnDelete.setEnabled(true);
        response = response.replace("\"", "");
        Story.toys.add(response);
        tv.setText("That's a " + Story.toys.get(Story.toys.size() - 1));
        btnAnalyze.setText("Use " + Story.toys.size() + "/" + Story.STORYBOARDS);
        btnDelete.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        // Do nothing to prevent back button navigation
    }
}