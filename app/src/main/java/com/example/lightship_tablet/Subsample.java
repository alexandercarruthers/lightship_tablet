package com.example.lightship_tablet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;


public class Subsample extends AppCompatActivity {

    int z = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsample);

//        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView1);
//        imageView.setImage(ImageSource.resource(R.drawable.deck1));


        final PinView imgView = findViewById(R.id.imageView1);
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (imgView.isReady()) {
                    PointF sCoord = imgView.viewToSourceCoord(e.getX(), e.getY());
                    Log.i("New Point", String.valueOf(sCoord.x) + " " + String.valueOf(sCoord.y));

                    if(collisionCheck(sCoord)){

                    }
                    else{
                        z = z + 1;
                        imgView.setPin(new PointF(sCoord.x,sCoord.y),String.valueOf( "test " + z));
                    }
                    // ...
                }
                return true;
            }
        });


        imgView.setImage(ImageSource.resource(R.drawable.deckno1));
        //imgView.setPin(new PointF(590f,85f));


        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

    }

    private boolean collisionCheck(PointF sCoord){
        PinView imgView = findViewById(R.id.imageView1);
        ArrayList<PointF> lst = imgView.getPinPoints();
        for(PointF point : lst) {
            //point is already on image
            Double distance = Math.hypot(point.x - sCoord.x, point.y - sCoord.y);
            if( distance < 16){ //16 PIXELS IS 1 FRAME
                Log.i("distance","collision");
                Log.i("test",imgView.getStringFromPoint(point));
                return true;
            }
        }
        return false;
    }


}