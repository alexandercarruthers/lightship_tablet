package com.example.lightship_tablet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
                // Create URL
                try {
                    test();
                    getImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.getLayoutParams().height = 1181;
        i.getLayoutParams().width = 3309;
        i.requestLayout();
        setListener();
    }

    private void setListener() {
        ImageView i = (ImageView) findViewById(R.id.imageView);
        i.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                int x = (int)event.getX();
                int y = (int)event.getY();
                Log.i("x y", String.valueOf(x) + " " + String.valueOf((y*-1) + 1181)); //reflect on Y axis and add image height

                return false;
            }
        });

    }

    private void getImage() {
        ImageView i = null;
        try {
            String imageUrl = "http://10.0.2.2:8000/media/decks/DECK1-1_yDzBpAA.png";
            i = (ImageView) findViewById(R.id.imageView);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            i.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("a", "fail");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("a", "fail");
        }

    }

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

        Log.i("a", "2");


        return;
    }

}