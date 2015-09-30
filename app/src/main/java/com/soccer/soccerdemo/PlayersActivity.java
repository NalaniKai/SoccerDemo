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

/* Programmer: Nalani (Megan Chun)
 * Last Updated: Sept. 29, 2015
 *
 * class: PlayersActivity           User can view the different teams and players on each team.
 *                                  The user can also move players between teams. Data gets passed
 *                                  back to MainActivity after finishing.
 */
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

    //players
    String mem1;
    String mem2;

    //teams
    String team1;
    String team2;

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

        //initialize buttons
        statsBtn = (Button) findViewById(R.id.statsButton);
        left = (ImageButton) findViewById(R.id.arrow_left);
        right = (ImageButton) findViewById(R.id.arrow_right);

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
        statsBtn.setOnClickListener(new statsListener()); //button to return to MainActivity
        spinner1Team.setOnItemSelectedListener(new s1TeamListener()); //team on left
        spinner2Team.setOnItemSelectedListener(new s2TeamListener()); //team on right
        spinnerMemb1.setOnItemSelectedListener(new player1Listener()); //players on left
        spinnerMemb2.setOnItemSelectedListener(new player2Listener()); //players on right
        left.setOnClickListener(new leftListener()); //players from right to left
        right.setOnClickListener(new rightListener()); //players from left to right
    }

    /*
     * class: player1Listener              Gets the player selected in the list on the left.
     */
    public class player1Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mem1 = parent.getItemAtPosition(position).toString(); //selected player
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * class: leftListener          Moves the player on the right to the team on the left.
     */
    public class leftListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            players.get(mem2).setTeamName(team1);

            displayPlayers2();
            displayPlayers1();
        }
    }

    /*
     * class: player2Listener       Gets the player selected on the right.
     */
    public class player2Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mem2 = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * class: rightListener         Moves the player on the left to the team on the right.
     */
    public class rightListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            players.get(mem1).setTeamName(team2);

            displayPlayers1(); //update left player spinner
            displayPlayers2(); //update right player spinner
        }
    }

    /*
     * class: s2Listener            Listens for selected item in spinner on the right side of the
     *                              screen and displays the players in that team.
     */
    private class s2TeamListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            team2 = parent.getItemAtPosition(position).toString(); //get selected team

            displayPlayers2(); //display players in team 2
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers2          Displays players in team selected on right drop down.
     */
    public void displayPlayers2 () {
        player2List = new ArrayList<>(); //new arrayList for players

        Set<String> keys = players.keySet(); //get keys for all players

        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team2)) {
                player2List.add(k); //add players to spinner on right
            }
        }

        //set adapter and connect to spinner
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
            team1 = parent.getItemAtPosition(position).toString(); //get selected team

            displayPlayers1(); //display players in team
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers           Displays players in left drop down for members.
     */
    public void displayPlayers1() {
        player1List = new ArrayList<>(); //new player list

        Set<String> keys = players.keySet(); //gets all player keys

        //check for players in team
        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team1)) {
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
    public class statsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            i.putExtra("players updated", players);
            i.putExtra("hi", "test");
            setResult(1, i);
            finish(); //closes current view and returns to the main view
        }
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
