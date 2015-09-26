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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public class playerViewListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, PlayersActivity.class));
        }
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
