package at.ac.tuwien.catsandmice.client.characters;


import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresantation;
import at.ac.tuwien.catsandmice.client.world.WorldRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Character;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.MouseUpdateMessage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public void draw(Graphics2D g2d, ImageObserver observer, WorldRepresentation worldRepresentation) {
        if(!isInitialised() || mouse.getBoundaries().equals(worldRepresentation) ) {
            if(isInitialised() && mouse.isAlive() && tryToEnter) {
                for(SubwayRepresantation subwayRepresantation : worldRepresentation.getSubwayRepresantations()) {
                    if(tryToEnter && subwayRepresantation.enterOrExit(this)) {
                        tryToEnter = false;
                        drawWorld(g2d, observer, worldRepresentation, subwayRepresantation);
                    }
                }
                if(tryToEnter) {
                    drawWorld(g2d, observer, worldRepresentation);
                }
            } else {
                drawWorld(g2d, observer, worldRepresentation);
            }
        } else {
            //we know we are in a subway, so only exiting is possible
            List<SubwayRepresantation> subwayRepresantations = worldRepresentation.getSubwayRepresantations().stream().filter(new Predicate<SubwayRepresantation>() {
                @Override
                public boolean test(SubwayRepresantation subwayRepresantation) {
                    return subwayRepresantation.getUuid().equals(mouse.getBoundaries().getUuid());
                }
            }).collect(Collectors.toList());

            SubwayRepresantation subwayRepresantation = subwayRepresantations.get(0);

            if(tryToEnter && subwayRepresantation.enterOrExit(this)) {
                //if it wants to exit and it succeeds to exit
                drawWorld(g2d, observer, worldRepresentation);
            } else {
                //if it either does not want to exit or fails to do so
                drawWorld(g2d, observer, worldRepresentation, subwayRepresantation);
            }

        }
        tryToEnter = false;
    }

    private void drawWorld(Graphics2D g2d, ImageObserver observer, WorldRepresentation worldRepresentation) {
        super.draw(g2d, observer, worldRepresentation);
    }

    private void drawWorld(Graphics2D g2d, ImageObserver observer, WorldRepresentation worldRepresentation, SubwayRepresantation subwayRepresantation) {
        for(SubwayRepresantation subway : worldRepresentation.getSubwayRepresantations()) {
            subway.draw(g2d, observer, !subway.equals(subwayRepresantation));
        }
        for(MouseRepresentation mouse : subwayRepresantation.getMiceRepresentations()) {
            if(mouse.isAlive()) {
                mouse.draw(g2d, observer);
            }
        }
        for(CatRepresentation cat : subwayRepresantation.getKnownCatRepresentationLocations()) {
            cat.draw(g2d, observer);
        }
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
