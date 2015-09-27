package com.soccer.soccerdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;


public class FieldActivity extends ActionBarActivity {

    //buttons
    private Button stats;
    private Button play;

    //spinners for teams
    private Spinner team1;
    private Spinner team2;

    //arrayList of teams
    private ArrayList<String> team_list;

    //array adapter for teams
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        //initialize array list for teams
        team_list = new ArrayList<>();

        Intent intent = getIntent(); //get intent

        team_list = intent.getStringArrayListExtra("hi"); //get team name array list

        //initialize button to go back to stats page
        stats = (Button) findViewById(R.id.return_stats);

        //initialize button to play game
        play = (Button) findViewById(R.id.play);

        //initialize spinners for teams
        team1 = (Spinner) findViewById(R.id.spinner_team1);
        team2 = (Spinner) findViewById(R.id.spinner_team2);

        //set adapter and connect to team spinners
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team1.setAdapter(adapter);
        team2.setAdapter(adapter);

        //set on click listeners for buttons
        stats.setOnClickListener(new returnToStatsListener());
        play.setOnClickListener(new playListener());
    }

    public class playListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int winner = (int) (Math.random()*2.0);
        }
    }

    public class returnToStatsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_field, menu);
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
