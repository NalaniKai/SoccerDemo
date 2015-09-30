package com.soccer.soccerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import org.w3c.dom.Text;

/**
 * Created by chunm18 on 9/28/2015.
 */
public class PlayersInGame extends SurfaceView {
    public PlayersInGame(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    public void drawPlayer(Canvas c, float x, float y, String name) {
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);

        //draw blue dot representing player
        c.drawCircle(x, y, 20, blue);
        c.drawText(name, x+40, y, blue);
    }
}
