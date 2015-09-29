package com.soccer.soccerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

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
}
