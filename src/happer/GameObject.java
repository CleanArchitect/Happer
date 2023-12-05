package happer;

import java.awt.Graphics;

public abstract class GameObject {
    private Position position;
    private boolean active;
    private boolean special;

    public GameObject(Position position) {
        setPosition(position);
        this.active = true;
        this.special = false;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
        position.setGameObject(this);
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSpecial() {
        return this.special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public void move(String direction) {
        if (canMove(direction)) {
            changePos(this.position, this.position.getNeighbor(direction));
        }
    }

    public boolean canMove(String direction) {
        boolean succes = false;
        Position neighbor = this.position.getNeighbor(direction);

        if (neighbor != null) {
            if (neighbor.isFree()) {
                succes = true;
            } else {
                GameObject neighborObject = neighbor.getGameObject();
                if (neighborObject instanceof Special) {
                    setSpecial(true);
                    succes = true;
                } else if (canPush(neighborObject) &&
                        neighborObject.canMove(direction)) {
                    neighborObject.move(direction);
                    succes = true;
                }
            }
        }

        return succes;
    }

    public boolean canPush(GameObject gameObject) {
        boolean canPush = false;
        if (gameObject instanceof Box) {
            canPush = true;
        }
        return canPush;
    }

    public void changePos(Position oldPos, Position newPos) {
        oldPos.setGameObject(null);
        setPosition(newPos);
    }

    public abstract void draw(Graphics paramGraphics, HapperPanel paramHapperPanel);
}