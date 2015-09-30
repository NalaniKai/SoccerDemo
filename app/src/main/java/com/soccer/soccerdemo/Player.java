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
    private float x;
    private float y;
    private boolean team1;

    public Player(String name, String position, String team, int goals, int red, int yellow) {
        this.name = name;
        this.position = position;
        this.team = team;
        this.goals = goals;
        this.red = red;
        this.yellow = yellow;
        this.x = 0;
        this.y = 0;
        team1 = false;
    }

    //get player stats
    public String getName() { return  name; }
    public String getPosition() { return  position; }
    public String getTeamName() { return team; }
    public int getGoalsScored() { return goals; }
    public int getRedCards() { return red; }
    public int getYellowCards() { return yellow; }

    //get player position on surface view
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean getTeam1() { return team1; }

    //set player team
    public void setTeamName(String team) { this.team = team; }
    public void setGoals() { ++this.goals; }
    public void setYellowCards() { ++this.yellow; }

    //set player position on surface view
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setTeam1(boolean team1) { this.team1 = team1; }
}
