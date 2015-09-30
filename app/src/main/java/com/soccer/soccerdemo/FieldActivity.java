package com.soccer.soccerdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/*
 * class: FieldActivity         A soccer field is draw and the user can view a list of the teams
 *                              and players in each team. The user can select a player and the
 *                              player will be drawn on the field. The user can also play a game
 *                              where a random team is selected as a winner and player stats are
 *                              updated. After a winner is selected another activity opens up to
 *                              celebrate the win.
 */
public class FieldActivity extends ActionBarActivity {

    private TextView winningTeam; //will display winning team

    private PlayersInGame playersDraw; //surface view to draw players on
    private Canvas c;

    //buttons to return to the main activity and play the game
    private Button stats;
    private Button play;

    //selected teams and players
    private String team1; //team selected on left
    private String team2; //team selected on right
    private String player1; //on left
    private String player2; //on right

    //spinners for teams
    private Spinner spinnerTeam1;
    private Spinner spinnerTeam2;

    //spinners for players
    private Spinner spinnerPlayers1;
    private Spinner spinnerPlayers2;

    //arrayList of teams and players
    private ArrayList<String> team_list;

    //array adapter for teams and players
    private ArrayAdapter<String> adapter_teams;

    //adapters for players
    private ArrayAdapter<String> adapterPlayers1;
    private ArrayAdapter<String> adapterPlayers2;

    //ArrayLists for players
    private ArrayList<String> player1List;
    private ArrayList<String> player2List;

    private HashMap<String, Boolean> playerDrawn; //check if player has been drawn

    private HashMap<String, Player> players; //hashmap of players

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        winningTeam = (TextView) findViewById(R.id.winner); //initialize winner textview

        playersDraw = (PlayersInGame) findViewById(R.id.surfaceViewPlayers); //players surface view
        playersDraw.setZOrderOnTop(true); //set view on top
        SurfaceHolder holder = playersDraw.getHolder(); //create holder
        holder.setFormat(PixelFormat.TRANSPARENT); //make surface view transparent

        team_list = new ArrayList<>(); //initialize array list for teams and players

        stats = (Button) findViewById(R.id.return_stats); //initialize button to go back to stats page
        play = (Button) findViewById(R.id.play); //initialize button to play game

        //initialize spinners for teams
        spinnerTeam1 = (Spinner) findViewById(R.id.spinner_team1);
        spinnerTeam2 = (Spinner) findViewById(R.id.spinner_team2);

        //initialize spinners for players
        spinnerPlayers1 = (Spinner) findViewById(R.id.spinner_players1);
        spinnerPlayers2 = (Spinner) findViewById(R.id.spinner_players2);

        players = new HashMap<>(); //initialize hashmap of players

        playerDrawn = new HashMap<>(); //initialize hashmap to track whether each has been drawn

        Intent intent = getIntent(); //get intent

        team_list = intent.getStringArrayListExtra("teams"); //get team name array list

        players = (HashMap<String, Player>) intent.getSerializableExtra("players"); //get players

        Set<String> playerNames = players.keySet(); //get all players names

        //initialize players as not being drawn
        for(String name: playerNames) {
            playerDrawn.put(name, false);
        }

        //set adapter and connect to team spinners
        adapter_teams = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter_teams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam1.setAdapter(adapter_teams);
        spinnerTeam2.setAdapter(adapter_teams);

        //set on click listeners for buttons
        stats.setOnClickListener(new returnToStatsListener());
        play.setOnClickListener(new playListener());
        spinnerTeam1.setOnItemSelectedListener(new team1Listener());
        spinnerTeam2.setOnItemSelectedListener(new team2Listener());
        spinnerPlayers1.setOnItemSelectedListener(new player1Listener());
        spinnerPlayers2.setOnItemSelectedListener(new player2Listener());
    }

    /*
     * class: player2Listener           Listens for player selected on right and draws on field.
     */
    public class player2Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            player2 = parent.getItemAtPosition(position).toString(); //get selected player

            if( !playerDrawn.get(player2).booleanValue()) {

                float x = (float) (Math.random() * (playersDraw.getWidth()-100)) + 30; //get random x position
                float y = (float) (Math.random() * (playersDraw.getHeight()-100)) + 30; //get random y position

                c = playersDraw.getHolder().lockCanvas(); //lock canvas

                playersDraw.drawPlayerTeam2(c, x, y, player2); //draws selected player on board

                Set<String> playerNames = players.keySet(); //get all player names

                //draw previously selected players
                for(String name: playerNames) {

                    //draw players on team 1
                    if ( playerDrawn.get(name).booleanValue() && players.get(name).getTeam1()) {
                        playersDraw.drawPlayerTeam1(c,
                                players.get(name).getX(),
                                players.get(name).getY(),
                                name);
                    }

                    //draw players on team 2
                    if ( playerDrawn.get(name).booleanValue() && !players.get(name).getTeam1() ) {
                        playersDraw.drawPlayerTeam2(c,
                                players.get(name).getX(),
                                players.get(name).getY(),
                                name);
                    }
                }

                playersDraw.getHolder().unlockCanvasAndPost(c); //draw
                playersDraw.postInvalidate();

                //set player position
                players.get(player2).setX(x);
                players.get(player2).setY(y);

                playerDrawn.put(player2, true); //track players drawn
                players.get(player2).setTeam1(false); //track that player is on team 2
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * class: player1Listener           Listens for when player on left is selected and draws on field.
     */
    public class player1Listener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            player1 = parent.getItemAtPosition(position).toString(); //get selected player

            //does not redraw if player has already been drawn
            if( !playerDrawn.get(player1).booleanValue()) {

                float x = (float) (Math.random() * (playersDraw.getWidth()-100) ) + 30; //get random x position
                float y = (float) (Math.random() * (playersDraw.getHeight()-100) + 30); //get random y position

                c = playersDraw.getHolder().lockCanvas(); //lock canvas
                playersDraw.drawPlayerTeam1(c, x, y, player1); //draws selected player on board

                Set<String> playerNames = players.keySet(); //get all player names

                //draw previously selected players
                for(String name: playerNames) {

                    //draw players on team 1
                    if ( playerDrawn.get(name).booleanValue() && players.get(name).getTeam1() ) {
                        playersDraw.drawPlayerTeam1(c,
                                players.get(name).getX(),
                                players.get(name).getY(),
                                name);
                    }

                    //draw players on team 2
                    if ( playerDrawn.get(name).booleanValue() && !players.get(name).getTeam1() ) {
                        playersDraw.drawPlayerTeam2(c,
                                players.get(name).getX(),
                                players.get(name).getY(),
                                name);
                    }
                }

                playersDraw.getHolder().unlockCanvasAndPost(c); //draw
                playersDraw.postInvalidate();
                players.get(player1).setTeam1(true); //set team 1 to true

                //set player position
                players.get(player1).setX(x);
                players.get(player1).setY(y);

                playerDrawn.put(player1, true); //track players drawn
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
    * class: team2Listener      Listens for selected team on right side of screen.
    */
    public class team2Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            team2 = parent.getItemAtPosition(position).toString(); //get selected team

            displayPlayers2(); //display players in team selected on right
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers2          Displays players in team selected on the right.
     */
    public void displayPlayers2() {
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
        spinnerPlayers2.setAdapter(adapterPlayers2);
    }

    /*
     * class: team1Listener      Listens for selected team on left side of screen.
     */
    private class team1Listener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            team1 = parent.getItemAtPosition(position).toString();

            displayPlayers1(); //display players in team selected on left
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /*
     * method: displayPlayers1          Display players in team selected on the left.
     */
    public void displayPlayers1() {
        player1List = new ArrayList<>(); //new arrayList for players

        Set<String> keys = players.keySet(); //get keys for all players

        for(String k: keys) {
            if(players.get(k).getTeamName().equals(team1)) {
                player1List.add(k); //add players to spinner on right
            }
        }

        //set adapter and connect to spinner
        adapterPlayers1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                player1List);
        adapterPlayers1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers1.setAdapter(adapterPlayers1);
    }

    /*
     * class: playListener      Listens for when user clicks the play button.
     */
    public class playListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int winner = (int) (Math.random()*2.0); //chooses random winner

            if(winner == 0) {
                winningTeam.setText(team1 + " wins!");

                Set<String> keys = players.keySet(); //get keys for all players

                for(String k: keys) {
                    if(players.get(k).getTeamName().equals(team1)) {
                        players.get(k).setGoals(); //add 1 goal for each player on team

                        //add 1 yellow card for players on team with less than 2 yellow cards
                        if(players.get(k).getYellowCards() < 2) {
                            players.get(k).setYellowCards();
                        }
                    }
                }

                //switch to winner view
                Intent intent = new Intent(FieldActivity.this, WinnerActivity.class);
                intent.putExtra("winner", team1);
                startActivity(intent);
            }
            else {
                winningTeam.setText(team2 + " wins!");

                Set<String> keys = players.keySet(); //get keys for all players

                for(String k: keys) {
                    if(players.get(k).getTeamName().equals(team2)) {
                        players.get(k).setGoals(); //add 1 goal for each player on team

                        //add 1 yellow card for players on team with less than 2 yellow cards
                        if(players.get(k).getYellowCards() < 2) {
                            players.get(k).setYellowCards();
                        }
                    }
                }

                //switch to winner view
                Intent intent = new Intent(FieldActivity.this, WinnerActivity.class);
                intent.putExtra("winner", team2);
                startActivity(intent);
            }
        }
    }

    /*
     * class: returnToStatsListener         Returns to MainActivity when user clicks stats button.
     */
    public class returnToStatsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent();

            i.putExtra("stats updated", players);
            setResult(2, i);
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
