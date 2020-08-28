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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.lightship_tablet.Models.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Subsample extends AppCompatActivity {
    ArrayList<Point> myPoints;
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
                        myPoints = imgView.getPoints();
                        //addRow("test " + z);
                        updateRows();
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

    private void updateRows(){


        TableLayout tl = (TableLayout) findViewById(R.id.tlbItems);
        int count = tl.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = tl.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }


        for(Point point: myPoints){
            TableRow tr = new TableRow(this);
            TableRow.LayoutParams ly = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            TextView textViewItem = new TextView(this);
            textViewItem.setText(point.getTitle());
            textViewItem.setLayoutParams(ly);
            textViewItem.setLayoutParams(new TableRow.LayoutParams(1));
            tr.addView(textViewItem);

            TextView textViewWeight = new TextView(this);
            textViewWeight.setText("2.5");
            textViewWeight.setLayoutParams(ly); // weighting of 1
            textViewWeight.setLayoutParams(new TableRow.LayoutParams(2)); // add to col
            tr.addView(textViewWeight);

            Button b = new Button(this);
            b.setText("Update");
            b.setLayoutParams(ly); // weighting of 1
            b.setLayoutParams(new TableRow.LayoutParams(3)); // add to col
            tr.addView(b);

            Button c = new Button(this);
            c.setText("Delete");
            c.setLayoutParams(ly); // weighting of 1
            c.setLayoutParams(new TableRow.LayoutParams(4)); // add to col
            tr.addView(c);
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
    private void addRow(String name){
        TableLayout tl = (TableLayout) findViewById(R.id.tlbItems);
        TableRow tr = new TableRow(this);
        TableRow.LayoutParams ly = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

        TextView textViewItem = new TextView(this);
        textViewItem.setText(name);
        textViewItem.setLayoutParams(ly);
        textViewItem.setLayoutParams(new TableRow.LayoutParams(1));
        tr.addView(textViewItem);

        TextView textViewWeight = new TextView(this);
        textViewWeight.setText("2.5");
        textViewWeight.setLayoutParams(ly); // weighting of 1
        textViewWeight.setLayoutParams(new TableRow.LayoutParams(2)); // add to col
        tr.addView(textViewWeight);

        Button b = new Button(this);
        b.setText("Update");
        b.setLayoutParams(ly); // weighting of 1
        b.setLayoutParams(new TableRow.LayoutParams(3)); // add to col
        tr.addView(b);

        Button c = new Button(this);
        c.setText("Delete");
        c.setLayoutParams(ly); // weighting of 1
        c.setLayoutParams(new TableRow.LayoutParams(4)); // add to col
        tr.addView(c);
        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));


    }

    private boolean collisionCheck(PointF sCoord){
        PinView imgView = findViewById(R.id.imageView1);
        ArrayList<Point> lst = imgView.getPoints();
        for(Point point : lst) {
            //point is already on image
            Double distance = Math.hypot(point.point.x - sCoord.x, point.point.y - sCoord.y);
            if( distance < 16){ //16 PIXELS IS 1 FRAME
                Log.i("distance","collision");
                Log.i("test",point.point.x + " " + point.point.y);
                return true;
            }
        }
        return false;
    }


}