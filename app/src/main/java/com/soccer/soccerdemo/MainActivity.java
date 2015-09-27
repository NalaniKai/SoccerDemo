package com.soccer.soccerdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    Team currentTeam; //current team
    Player currentPlayer; //current player

    String team_selected; //team selected in team spinner

    private Button player_view; //btn to change to PlayersActivity
    private Button add_player; //btn to add a new player
    private Button add_team; //btn to add a new team

    //player stats when adding a new player
    private EditText player_goals;
    private EditText player_red;
    private EditText player_yellow;
    private EditText player_position;
    private EditText player_name;

    private EditText team_name; //new team name

    private ImageView team_logo; //team logo

    HashMap<String, Player> players; //stores players

    HashMap<String, Team> teams; //teamName = key; stores teams
    ArrayList<String> team_names; //team names
    ArrayAdapter<String> adapter_teams; //adapter for team spinner
    Spinner spinner_teams; //spinner to display teams

    Spinner spinner_positions; //spinner to display soccer positions
    ArrayAdapter<String> adapter_positions; //adapter for player positions
    ArrayList<String> team_positions; //positions of players in a team

    //Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent = new Intent(MainActivity.this,PlayersActivity.class);

        //initialize buttons and connect to view
        player_view = (Button) findViewById(R.id.team_players);
        add_player = (Button) findViewById(R.id.add_player);
        add_team = (Button) findViewById(R.id.add_team);

        //initialize text fields for new players and connect to view
        player_goals = (EditText) findViewById(R.id.player_goals);
        player_red = (EditText) findViewById(R.id.player_red);
        player_yellow = (EditText) findViewById(R.id.player_yellow);
        player_name = (EditText) findViewById(R.id.player_name);
        player_position = (EditText) findViewById(R.id.player_position);

        //initialize text field for new team and image view for logo
        team_name = (EditText) findViewById(R.id.team_name);
        team_logo = (ImageView) findViewById(R.id.team_logo);

        //initialize spinners for teams and positions
        spinner_teams = (Spinner) findViewById(R.id.spinner_teams);
        spinner_positions = (Spinner) findViewById(R.id.spinner_positions);

        team_names = new ArrayList<>(); //initialize team names

        teams = new HashMap<>(); //initialize teams hashmap
        players = new HashMap<>(); //initialize player hashmap

        //start with players in hashmap
        currentPlayer = new Player("Lily", "Striker", "Kittens", 8, 0 , 1);
        players.put("Lily", currentPlayer);
        currentPlayer = new Player("Sam", "Mid-field", "Meow", 12, 1, 2);
        players.put("Sam", currentPlayer);
        currentPlayer = new Player("Linda", "Defender", "Meow", 1, 1, 1);
        players.put("Linda", currentPlayer);

        //add team names to array list
        team_names.add("Meow");
        team_names.add("Kittens");

        //start with teams in hashmap
        currentTeam = new Team("Meow", team_logo);
        teams.put("Meow", currentTeam);
        currentTeam = new Team("Kittens", team_logo);
        teams.put("Kittens", currentTeam);

        //set listeners
        add_team.setOnClickListener(new addTeamListener());
        add_player.setOnClickListener(new addPlayerListener());
        spinner_teams.setOnItemSelectedListener(new teamSelectedListener());
        player_view.setOnClickListener(new playerViewListener());

        adapter_teams = new ArrayAdapter<>(this,    //initialize adapter for teams
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_names);

        //connect team adapter to team spinner
        adapter_teams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teams.setAdapter(adapter_teams);
    }

    /*
     * class: teamSelectedListener          Listens for selected item in team spinner.
     */
    public class teamSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            team_selected = parent.getItemAtPosition(position).toString(); //get selected item

            displayPositions();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class addPlayerListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            addPlayer(player_name.getText().toString());
        }
    }

    private void addPlayer(String name) {
        //get player stats from gui
        currentPlayer = new Player(name,
                player_position.getText().toString(),
                team_selected, Integer.parseInt(player_goals.getText().toString()),
                Integer.parseInt(player_red.getText().toString()),
                Integer.parseInt(player_yellow.getText().toString()));

        players.put( name, currentPlayer); //add new player

        displayPositions(); //update positions in a team
    }

    public void displayPositions() {
        team_positions = new ArrayList<>(); //initialize team positions

        Set<String> keys = players.keySet(); //get all keys for players

        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team_selected)) {
                team_positions.add(players.get(k).getPosition()); //get all positions
            }
        }

        adapter_positions = new ArrayAdapter<>(this, //initialize adapter for positions
                android.R.layout.simple_list_item_1,
                android.R.id.text1, team_positions);

        adapter_positions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_positions.setAdapter(adapter_positions);
    }

    public class playerViewListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,PlayersActivity.class);
            //intent.putStringArrayListExtra(team_names);
            startActivity(intent);
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
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_teams.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(team_names);
        spinnerAdapter.notifyDataSetChanged();

        currentTeam = new Team(teamName, team_logo); //create new team

        teams.put(teamName, currentTeam); //add team to hashmap

        //intent.putStringArrayListExtra("", team_names);
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
