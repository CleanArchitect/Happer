package happer;

import java.util.*;

/**
 * @author Kay
 */
public class Action {
    private String direction;
    private GameObject gameObject;
    private HashMap actions;
    private Action action;

    public Action() {
        actions = new HashMap();
    }

    /**
     * The constructor for an Action object with given Direction and GameObject.
     * 
     * @param direction  The direction where the player should move.
     * @param gameObject The GameObject that needs to be moved for the given
     *                   Direction
     */
    public Action(String direction, GameObject gameObject) {
        this.direction = direction;
        this.gameObject = gameObject;
    }

    public void execute(String key) {
        action = (Action) actions.get(key);
        if (action != null && action.getGameObject().isActive()) {
            action.getGameObject().move(action.getDirection());
        }
    }

    public String getDirection() {
        return direction;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void makeActions(ArrayList players) {
        int playerNr = players.size();
        GameObject player;

        for (int i = 0; i < playerNr; i++) {
            switch (i) {
                case 0:
                    player = (GameObject) players.get(i);
                    Action playerUp = new Action("Up", player);
                    Action playerDown = new Action("Down", player);
                    Action playerLeft = new Action("Left", player);
                    Action playerRight = new Action("Right", player);

                    actions.put("W", playerUp);
                    actions.put("S", playerDown);
                    actions.put("A", playerLeft);
                    actions.put("D", playerRight);

                    break;
                case 1:
                    player = (GameObject) players.get(i);
                    Action player2Up = new Action("Up", player);
                    Action player2Down = new Action("Down", player);
                    Action player2Left = new Action("Left", player);
                    Action player2Right = new Action("Right", player);

                    actions.put("Up", player2Up);
                    actions.put("Down", player2Down);
                    actions.put("Left", player2Left);
                    actions.put("Right", player2Right);
                    break;
                case 2:
                    player = (GameObject) players.get(i);
                    Action player3Up = new Action("Up", player);
                    Action player3Down = new Action("Down", player);
                    Action player3Left = new Action("Left", player);
                    Action player3Right = new Action("Right", player);

                    actions.put("T", player3Up);
                    actions.put("G", player3Down);
                    actions.put("F", player3Left);
                    actions.put("H", player3Right);
                    break;
                case 3:
                    player = (GameObject) players.get(i);
                    Action player4Up = new Action("Up", player);
                    Action player4Down = new Action("Down", player);
                    Action player4Left = new Action("Left", player);
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