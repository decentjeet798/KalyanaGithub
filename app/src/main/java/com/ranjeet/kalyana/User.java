package com.ranjeet.kalyana;

/**
 * Created by Ranjeet on 6/28/2017.
 */

public class User {

    public  String sprofile;
    public  String firstname;
    public String lastname;
    public  String ssister;
    public  String sbrother;
    public  String sgender;
    public String sreligion;
    public  String stongue;
    public  String  scountry;
    public String imageURL;
    public User(String sprofile, String firstname, String lastname, String ssister, String sbrother, String sgender, String sreligion, String stongue, String scountry, String imageURL) {
        this.sprofile = sprofile;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssister = ssister;
        this.sbrother = sbrother;
        this.sgender = sgender;
        this.sreligion = sreligion;
        this.stongue = stongue;
        this.scountry = scountry;

        this.imageURL = imageURL;
    }

    public User() {
    }

    public String getSprofile() {
        return sprofile;
    }

    public void setSprofile(String sprofile) {
        this.sprofile = sprofile;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSsister() {
        return ssister;
    }

    public void setSsister(String ssister) {
        this.ssister = ssister;
    }

    public String getSbrother() {
        return sbrother;
    }

    public void setSbrother(String sbrother) {
        this.sbrother = sbrother;
    }

    public String getSgender() {
        return sgender;
    }

    public void setSgender(String sgender) {
        this.sgender = sgender;
    }

    public String getSreligion() {
        return sreligion;
    }

    public void setSreligion(String sreligion) {
        this.sreligion = sreligion;
    }

    public String getStongue() {
        return stongue;
    }

    public void setStongue(String stongue) {
        this.stongue = stongue;
    }

    public String getScountry() {
        return scountry;
    }

    public void setScountry(String scountry) {
        this.scountry = scountry;
    }



    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
