package com.example.lightship_tablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.lightship_tablet.Models.Item;

import java.util.ArrayList;
import java.util.Iterator;

public class PinView extends SubsamplingScaleImageView {


    private ArrayList<Item> items = new ArrayList<>();
    private Bitmap pin;
    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public boolean setPin(Item newItem) {
            items.add(newItem);
            initialise();
            invalidate();
            return true;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public void removeItem(int id){
        for(Iterator<Item> iterator = items.iterator(); iterator.hasNext(); ) {
            if(iterator.next().getId() == id)
                iterator.remove();
        }
        initialise();
        invalidate();
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.forty);
        float w = (density/420f) * pin.getWidth();
        float h = (density/420f) * pin.getHeight();
        pin = Bitmap.createScaledBitmap(pin, (int)w, (int)h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }
        Paint paint = new Paint();
        paint.setTextSize(36);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        for (Item current_item : items){
            if (current_item != null && pin != null) {
                PointF vPin = sourceToViewCoord(current_item.point);
                float vX = vPin.x - (pin.getWidth()/2);
                float vY = vPin.y - pin.getHeight();
                canvas.drawBitmap(pin, vX, vY, paint);
                canvas.drawText(current_item.toString(), vX, vY, paint);
            }
        }
    }
}