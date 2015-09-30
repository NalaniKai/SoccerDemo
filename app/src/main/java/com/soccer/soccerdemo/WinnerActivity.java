package com.soccer.soccerdemo;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/* Programmer: Nalani (Megan Chun)
 * Last Updated: Sept. 29, 2015
 *
 * class: WinnerActivity                    Displays the winner of the game and has moving shapes
 *                                          when the user taps the screen.
 */
public class WinnerActivity extends ActionBarActivity {

    private WinnerCelebration win; //surface view to draw shapes
    private Canvas c; //canvas
    private String team; //team name of winner

    //text views to display winning team
    private TextView name;
    private TextView name2;
    private TextView name3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        //initialize surface view and canvas
        win = (WinnerCelebration) findViewById(R.id.winner_view);
        c = new Canvas();

        //initialize textviews for winner name
        name = (TextView) findViewById(R.id.team_name);
        name2 = (TextView) findViewById(R.id.name1);
        name3 = (TextView) findViewById(R.id.name2);


        Intent i = getIntent(); //get intent
        team = i.getStringExtra("winner"); //get team that won

        //display team name
        name.setText(team + " won!!!");
        name2.setText(team + " won!!!");
        name3.setText(team + " won!!!");

        win.setZOrderOnTop(true); //set view on top
        SurfaceHolder holder = win.getHolder(); //create holder
        holder.setFormat(PixelFormat.TRANSPARENT); //make surface view transparent

        win.setOnTouchListener(new touchListener()); //set listener to move shapes upon touch

        //set timer for activity
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish(); //return to Field Activity
            }
        }, 5000);
    }

    /*
     * class: touchListener                 Listens for when user touches screen and redraws shapes
     *                                      in different colors and sizes in different postions.
     */
    public class touchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            c = win.getHolder().lockCanvas(); //lock canvas
            win.move(c); //moves shapes
            win.getHolder().unlockCanvasAndPost(c); //unlock canvas
            win.postInvalidate();

            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_winner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
