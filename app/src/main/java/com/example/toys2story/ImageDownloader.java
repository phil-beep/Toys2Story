package com.example.toys2story;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, String> {

    private Context context;
    private Callback callback;

    public ImageDownloader(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public interface Callback {
        void onRequestComplete();
    }

    @Override
    protected String doInBackground(String... params) {
        String imageUrl = params[0];
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            String filename = "image.png";
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return filename;
        } catch (Exception e) {
            Log.e("ImageDownloader", "Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            File file = new File(context.getFilesDir(), result);
            Story.bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            callback.onRequestComplete();
        } else {
            Log.e("Error", "Empty result");
        }
    }
}
