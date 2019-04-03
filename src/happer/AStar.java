/*
 * AStar.java
 *
 * Created on 5 juni 2007, 18:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package happer;

import java.util.*;

/**
 * A* happer AI.
 * @author Djaimy + ijsbrand + kay :D
 */
public class AStar {
    private ArrayList openList, closedList, deniedList, path, players, happers;
    private Position movePos, currentNode, lowestF;
    private boolean playerDead, lost;
    private int availableDirections;
    
    /**
     * Creates a new instance of AStar.
     */
    public AStar() {
        this.openList   = new ArrayList();
        this.closedList = new ArrayList();
        this.deniedList = new ArrayList();
        this.path       = new ArrayList();
        
        this.playerDead = false;
        this.lost       = false;
    }
    
    /**
     * This is the main move function used by objects to determine the next
     * Position to find the closest target.
     * @param source the Position of the Source object.
     * @param targetArray The ArrayList with Players.
     * @param happers The ArrayList with Happers.
     */
    public void move(Position source, ArrayList targetArray, ArrayList happers) {
        this.players    = targetArray;
        this.happers    = happers;
        Position target = getClosestPlayer(targetArray, source);
        
        source.setParent(null);
        setHeuristics(source, target, source);
        openList.add(source);
        
        movePos = getLowestF(openList);
        closedList.add(movePos);
        
        openList.remove(movePos);
        
        boolean breakoff = false;
        
        if (allDead()) {
            breakoff   = true;
            playerDead = true;
        }
      
        while((getLastClosed() != target) && (breakoff == false)){
            breakoff = false;
            
            availableDirections = 0;
            
            HashMap neighbors;
            neighbors = getLastClosed().getNeighbors();
            Set keys = neighbors.keySet();
            for(Iterator iter = keys.iterator(); iter.hasNext(); ){
                boolean addNodeToList = true;
                Position nodePos = (Position)neighbors.get(iter.next());
                
                if(!(nodePos.getGameObject() instanceof Box) && !(nodePos.getGameObject() instanceof Wall)){
                    
                    availableDirections++;
                    Iterator it;
                    Position nodeFromList;
                    

                    for (it = closedList.iterator(); it.hasNext(); ) {
                        nodeFromList = (Position)it.next();
                        
                        if (nodeFromList == nodePos) {
                            addNodeToList = false;
                            break;
                        }
                    }
                    
                    if (addNodeToList == true) {    //not in closedList

                        for (it = openList.iterator(); it.hasNext(); ) {
                            nodeFromList = (Position)it.next();
                            
                            if (nodeFromList == nodePos) {
                                addNodeToList = false;
                                break;
                            }
                        }
                    }
                    
                    if (addNodeToList == true) {    //not in openList
                        

                        for (it = deniedList.iterator(); it.hasNext(); ) {
                            nodeFromList = (Position)it.next();
                            
                            if (nodeFromList == nodePos) {
                                addNodeToList = false;
                                break;
                            }
                        }
                    }
                    
                    if(addNodeToList == true){ //ok it is not in anylist
                        setHeuristics(source,target,nodePos);
                        setParent(nodePos);
                        openList.add(nodePos);
                    }
                }else{
                    deniedList.add(nodePos);
                }
                
            }

            if(!(openList.size() > 0)){
                breakoff = true;
            }else{
                movePos = getLowestF(openList);
                closedList.add(movePos);
                openList.remove(movePos);
            }
        }
        if (availableDirections == 0) {
            source.getGameObject().setActive(false);
            source.setGameObject(null);
            if (allLost()){
                lost = true;
            }
        } else {

            Position tempPos = getLastClosed();
            if(tempPos == null){
                tempPos = getLowestF(closedList);
            }
            
            while(tempPos.getParent() != null){
                path.add(tempPos);
                tempPos = tempPos.getParent();
            }
            

            if (path.size() != 0) {
                tempPos = (Position)path.get(path.size()-1); // positie waar we heen moeten
                
                if (tempPos.getGameObject() instanceof Player){
                    tempPos.getGameObject().setActive(false);
                    tempPos.setGameObject(null);
                } else {
                    if (!(tempPos.getGameObject() instanceof Happer)){
                        if (tempPos.getGameObject() instanceof Special){
                            source.getGameObject().setSpecial(true);
                        }
                        source.getGameObject().changePos(source, tempPos);
                    }
                }
            } else {
              
            }
            
            closedList.removeAll(closedList);
            openList.removeAll(openList);
            deniedList.removeAll(deniedList);
            path.removeAll(path);
        }
    }
    private Position getLowestF(ArrayList list){
        Position lowestF = null;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Position posFromList = (Position)it.next();
            if(lowestF == null){
                lowestF = posFromList;
            }else{
                if(lowestF.getHeuristic() > posFromList.getHeuristic()){
                    lowestF = posFromList;
                }
            }
        }
        return lowestF;
    }
    
   
    private void setParent(Position tempPos){
        tempPos.setParent(getLastClosed());
    }
    
    /**
     * A public method to find out if the current happer has lost.
     * @return Returns true when the current Happer has lost.
     */
    public boolean happerLost(){
        return lost;
    }
    /**
     * A public method to find out if the Players have lost.
     * @return Returns true when the current Player has lost.
     */
    public boolean playerDead(){
        return playerDead;
    }
    
    /**
     * A method that looks if all Players are inactive.
     * @return returns true when all Players are inactive.
     */
    public boolean allDead() {
        boolean allDead = true;
        for (Iterator it = players.iterator(); it.hasNext();){
            GameObject player = (GameObject)it.next();
            if (player.isActive()){
                allDead = false;
                break;
            }
        }
        return allDead;
    }
    
    /**
     * A method that looks if all Happers are inactive.
     * @return Returns true when all Happers are inactive.
     */
    public boolean allLost() {
        boolean allLost = true;
        for (Iterator it = happers.iterator(); it.hasNext();){
            Happer happer = (Happer)it.next();
            if (happer.isActive()){
                allLost = false;
                break;
            }
        }
        return allLost;
    }
    
    private void setHeuristics(Position source, Position target, Position current){
        int distanceD_X = 0;
        int distanceD_Y = 0;
        int distanceH_X = 0;
        int distanceH_Y = 0;
        if (allDead()) {
            playerDead = true;
        } else {
            //set the distance from the current pos to the beginning
            if(source.getPoint().getX() > current.getPoint().getX()){
                distanceD_X = (int)source.getPoint().getX() - (int)current.getPoint().getX();
            }else{
                distanceD_X = (int)current.getPoint().getX() - (int)source.getPoint().getX();
            }
            if(source.getPoint().getY() > current.getPoint().getY()){
                distanceD_Y = (int)source.getPoint().getY() - (int)current.getPoint().getY();
            }else{
                distanceD_Y = (int)current.getPoint().getY() - (int)source.getPoint().getY();
            }
            //set the distance from the current pos to the target
            if(target.getPoint().getX() > current.getPoint().getX()){
                distanceH_X = (int)target.getPoint().getX() - (int)current.getPoint().getX();
            }else{
                distanceH_X = (int)current.getPoint().getX() - (int)target.getPoint().getX();
            }
            if(target.getPoint().getY() > current.getPoint().getY()){
                distanceH_Y = (int)target.getPoint().getY() - (int)current.getPoint().getY();
            }else{
                distanceH_Y = (int)current.getPoint().getY() - (int)target.getPoint().getY();
            }
            int distanceG = distanceD_X + distanceD_Y;
            int distanceH = distanceH_X + distanceH_Y;
            current.setDistanceG(distanceG);
            current.setDistanceH(distanceH);
        }
    }
    
    private Position getLastClosed(){
        Position tempPos = (Position)closedList.get(closedList.size()-1);
        return tempPos;
    }
   
    private Position getClosestPlayer(ArrayList list, Position source){
        Iterator it;
        Position player;
        GameObject gameObjectPlayer;
        
        for (it = list.iterator(); it.hasNext(); ) {
            gameObjectPlayer = (GameObject)it.next();
            player = gameObjectPlayer.getPosition();
            setHeuristics(player, source, player);
        }
        
        Position lowestFPos, tempPos;
        lowestFPos = null;
        tempPos    = null;
        for (it = list.iterator(); it.hasNext(); ) {
            if(lowestFPos == null){
                gameObjectPlayer = (GameObject)it.next();
                if (gameObjectPlayer.isActive()){
                    lowestFPos = gameObjectPlayer.getPosition();
                }
            }else{
                gameObjectPlayer = (GameObject)it.next();
                if (gameObjectPlayer.isActive()) {
                    tempPos = gameObjectPlayer.getPosition();
                    if(lowestFPos.getHeuristic() > tempPos.getHeuristic()){
                        lowestFPos = tempPos;
                    }
                }
            }
        }
        return lowestFPos;
    }
}
