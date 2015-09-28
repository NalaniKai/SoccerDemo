package com.soccer.soccerdemo;

import java.io.Serializable;

/**
 * Created by chunm18 on 9/26/2015.
 */
public class Player implements Serializable {

    private String name;
    private String position;
    private String team;
    private int goals;
    private int red;
    private int yellow;

    public Player(String name, String position, String team, int goals, int red, int yellow) {
        this.name = name;
        this.position = position;
        this.team = team;
        this.goals = goals;
        this.red = red;
        this.yellow = yellow;
    }

    //get player stats
    public String getName() { return  name; }
    public String getPosition() { return  position; }
    public String getTeamName() { return team; }
    public int getGoalsScored() { return goals; }
    public int getRedCards() { return red; }
    public int getYellowCards() { return yellow; }

    //set player team
    public void setTeamName(String team) { this.team = team; }
}
