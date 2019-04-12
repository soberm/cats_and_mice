package at.ac.tuwien.catsandmice.client.characters;


import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.MouseUpdateMessage;

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

    protected void updateOnServer() {
        MouseUpdateMessage mouseUpdateMessage = new MouseUpdateMessage();
        mouseUpdateMessage.setMouse(mouse);
        mouseUpdateMessage.setContainedInUUID(mouse.getBoundaries().getUuid());

        String json = ClientConstants.getGson().toJson(mouseUpdateMessage);
        writer.println(json);
        writer.flush();
    }

    @Override
    public void enterSubway(SubwayRepresantation subway) {
        if(isTryToEnter()) {
            tryToEnter = !subway.enterOrExit(this);
        }
    }

    @Override
    public void resetClicks() {
        super.resetClicks();
        this.tryToEnter = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(e.getKeyCode() == KeyEvent.VK_E) {
            setTryToEnter(true);
        }
    }

    public void moveTo(int x, int y) {
        mouse.setX(x-mouse.getWidth()/2);
        mouse.setY(y-mouse.getHeight()/2);
    }

    public MouseRepresentation getMouse() {
        return mouse;
    }

    public void setMouse(MouseRepresentation mouse) {
        this.mouse = mouse;
    }
}
