package com.mycompany.jyahtzee.manager;

import java.util.HashMap;

/**
 *
 * @author Kevin
 */
public class GameManager {

    HashMap<Integer, Game> games = new HashMap<>();

    public GameManager() {
    }

    public boolean createGame() {
        Game newGame = new Game(player);
        games.put(newGame.getID(), newGame);
        
        return false;
    }

    public boolean joinGame(int id) {
        Game game = games.get(id);
        game.addPlayer(player);

        return false;
    }

    public boolean observeGame(int id) {
        return false;
    }

}
