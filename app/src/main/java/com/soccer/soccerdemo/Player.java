package com.soccer.soccerdemo;

/**
 * Created by chunm18 on 9/26/2015.
 */
public class Player {

    private String playerName;
    private String teamName;
    private int goalsScored;
    private int redCards;
    private int yellowCards;

    public Player(String name, String team, int goals, int red, int yellow) {
        playerName = name;
        teamName = team;
        goalsScored = 0;
        redCards = 0;
        yellowCards = 0;
    }

    //get player stats
    public String getName() { return  playerName; }
    public String getTeamName() { return teamName; }
    public int getGoalsScored() { return goalsScored; }
    public int getRedCards() { return redCards; }
    public int getYellowCards() { return yellowCards; }
}
