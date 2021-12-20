package com.example.individualproject3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;



public class GameView extends View {
    private int level;
    private Paint mPaint;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }
    public void setLevel(int l) {
        this.level = l;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int randomColor = Color.argb(255, 0, 0,0);
        mPaint.setColor(randomColor);
        mPaint.setStrokeWidth(120);
        mPaint.setTextSize(50);
        if (level==1) {
            canvas.drawLine(0, 100, 530, 100, mPaint);
            canvas.drawLine(470, 100, 470, 600, mPaint);
            canvas.drawLine(410, 600, 1000, 600, mPaint);
        }
        if (level==2) {
            canvas.drawLine(80, 800, 80, 300, mPaint);
            canvas.drawLine(20, 300, 660, 300, mPaint);
            canvas.drawLine(600, 300, 600, 0, mPaint);
        }

        if (level==3) {
            canvas.drawLine(80, 0, 80, 600, mPaint);
            canvas.drawLine(20, 600, 700, 600, mPaint);
            canvas.drawLine(640, 600, 640, 0, mPaint);
        }

    }
}
