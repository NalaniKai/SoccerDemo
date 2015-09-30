package com.soccer.soccerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by chunm18 on 9/29/2015.
 */
public class WinnerCelebration extends SurfaceView {
    public WinnerCelebration(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas c) {

        super.onDraw(c);

        //random color
        Paint col = new Paint();
        col.setARGB(0xff, getColorval(), getColorval(), getColorval());

        c.drawCircle(xPosition(), yPosition(), getRadius(), col);

        col.setARGB(0xff, getColorval(), getColorval(), getColorval());
        c.drawCircle(xPosition(), yPosition(), getRadius(), col);

        col.setARGB(0xff, getColorval(), getColorval(), getColorval());
        c.drawCircle(xPosition(), yPosition(), getRadius(), col);

        Rect rect = new Rect();
        rect.set(xPosition(), yPosition(), xPosition(), yPosition());
        c.drawRect(rect, col);
    }

    public void move(Canvas c) {

        //random color
        Paint col = new Paint();
        col.setARGB(0xff, getColorval(), getColorval(), getColorval());

        c.drawCircle(xPosition(), yPosition(), getRadius(), col);

        col.setARGB(0xff, getColorval(), getColorval(), getColorval());
        c.drawCircle(xPosition(), yPosition(), getRadius(), col);

        Rect rect = new Rect();
        rect.set(xPosition(), yPosition(), xPosition(), yPosition());
        c.drawRect(rect, col);
    }

    private int getRadius() {
        return (int) (Math.random()*100);
    }

    private int getColorval() {
        return (int) (Math.random() * 255);
    }

    private int yPosition() {
        return (int) (Math.random()*getHeight());
    }

    private int xPosition() {
        return (int) (Math.random()*getWidth());
    }
}
