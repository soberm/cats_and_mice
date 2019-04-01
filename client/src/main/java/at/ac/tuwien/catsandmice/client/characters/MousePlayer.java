package at.ac.tuwien.catsandmice.client.characters;


import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;

import java.awt.event.KeyEvent;

public class MousePlayer extends Player {

    private boolean tryToEnter = false;
    private MouseRepresentation mouse;

    public MousePlayer(MouseRepresentation mouse) {
        this.mouse = mouse;
        setCharacter(mouse);

        UP = KeyEvent.VK_W;
        DOWN = KeyEvent.VK_S;
        LEFT = KeyEvent.VK_A;
        RIGHT = KeyEvent.VK_D;

        setSpeed(3);
    }

    public boolean isTryToEnter() {
        return tryToEnter;
    }

    public void setTryToEnter(boolean tryToEnter) {
        this.tryToEnter = tryToEnter;
    }

    @Override
    public void enterSubway(SubwayRepresantation subway) {
        if(isTryToEnter()) {
            tryToEnter = !subway.enterOrExit(this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_E) {
            setTryToEnter(true);
        }
    }

    public void moveTo(int x, int y) {
        mouse.setX(x-getWidth()/2);
        mouse.setY(y-getHeight()/2);
    }

    public MouseRepresentation getMouse() {
        return mouse;
    }

    public void setMouse(MouseRepresentation mouse) {
        this.mouse = mouse;
    }
}
