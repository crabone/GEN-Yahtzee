package com.mycompany.jyahtzee.client.transport;

import java.util.ArrayList;

/**
 * Created by rosanne on 08.06.16.
 */
public class CommRequired{

    private String about = "";
    private String argUserName = "";
    private String argPWD = "";
    private boolean resultBool;
    private String id = "0";
    private ArrayList<ArrayList<String>> games;
    private int resultInt;

    public synchronized void setAbout(String about)
    {
        this.about = about;
    }
    public String getAbout()
    {
        return about;
    }

    public synchronized void setArgPWD(String argPWD) {
        this.argPWD = argPWD;
    }
    public String getArgPWD() {
        return argPWD;
    }

    public synchronized void setArgUserName(String argUserName) {
        this.argUserName = argUserName;
    }
    public synchronized String getArgUserName() {
        return argUserName;
    }

    public synchronized void setResultBool(boolean result)
    {
        this.resultBool = result;
    }
    public synchronized boolean getResultBool()
    {
        return resultBool;
    }

    public synchronized void setId(String id)
    {
        this.id = id;
    }
    public synchronized String getId()
    {
        return id;
    }

    public ArrayList<ArrayList<String>> newListGame()
    {
        ArrayList<ArrayList<String>> newlist = new ArrayList<>();
        games = newlist;
        return games;
    }
    public ArrayList<ArrayList<String>> getGames()
    {
        return games;
    }

    public void setResultInt(int result)
    {
        resultInt = result;
    }
    public int getResultInt()
    {
        return resultInt;
    }

    public synchronized void clearVar()
    {
        about = "";
        argUserName = "";
        argPWD = "";
        resultBool = false;
        id = "0";
        resultInt = -1;
    }


}
