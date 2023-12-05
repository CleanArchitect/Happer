package happer;

import java.awt.Point;
import java.util.HashMap;

public class Position {
    private Point position;
    private Position parent;
    private HashMap neighbors;
    private GameObject gameObject;
    private int distanceG = 0;
    private int distanceH = 0;

    public Position(int coordX, int coordY) {
        this.gameObject = null;
        this.position = new Point(coordX, coordY);
        this.neighbors = new HashMap<Object, Object>();
    }

    public Position getNeighbor(String direction) {
        return (Position) this.neighbors.get(direction);
    }

    public void setNeighbor(String direction, Position neighbor) {
        getNeighbors().put(direction, neighbor);
    }

    public void setPoint(Point point) {
        this.position = point;
    }

    public Point getPoint() {
        return this.position;
    }

    public void setParent(Position parent) {
        this.parent = parent;
    }

    public Position getParent() {
        return this.parent;
    }

    public int getHeuristic() {
        return getDistanceH() + getDistanceG();
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public boolean isFree() {
        if (getGameObject() == null) {
            return true;
        }
        return false;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public HashMap getNeighbors() {
        return this.neighbors;
    }

    public int getDistanceG() {
        return this.distanceG;
    }

    public void setDistanceG(int distanceG) {
        this.distanceG = distanceG;
    }

    public int getDistanceH() {
        return this.distanceH;
    }

    public void setDistanceH(int distanceH) {
        this.distanceH = distanceH;
    }
}