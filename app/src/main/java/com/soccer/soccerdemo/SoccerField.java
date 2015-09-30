package com.soccer.soccerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by chunm18 on 9/27/2015.
 */
public class SoccerField extends SurfaceView{
    public SoccerField(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        //create green color
        Paint green = new Paint();
        green.setColor(Color.GREEN);

        //create white color
        Paint white = new Paint();
        white.setColor(Color.WHITE);

        //create and draw green soccer field
        Rect field = new Rect();
        field.set(0, 0, c.getWidth(), c.getHeight());
        c.drawRect(field, green);

        //draw center field lines
        c.drawCircle(c.getWidth() / 2, c.getHeight() / 2, 140, white);
        c.drawCircle(c.getWidth() / 2, c.getHeight() / 2, 130, green);
        c.drawCircle(c.getWidth() / 2, c.getHeight() / 2, 20, white);
        Rect mid_line = new Rect();
        mid_line.set((c.getWidth() / 2) - 5, 0, (c.getWidth() / 2) + 5, c.getHeight());
        c.drawRect(mid_line, white); //draw mid line

        //create and draw left goal box top
        Rect leftBoxTop = new Rect();
        leftBoxTop.set(0, (c.getHeight() / 4) - 20, c.getWidth() / 6, (c.getHeight() / 4) - 10);
        c.drawRect(leftBoxTop, white);

        //create and draw left goal box bottom
        Rect leftBoxBottom = new Rect();
        leftBoxBottom.set(0, 3 * (c.getHeight() / 4) + 10, c.getWidth() / 6, 3 * (c.getHeight() / 4) + 20);
        c.drawRect(leftBoxBottom, white);

        //create and draw left goal box front
        Rect leftBox = new Rect();
        leftBox.set((c.getWidth()/6)-5, (c.getHeight()/4)-20, (c.getWidth()/6)+5, 3*(c.getHeight()/4)+20);
        c.drawRect(leftBox, white);

        //create and draw right goal box top
        Rect rightBoxTop = new Rect();
        rightBoxTop.set( 5*(c.getWidth()/6), (c.getHeight()/4)-20, c.getWidth(), (c.getHeight()/4)-10);
        c.drawRect(rightBoxTop, white);

        //create and draw right goal box bottom
        Rect rightBoxBottom = new Rect();
        rightBoxBottom.set(5*(c.getWidth()/6), 3*(c.getHeight()/4)+10, c.getWidth(), 3*(c.getHeight()/4)+20);
        c.drawRect(rightBoxBottom, white);

        //create and draw right goal box front
        Rect rightBox = new Rect();
        rightBox.set(5*(c.getWidth()/6)-5, (c.getHeight()/4)-20, 5*(c.getWidth()/6)+5, 3*(c.getHeight()/4)+20);
        c.drawRect(rightBox, white);
    }

}
