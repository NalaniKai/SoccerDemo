package com.soccer.soccerdemo;

import android.media.Image;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by chunm18 on 9/26/2015.
 */
public class Team {

    //team stats
    private int goals;
    private int red_cards;
    private int yellow_cards;
    private String[] positions;
    HashMap<String, Player> players; //playerName = key

    //team name and logo
    private String teamName;
    private ImageView logo;

    public Team(int goals, int red_cards, int yellow_cards, String[] positions, String teamName, ImageView logo) {
        this.goals = goals;
        this.red_cards = red_cards;
        this.yellow_cards = yellow_cards;
        this.positions = positions;
        this.teamName = teamName;
        this.logo = logo;
    }

    //get team stats
    public int getTeamGoals() { return goals; }
    public int getTeamRed() { return red_cards; }
    public int getTeamYellow() { return yellow_cards; }
    public String[] getTeamPositions() { return positions; }

    //get team name and logo
    public String getTeamName() { return teamName; }
    public ImageView getTeamLogo() { return logo; }

    //set team stats
    public void setGoals(int goals) { this.goals = goals; }
    public void setTeamRed(int red) { this.red_cards = red; }
    public void setTeamYellow(int yellow) { this.yellow_cards = yellow; }
    public void setTeamPositions(String[] position) { this.positions = position; }

    //set team name and logo
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public void setTeamLogo(ImageView logo) { this.logo = logo; }
}
