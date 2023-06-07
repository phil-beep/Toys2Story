package com.example.toys2story;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {
    private Paint paint;
    private Path path;
    private float curX, curY;
    private static final float TOUCH_TOLERANCE = 4;
    private final int TARGET_RESOLUTION = 128;

    public ArrayList<ArrayList<ArrayList<Integer>>> coords = new ArrayList<>();
    ArrayList<ArrayList<Integer>> currentStrokes = new ArrayList<>();
    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);

        currentStrokes.add(x);
        currentStrokes.add(y);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    private void touchDown(float x, float y) {
        path.moveTo(x, y);
        curX = x;
        curY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - curX);
        float dy = Math.abs(y - curY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(curX, curY, (x + curX) / 2, (y + curY) / 2);
            curX = x;
            curY = y;

            currentStrokes.get(0).add(normalize(curX));
            currentStrokes.get(1).add(normalize(curY));

            ArrayList<ArrayList<Integer>> strokesCopy = new ArrayList<>(currentStrokes.size());
            for (ArrayList<Integer> stroke : currentStrokes) {
                ArrayList<Integer> strokeCopy = new ArrayList<>(stroke);
                strokesCopy.add(strokeCopy);
            }
            coords.add(strokesCopy);
        }
    }

    private void touchUp() {
        path.lineTo(curX, curY);

        currentStrokes.get(0).clear();
       currentStrokes.get(1).clear();
    }

    public void resetDrawing() {
        path.reset();
        coords.clear();
        invalidate();
    }

    private int normalize(double coord) {
        return (int) (Math.floor(Math.floor(coord) / this.getWidth() * TARGET_RESOLUTION));
    }
}
