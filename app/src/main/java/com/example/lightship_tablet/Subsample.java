package com.example.lightship_tablet;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.lightship_tablet.Models.Item;
import java.util.ArrayList;


public class Subsample extends AppCompatActivity implements Dialog.DialogListener {
    ArrayList<Item> myItems;

    Item new_item;
    PointF sCoord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsample);

        final PinView imgView = findViewById(R.id.imageView1);
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (imgView.isReady()) {
                    sCoord = imgView.viewToSourceCoord(e.getX(), e.getY());
                    if(collisionCheck(sCoord)){
                    }
                    else{
                        openDialog();
                    }
                }
                return true;
            }
        });
        imgView.setImage(ImageSource.resource(R.drawable.deckno1));
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    private void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    private void updateRows(){
        TableLayout tl = (TableLayout) findViewById(R.id.tlbItems);
        int count = tl.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = tl.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        int z = 0;
        for(Item item : myItems){
            TableRow tr = new TableRow(this);
            TableRow.LayoutParams ly = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            TextView textViewItem = new TextView(this);
            textViewItem.setText(item.getTitle());
            textViewItem.setLayoutParams(ly);
            textViewItem.setLayoutParams(new TableRow.LayoutParams(1));
            tr.addView(textViewItem);

            TextView textViewWeight = new TextView(this);
            textViewWeight.setText(String.valueOf(item.getWeight()));
            textViewWeight.setLayoutParams(ly);
            textViewWeight.setLayoutParams(new TableRow.LayoutParams(2));
            tr.addView(textViewWeight);

            Button b = new Button(this);
            b.setText("Update");
            b.setLayoutParams(ly);
            b.setLayoutParams(new TableRow.LayoutParams(3));
            tr.addView(b);

            Button c = new Button(this);
            c.setId(item.getId());
            c.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.i("button","button has been clicked " + v.getId());
                    PinView imgView = findViewById(R.id.imageView1);
                    imgView.removeItem(v.getId());
                }
            });

            c.setText("Delete");
            c.setLayoutParams(ly);
            c.setLayoutParams(new TableRow.LayoutParams(4));
            tr.addView(c);
            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }



    private void buttonDelete(){


    }

    private boolean collisionCheck(PointF sCoord){
        PinView imgView = findViewById(R.id.imageView1);
        ArrayList<Item> lst = imgView.getItems();
        for(Item item : lst) {
            //point is already on image
            Double distance = Math.hypot(item.point.x - sCoord.x, item.point.y - sCoord.y);
            if( distance < 16){ //16 PIXELS IS 1 FRAME
                Log.i("distance","collision");
                Log.i("test", item.point.x + " " + item.point.y);
                return true;
            }
        }
        return false;
    }


    @Override
    public void applyTexts(String title, String weight) {
        new_item = new Item(new PointF(sCoord.x,sCoord.y), title,Float.valueOf(weight));
        PinView imgView = findViewById(R.id.imageView1);
        imgView.setPin(new_item);
        myItems = imgView.getItems();
        updateRows();
    }
}