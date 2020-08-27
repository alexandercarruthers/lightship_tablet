package com.example.lightship_tablet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    Button mapsButton;
    Button subsampleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapsButton=(Button)findViewById(R.id.mapButton);
        subsampleButton=(Button)findViewById(R.id.subsampleButton);


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                // All your networking logic
//                // should be here
//                // Create URL
//                try {

////                    test();
////                    getImage();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        ImageView i = (ImageView) findViewById(R.id.imageView);
//        i.getLayoutParams().height = 1181;
//        i.getLayoutParams().width = 3309;
//        i.requestLayout();


    }



    private void mapsIntent(){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(intent);
    }




//
//    private void getImage() {
//        ImageView i = null;
//        try {
//            String imageUrl = "http://10.0.2.2:8000/media/decks/DECK1-1_yDzBpAA.png";
//            i = (ImageView) findViewById(R.id.imageView);
//            bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
//            i.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            Log.i("a", "fail");
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i("a", "fail");
//        }
//
//    }

    private void test() throws IOException {
        // Create URL
        //http://localhost:8000/api/v1/draughtreading/14
        //http://10.0.2.2:8000/api/v1/draughtreading/14
        String url = "http://10.0.2.2:8000/api/v1/draughtreading/14";
        //"https://api.github.com/"
        URL githubEndpoint = new URL(url);

// Create connection
        HttpURLConnection myConnection =
                (HttpURLConnection) githubEndpoint.openConnection();

        //myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
        Log.i("a", String.valueOf(myConnection.getResponseCode()));
        if (myConnection.getResponseCode() == 200) {
            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader =
                    new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            jsonReader.beginObject(); // Start processing the JSON object
            Log.i("a", "200 ok");
            while (jsonReader.hasNext()) { // Loop through all keys
                String key = jsonReader.nextName(); // Fetch the next key
                Log.i("a", key);
                if (key.equals("organization_url")) { // Check if desired key
                    String value = jsonReader.nextString();
                    break; // Break out of the loop
                } else {
                    jsonReader.skipValue(); // Skip values of other keys
                }

            }
            jsonReader.close();
            myConnection.disconnect();
        } else {
            Log.i("a", "1");
            // Error handling code goes here
        }

        Log.i("a", "com/example/lightship_tablet/Models");


        return;
    }

    public void mapsIntent(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(intent);
    }
    public void subsampleIntent(View view) {
        Intent intent = new Intent(MainActivity.this, Subsample.class);
        MainActivity.this.startActivity(intent);
    }
}