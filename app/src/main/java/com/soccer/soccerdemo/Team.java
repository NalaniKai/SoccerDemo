package com.soccer.soccerdemo;

import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.HashMap;

/* Programmer: Nalani (Megan Chun)
 * Last Updated: Sept. 29, 2015
 *
 * class Team                       Holds logo ID of a soccer Team.
 */
public class Team implements Serializable {

    //team stats
    private int logoID;

    public Team(int logoID) {
        this.logoID = logoID;
    }

    //gets the logo ID
    public int getLogoName() { return logoID; }

}
