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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class FieldActivity extends ActionBarActivity {

    //buttons to return to the main activity and play the game
    private Button stats;
    private Button play;

    //spinners for teams
    private Spinner team1;
    private Spinner team2;

    //spinners for players
    private Spinner players1;
    private Spinner players2;

    //arrayList of teams and players
    protected ArrayList<String> team_list;
    protected ArrayList<String> player_list;

    //array adapter for teams and players
    protected ArrayAdapter<String> adapter_teams;
    protected ArrayAdapter<String> adapter_players;

    HashMap<String, Player> players; //hashmap of players

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        players = new HashMap<>(); //initialize hashmap of players

        //initialize array list for teams and players
        team_list = new ArrayList<>();
        player_list = new ArrayList<>();

        stats = (Button) findViewById(R.id.return_stats); //initialize button to go back to stats page

        play = (Button) findViewById(R.id.play); //initialize button to play game

        //initialize spinners for teams
        team1 = (Spinner) findViewById(R.id.spinner_team1);
        team2 = (Spinner) findViewById(R.id.spinner_team2);

        //initialize spinners for players
        players1 = (Spinner) findViewById(R.id.spinner_players1);
        players2 = (Spinner) findViewById(R.id.spinner_players2);

        Intent intent = getIntent(); //get intent

        team_list = intent.getStringArrayListExtra("teams"); //get team name array list
        players = (HashMap<String, Player>) intent.getSerializableExtra("players"); //get players

        //set adapter and connect to team spinners
        adapter_teams = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter_teams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team1.setAdapter(adapter_teams);
        team2.setAdapter(adapter_teams);

        //set on click listeners for buttons
        stats.setOnClickListener(new returnToStatsListener());
        play.setOnClickListener(new playListener());
        team1.setOnItemSelectedListener(new team1Listener());
        team2.setOnItemSelectedListener(new team2Listener());
    }

    /*
    * class: team2Listener      Listens for selected team on right side of screen.
    */
    public class team2Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedTeam = parent.getItemAtPosition(position).toString(); //get selected team

            Set<String> keys = players.keySet(); //get all player keys

            //check for players in team
            for(String k: keys) {
                if(players.get(k).getTeamName().equals(selectedTeam)) {
                    player_list.add(k); //stores players in selected team
                }
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * class: team1Listener      Listens for selected team on left side of screen.
     */
    private class team1Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * class: playListener      Listens for when user clicks the play button.
     */
    public class playListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int winner = (int) (Math.random()*2.0); //chooses random winner
        }
    }

    /*
     * class: returnToStatsListener         Returns to MainActivity when user clicks stats button.
     */
    public class returnToStatsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish(); //closes current view
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
