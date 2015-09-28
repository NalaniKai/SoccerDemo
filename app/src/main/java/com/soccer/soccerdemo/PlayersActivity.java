package com.soccer.soccerdemo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class PlayersActivity extends ActionBarActivity {

    //buttons
    private Button statsBtn;
    private ImageButton right;
    private ImageButton left;

    //spinner drop down for teams
    private Spinner spinner1Team;
    private Spinner spinner2Team;

    //member spinners
    private Spinner spinnerMemb1;
    private Spinner spinnerMemb2;

    ArrayAdapter<String> adapterTeam; //adapter for team spinners
    ArrayList<String> team_list; //holds team names

    //adapters for players
    ArrayAdapter<String> adapterPlayers1;
    ArrayAdapter<String> adapterPlayers2;

    //ArrayLists for players
    ArrayList<String> player1List;
    ArrayList<String> player2List;

    HashMap<String, Player> players; //players hashmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //initialize team spinners
        spinner1Team = (Spinner) findViewById(R.id.spinner_team1);
        spinner2Team = (Spinner) findViewById(R.id.spinner_team2);

        //members spinners
        spinnerMemb1 = (Spinner) findViewById(R.id.spinner_members1);
        spinnerMemb2 = (Spinner) findViewById(R.id.spinner_members2);

        //initialize button to go back to MainActivity
        statsBtn = (Button) findViewById(R.id.statsButton);

        //buttons to exchange players between teams
        right = (ImageButton) findViewById(R.id.arrow_right);
        left = (ImageButton) findViewById(R.id.arrow_left);

        team_list = new ArrayList<>(); //initialize array list for teams

        players = new HashMap<>(); //initialize players hashmap

        Intent intent = getIntent(); //get intent

        team_list = intent.getStringArrayListExtra("hi"); //get array list of team names
        players = (HashMap<String, Player>) intent.getSerializableExtra("players"); //get player hashmap

        //initialize adapter with list of team names and connect to spinners
        adapterTeam = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapterTeam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1Team.setAdapter(adapterTeam);
        spinner2Team.setAdapter(adapterTeam);

        //set listeners
        statsBtn.setOnClickListener(new statsListener());
        spinner1Team.setOnItemSelectedListener(new s1TeamListener());
        spinner2Team.setOnItemSelectedListener(new s2TeamListener());
    }

    /*
     * class: s2Listener            Listens for selected item in spinner on the right side of the
     *                              screen and displays the players in that team.
     */
    private class s2TeamListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedTeam = parent.getItemAtPosition(position).toString(); //get selected team

            displayPlayers2(selectedTeam); //display players in team 2
            String playerNames = ""; //string to hold player names

            Set<String> keys = players.keySet(); //get all player keys

            //check for players in team
            for(String k: keys) {
                if(players.get(k).getTeamName().equals(selectedTeam)) {
                    playerNames += k + "\n"; //stores players in selected team
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers2          Displays players in team selected on right drop down.
     */
    public void displayPlayers2 (String team) {
        player2List = new ArrayList<>(); //new arrayList for players

        Set<String> keys = players.keySet(); //get keys for all players

        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team)) {
                player2List.add(k);
            }
        }

        adapterPlayers2 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                player2List);
        adapterPlayers2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemb2.setAdapter(adapterPlayers2);
    }

    /*
     * class: s1Listener            Listens for selected item in spinner on the left side of the
     *                              screen and displays the players in that team.
     */
    private class s1TeamListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedTeam = parent.getItemAtPosition(position).toString(); //get selected team

            displayPlayers1(selectedTeam); //display players in team
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers           Displays players in left drop down for members.
     */
    public void displayPlayers1(String team) {
        player1List = new ArrayList<>(); //new player list

        Set<String> keys = players.keySet(); //gets all player keys

        //check for players in team
        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team)) {
                player1List.add(k); //gets players in team
            }
        }

        //set adapter and spinner for players
        adapterPlayers1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                player1List);
        adapterPlayers1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemb1.setAdapter(adapterPlayers1);
    }

    /*
     *  class: statsListener            Goes back to MainActivity view and closes out of current view.
     */
    private class statsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        } //closes current view and returns to the main view

        /*
                send data back from activity

        @override
        onActivityResult(ssdfs) {
            if(requestCode == 100) {
                if(resultCode == 1) {
                    data.getStringExtra("goback");
                }
            }

        }

        before finish
        intent.putExtra("goback", "yay");
        setResult(1);
        finish();
         */
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
