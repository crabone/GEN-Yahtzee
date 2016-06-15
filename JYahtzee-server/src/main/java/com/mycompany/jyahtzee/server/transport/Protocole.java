package com.mycompany.jyahtzee.server.transport;

/**
 *
 * @author rosanne
 */
public class Protocole {    
	public static final String CMD_HI = "hi";
	public static final String CMD_ACK = "ack";
	public static final String CMD_OK = "ok";
	public static final String CMD_KO = "ko";
	public static final String CMD_AUTH = "authentification";
	public static final String CMD_USERNAME = "username";
	public static final String CMD_MDP = "mdp";
	public static final String CMD_INSCRIPTION = "inscription"; 
	public static final String CMD_CREATION = "creation";
	public static final String CMD_JOIN = "join";
	public static final String CMD_OBSERVE = "observe";
	public static final String CMD_BYE = "bye";
	public static final String CMD_ROLL_THE_DICES = "rollDices";
        public static final String CMD_ROLL = "rollDice";
	public static final String CMD_DECISION = "decision";
	public static final String CMD_GETGAMES = "getGames";        
        public static final String CMD_UPDATEVUE = "update";
        public static final String CMD_PLAYERTURN = "playerTurn";
    public static final String CMD_END = "end";
    public static final String CMD_START = "start";
}
