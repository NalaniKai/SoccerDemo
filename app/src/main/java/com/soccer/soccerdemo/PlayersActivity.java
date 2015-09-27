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

    private Button statsBtn;
    private ImageButton right;
    private ImageButton left;

    //member lists
    private EditText memb1;
    private EditText memb2;

    //spinner drop down for teams
    private Spinner spinner1;
    private Spinner spinner2;

    ArrayAdapter<String> adapter; //adapter for spinner
    ArrayList<String> team_list;


    /*MainActivity main;

    public PlayersActivity(MainActivity m) {
        main = m;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        //Bundle extras = getIntent().getExtras();

        //teams spinners
        spinner1 = (Spinner) findViewById(R.id.spinner_team1);
        spinner2 = (Spinner) findViewById(R.id.spinner_team2);

        /*//Intent intent = getIntent();

        if(null != extras) {
            //h = intent.getStringArrayExtra("hi");

            //team_list.add(extras.getString("myStr"));
            /*for(int i = 0; i < intent.getStringArrayExtra("hi").length; i++ ) {
                h[i] = intent.getStringArrayExtra("hi").clone();
            }
            for(int i = 0; i< intent.getStringArrayListExtra("hi").size(); i++)
            {
                team_list.add(intent.getStringArrayListExtra("hi").get(i));
            }
        }*/

        /*adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);*/
        /*for(int i = 0; i < h.length; i++) {
            h[i];
        }*/

        /*adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                h);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);*/
        //spinner2.setAdapter(adapter);

        //button to go back to MainActivity
        statsBtn = (Button) findViewById(R.id.statsButton);
        statsBtn.setOnClickListener(new statsListener());

        //buttons to exchange players between teams
        right = (ImageButton) findViewById(R.id.arrow_right);
        left = (ImageButton) findViewById(R.id.arrow_left);

        //members list
        memb1 = (EditText) findViewById(R.id.membList1);
        memb2 = (EditText) findViewById(R.id.membList2);



        /*adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                team_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);*/
    }


    private class statsListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
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
