/*
 * Action.java
 *
 * Created on 31 mei 2007, 13:10
 */

package happer;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This class makes and saves all the Action classes for the different players.
 * Once an Action class is made, it will be saved in a Hashmap. The Game class
 * calls execute with a KeyCode, and the Action will be performed for the right
 * player.
 * @author Kay Smits
 * @version 1.0 13/06/07
 */
public class Action {
    private String     direction;
    private GameObject gameObject;
    private HashMap    actions;
    private Action     action;
    
    /**
     * The constructor for an Action object. This one is only used for the HapperPanel to
     * make and save all the Action objects in the HashMap.
     */
    public Action() {
        actions = new HashMap();
    }
    
    /**
     * The constructor for an Action object with given Direction and GameObject.
     * @param direction The direction where the player should move.
     * @param gameObject The GameObject that needs to be moved for the given Direction
     */
    public Action(String direction, GameObject gameObject) {
        this.direction  = direction;
        this.gameObject = gameObject;
    }
    
    /**
     * Performs the GameObject move method. Gets the GameObject from the Actions Hashmap
     * and calls the method to move.
     * @param key The Actions Hashmap key
     */
    public void execute(String key){
        action = (Action)actions.get(key);
        if (action != null && action.getGameObject().isActive()){
            action.getGameObject().move(action.getDirection());
        }
    }
    
    /**
     * Returns the Direction from the Action object.
     * @return The direction where the player should move.
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Returns the GameObject from the Action object.
     * @return The GameObject that belongs to the given Key
     */
    public GameObject getGameObject() {
        return gameObject;
    }
    
    /**
     * Fills the Action Hashmap with standard keybindings. Depending on how
     * many players are chosen.
     * @param players The number of Players
     */
    public void makeActions(ArrayList players) {
        int playerNr = players.size();
        GameObject player;
        
        for (int i = 0; i < playerNr; i++){
            switch (i) {
                case 0:
                    player = (GameObject)players.get(i);
                    Action playerUp     = new Action("Up",    player);
                    Action playerDown   = new Action("Down",  player);
                    Action playerLeft   = new Action("Left",  player);
                    Action playerRight  = new Action("Right", player);
                    
                    actions.put("W", playerUp);
                    actions.put("S", playerDown);
                    actions.put("A", playerLeft);
                    actions.put("D", playerRight);
                    
                    break;
                case 1:
                    player = (GameObject)players.get(i);
                    Action player2Up    = new Action("Up",    player);
                    Action player2Down  = new Action("Down",  player);
                    Action player2Left  = new Action("Left",  player);
                    Action player2Right = new Action("Right", player);
                    
                    actions.put("Up",    player2Up);
                    actions.put("Down",  player2Down);
                    actions.put("Left",  player2Left);
                    actions.put("Right", player2Right);
                    break;
                case 2:
                    player = (GameObject)players.get(i);
                    Action player3Up    = new Action("Up",    player);
                    Action player3Down  = new Action("Down",  player);
                    Action player3Left  = new Action("Left",  player);
                    Action player3Right = new Action("Right", player);
                    
                    actions.put("T", player3Up);
                    actions.put("G", player3Down);
                    actions.put("F", player3Left);
                    actions.put("H", player3Right);
                    break;
                case 3:
                    player = (GameObject)players.get(i);
                    Action player4Up    = new Action("Up",    player);
                    Action player4Down  = new Action("Down",  player);
                    Action player4Left  = new Action("Left",  player);
                    Action player4Right = new Action("Right", player);
                    
                    actions.put("I", player4Up);
                    actions.put("K", player4Down);
                    actions.put("J", player4Left);
                    actions.put("L", player4Right);
                    break;                    
            }
        }
    }
    
}