package com.soccer.soccerdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    private Button player_view; //btn to change to PlayersActivity
    private Button add_player; //btn to add a new player
    private Button add_team; //btn to add a new team

    //player stats when adding a new player
    private EditText player_goals;
    private EditText player_red;
    private EditText player_yellow;
    private EditText player_name;

    private EditText team_name; //new team name

    private ImageView team_logo; //team logo

    HashMap<String, Team> teams; //teamName = key;
    ArrayList<String> team_names; //team names
    ArrayAdapter<String> adapter_teams; //spinner adapter for teams
    Spinner spinner_teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teams = new HashMap<>();
        spinner_teams = (Spinner) findViewById(R.id.spinner_teams);
        team_names = new ArrayList<>();
        adapter_teams = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_names.toArray(new String[0]));
        adapter_teams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teams.setAdapter(adapter_teams);

        //initialize variables
        player_view = (Button) findViewById(R.id.team_players);
        add_player = (Button) findViewById(R.id.add_player);
        add_team = (Button) findViewById(R.id.add_team);

        player_goals = (EditText) findViewById(R.id.player_goals);
        player_red = (EditText) findViewById(R.id.player_red);
        player_yellow = (EditText) findViewById(R.id.player_yellow);
        player_name = (EditText) findViewById(R.id.player_name);

        team_name = (EditText) findViewById(R.id.team_name);
        team_logo = (ImageView) findViewById(R.id.team_logo);

        player_view.setOnClickListener(new playerViewListener());

        //set listeners
        add_team.setOnClickListener(new addTeamListener());
    }

    public class playerViewListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, PlayersActivity.class));
        }
    }

    public class addTeamListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            addTeam(team_name.getText().toString());
        }
    }

    private void addTeam(String teamName) {
        //don't do anything if team already exists
        if(team_names.indexOf(teamName) >= 0) return;

        //add team name and resort
        team_names.add((teamName));
        Collections.sort(team_names);

        //create new adapter and add updated elements
        //connect to spinner
        ArrayAdapter<String> spinnerAdaper =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,
                        android.R.id.text1);
        spinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teams.setAdapter(spinnerAdaper);
        spinnerAdaper.addAll(team_names);
        spinnerAdaper.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
