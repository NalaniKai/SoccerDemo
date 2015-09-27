package com.soccer.soccerdemo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Set;


public class PlayersActivity extends ActionBarActivity {

    //buttons
    private Button statsBtn;
    private ImageButton right;
    private ImageButton left;

    //member lists
    private EditText memb1;
    private EditText memb2;

    //spinner drop down for teams
    private Spinner spinner1;
    private Spinner spinner2;

    ArrayAdapter<String> adapter; //adapter for team spinners
    ArrayList<String> team_list; //holds team names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        team_list = new ArrayList<>(); //initialize array list for teams

        Intent intent = getIntent(); //get intent

        team_list = intent.getStringArrayListExtra("hi"); //get array list of team names

        //initialize team spinners
        spinner1 = (Spinner) findViewById(R.id.spinner_team1);
        spinner2 = (Spinner) findViewById(R.id.spinner_team2);

        //initialize button to go back to MainActivity
        statsBtn = (Button) findViewById(R.id.statsButton);

        statsBtn.setOnClickListener(new statsListener()); //set listener

        //buttons to exchange players between teams
        right = (ImageButton) findViewById(R.id.arrow_right);
        left = (ImageButton) findViewById(R.id.arrow_left);

        //members list
        memb1 = (EditText) findViewById(R.id.membList1);
        memb2 = (EditText) findViewById(R.id.membList2);

        //initialize adapter with list of team names and connect to spinners
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
    }

    /*
     *  class: statsListener            Goes back to MainActivity view and closes out of current view.
     */
    private class statsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        } //closes current view and returns to the main view
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_players, menu);
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
