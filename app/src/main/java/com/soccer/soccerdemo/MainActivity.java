package com.soccer.soccerdemo;

import android.content.Intent;
import android.media.Image;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    Team currentTeam; //current team
    Player currentPlayer; //current player

    String teamSelected; //team selected in team spinner
    String logo;

    private Button playerView; //btn to change to PlayersActivity
    private Button addPlayer; //btn to add a new player
    private Button addTeam; //btn to add a new team
    private Button startGame; //btn to change to FieldActivity

    //player stats when adding a new player
    private EditText playerGoals;
    private EditText playerRed;
    private EditText playerYellow;
    private EditText playerPosition;
    private EditText playerName;

    //current team stats
    private TextView teamGoals;
    private TextView teamRed;
    private TextView teamYellow;

    private EditText teamName; //new team name

    private ImageView teamLogoCurrent; //team logo

    HashMap<String, Player> players; //stores players

    HashMap<String, Team> teams; //teamName = key; stores teams
    ArrayList<String> teamNames; //team names
    ArrayAdapter<String> adapterTeams; //adapter for team spinner
    Spinner spinnerTeams; //spinner to display teams

    Spinner spinnerPositions; //spinner to display soccer positions
    ArrayAdapter<String> adapterPositions; //adapter for player positions
    ArrayList<String> teamPositions; //positions of players in a team

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize team stats
        teamGoals = (TextView) findViewById(R.id.team_goals);
        teamRed = (TextView) findViewById(R.id.team_red);
        teamYellow = (TextView) findViewById(R.id.team_yellow);

        //initialize buttons and connect to view
        playerView = (Button) findViewById(R.id.team_players);
        addPlayer = (Button) findViewById(R.id.add_player);
        addTeam = (Button) findViewById(R.id.add_team);
        startGame = (Button) findViewById(R.id.start_game);

        //initialize text fields for new players and connect to view
        playerGoals = (EditText) findViewById(R.id.player_goals);
        playerRed = (EditText) findViewById(R.id.player_red);
        playerYellow = (EditText) findViewById(R.id.player_yellow);
        playerName = (EditText) findViewById(R.id.player_name);
        playerPosition = (EditText) findViewById(R.id.player_position);

        //initialize text field for new team and image view for logo
        teamName = (EditText) findViewById(R.id.team_name);
        teamLogoCurrent = (ImageView) findViewById(R.id.team_logo);

        //initialize spinners for teams and positions
        spinnerTeams = (Spinner) findViewById(R.id.spinner_teams);
        spinnerPositions = (Spinner) findViewById(R.id.spinner_positions);

        teamNames = new ArrayList<>(); //initialize team names

        teams = new HashMap<>(); //initialize teams hashmap
        players = new HashMap<>(); //initialize player hashmap

        //start with players in hashmap
        currentPlayer = new Player("Lily", "Striker", "Kittens", 8, 0 , 1);
        players.put("Lily", currentPlayer);
        currentPlayer = new Player("Sam", "Mid-field", "Meow", 12, 1, 2);
        players.put("Sam", currentPlayer);
        currentPlayer = new Player("Linda", "Defender", "Meow", 1, 1, 1);
        players.put("Linda", currentPlayer);
        currentPlayer = new Player("Jack", "Defender", "Puppies", 2, 1, 2);
        players.put("Jack", currentPlayer);

        //add team names to array list
        teamNames.add("Meow");
        teamNames.add("Kittens");
        teamNames.add("Puppies");

        //create and add team Meow
        teamLogoCurrent.setImageResource(R.mipmap.flower_logo);
        currentTeam = new Team("Meow", teamLogoCurrent, R.mipmap.flower_logo);
        teams.put("Meow", currentTeam);

        //create and add team Kittens
        teamLogoCurrent.setImageResource(R.mipmap.emma_logo);
        currentTeam = new Team("Kittens", teamLogoCurrent, R.mipmap.emma_logo);
        teams.put("Kittens", currentTeam);

        //create and add team Puppies
        teamLogoCurrent.setImageResource(R.mipmap.koa_logo);
        currentTeam = new Team("Puppies", teamLogoCurrent, R.mipmap.koa_logo);
        teams.put("Puppies", currentTeam);

        //set listeners
        addTeam.setOnClickListener(new addTeamListener());
        addPlayer.setOnClickListener(new addPlayerListener());
        spinnerTeams.setOnItemSelectedListener(new teamSelectedListener());
        playerView.setOnClickListener(new playerViewListener());
        startGame.setOnClickListener(new gameListener());

        adapterTeams = new ArrayAdapter<>(this,    //initialize adapter for teams
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                teamNames);

        //connect team adapter to team spinner
        adapterTeams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(adapterTeams);
    }

    /*
     * class: teamSelectedListener          Listens for selected item in team spinner.
     */
    public class teamSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            teamSelected = parent.getItemAtPosition(position).toString(); //get selected item

            displayPositions(); //update team positions in position spinner
            teamStats(); //display team stats

            //update to selected team logo
            teamLogoCurrent.setImageResource(teams.get(teamSelected).getLogoName());

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: teamStats            Checks for players in the selected team then gets player stats
     *                              and displays them on gui.
     */
    public void teamStats() {
        Set<String> keys = players.keySet(); //get all player keys

        //variables to hold team stats
        int yellow = 0;
        int red = 0;
        int goals = 0;

        //check if players are in selected team and display team stats
        for(String k: keys) {
            if(players.get(k).getTeamName().equals(teamSelected)) {
                yellow += players.get(k).getYellowCards();
                red += players.get(k).getRedCards();
                goals += players.get(k).getGoalsScored();
            }
        }

        //display team stats
        teamYellow.setText(yellow + "");
        teamRed.setText(red + "");
        teamGoals.setText(goals + "");
    }

    /*
     * class: gameListener      Listens for start game button press. Passes team names
     *                          and players with intent.
     */
    public class gameListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, FieldActivity.class);

            intent.putStringArrayListExtra("teams", teamNames); //add team names to intent
            intent.putExtra("players", players); //add players hashmap to intent

            startActivity(intent); //change to field activity view
        }
    }

    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {

            if(resultCode == 2) {
                //get updated player hashmap
                players = (HashMap<String, Player>) data.getSerializableExtra("players updated");

                displayPositions(); //update player positions if players changed teams
            }
        }
    }

    /*
     * class: addPlayerListener         Adds a player to the player hashmap when user
     *                                  clicks add player button.
     */
    public class addPlayerListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            addPlayer(playerName.getText().toString()); //call method to add player
        }
    }

    /*
     *  method: addPlayer       Adds player to player hashmap and updates positions for teams.
     */
    private void addPlayer(String name) {
        currentPlayer = new Player(name,                //get player stats from gui
                playerPosition.getText().toString(),
                teamSelected, Integer.parseInt(playerGoals.getText().toString()),
                Integer.parseInt(playerRed.getText().toString()),
                Integer.parseInt(playerYellow.getText().toString()));

        players.put( name, currentPlayer); //add new player

        displayPositions(); //update positions in a team
        teamStats(); //update team stats
    }

    /*
     * method: displayPositions             Displays positions players play in a team.
     */
    public void displayPositions() {
        teamPositions = new ArrayList<>(); //initialize team positions

        Set<String> keys = players.keySet(); //get all keys for players

        for(String k: keys) {
            if(players.get(k).getTeamName().equals(teamSelected)) {
                teamPositions.add(players.get(k).getPosition()); //get all positions
            }
        }

        adapterPositions = new ArrayAdapter<>(this, //initialize adapter for positions
                android.R.layout.simple_list_item_1,
                android.R.id.text1, teamPositions);

        //connect adapter to spinner
        adapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPositions.setAdapter(adapterPositions);
    }

    /*
     * class: playerViewListener            Changes view to PlayerActivity when user clicks
     *                                      the view players button.
     */
    public class playerViewListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,PlayersActivity.class); //create intent

            intent.putStringArrayListExtra("hi", teamNames); //put team names into intent
            intent.putExtra("players", players); //put hashtable of players into intent
            startActivityForResult(intent, 1); //change to PlayerActivity view and wait for result
        }
    }

    /*
     * class: addTeamListener           Listens for when user clicks
     *                                  add team button.
     */
    public class addTeamListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            addTeam(teamName.getText().toString()); //calls method to add team
        }
    }

    /*
     * method: addTeam              Adds team to hashmap for teams.
     *                              Does not add teams with the same names.
     */
    private void addTeam(String teamName) {
        if(teamNames.indexOf(teamName) >= 0) return; //don't do anything if team already exists

        //add team name and resort
        teamNames.add((teamName));
        Collections.sort(teamNames);

        //create new adapter and add updated elements
        //connect to spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(teamNames);
        spinnerAdapter.notifyDataSetChanged();

        currentTeam = new Team(teamName, teamLogoCurrent, R.mipmap.thunder_logo); //create new team
        teams.put(teamName, currentTeam); //add team to hashmap
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
