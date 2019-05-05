package at.ac.tuwien.catsandmice.client.characters;


import at.ac.tuwien.catsandmice.client.util.ClientConstants;
import at.ac.tuwien.catsandmice.client.world.SubwayRepresentation;
import at.ac.tuwien.catsandmice.dto.characters.Cat;
import at.ac.tuwien.catsandmice.dto.characters.Mouse;
import at.ac.tuwien.catsandmice.dto.util.MouseUpdateMessage;
import at.ac.tuwien.catsandmice.dto.world.Subway;
import at.ac.tuwien.catsandmice.dto.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MousePlayer extends Player {

    private boolean tryToEnter = false;
    private MouseRepresentation mouseRepresentation;

    public MousePlayer(MouseRepresentation mouseRepresentation) {
        this.mouseRepresentation = mouseRepresentation;
        setCharacter(mouseRepresentation.getMouse());

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
        mouseUpdateMessage.setMouse(mouseRepresentation.getMouse());
        mouseUpdateMessage.setContainedInUUID(mouseRepresentation.getMouse().getBoundaries().getUuid());

        String json = ClientConstants.getGson().toJson(mouseUpdateMessage);
        writer.println(json);
        writer.flush();
    }

    @Override
    public void draw(Graphics2D g2d, ImageObserver observer, World world) {
        if(!isInitialised() || mouseRepresentation.getMouse().getBoundaries().equals(world) ) {
            if(isInitialised() && mouseRepresentation.getMouse().isAlive() && tryToEnter) {
                for(Subway subway : world.getSubways()) {
                    SubwayRepresentation subwayRepresentation = new SubwayRepresentation(subway);
                    if(tryToEnter && subwayRepresentation.enterOrExit(this)) {
                        tryToEnter = false;
                        drawWorld(g2d, observer, world, subway);
                    }
                }
                if(tryToEnter) {
                    drawWorld(g2d, observer, world);
                }
            } else {
                drawWorld(g2d, observer, world);
            }
        } else {
            //we know we are in a subway, so only exiting is possible
            List<Subway> subways = world.getSubways().stream().filter(new Predicate<Subway>() {
                @Override
                public boolean test(Subway subwayRepresantation) {
                    return subwayRepresantation.getUuid().equals(mouseRepresentation.getMouse().getBoundaries().getUuid());
                }
            }).collect(Collectors.toList());

            Subway subway= subways.get(0);
            SubwayRepresentation subwayRepresentation = new SubwayRepresentation(subway);

            if(tryToEnter && subwayRepresentation.enterOrExit(this)) {
                //if it wants to exit and it succeeds to exit
                drawWorld(g2d, observer, world);
            } else {
                //if it either does not want to exit or fails to do so
                drawWorld(g2d, observer, world, subway);
            }

        }
        tryToEnter = false;
    }

    private void drawWorld(Graphics2D g2d, ImageObserver observer, World world) {
        super.draw(g2d, observer, world);
    }

    private void drawWorld(Graphics2D g2d, ImageObserver observer, World world, Subway subway) {
        for(Subway s : world.getSubways()) {
            SubwayRepresentation subwayRepresentation = new SubwayRepresentation(s);
            subwayRepresentation.draw(g2d, observer, !subway.equals(s));
        }
        for(Mouse mouse : subway.getContainedMice()) {
            if(mouse.isAlive()) {
                MouseRepresentation mouseRepresentation = new MouseRepresentation(mouse);
                mouseRepresentation.draw(g2d, observer);
            }
        }
        for(Cat cat : subway.getKnownCatLocations()) {
            CatRepresentation catRepresentation = new CatRepresentation(cat);
            catRepresentation.draw(g2d, observer);
        }
    }

    @Override
    public void enterSubway(SubwayRepresentation subway) {
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
        if(e.getKeyCode() == KeyEvent.VK_F) {
            if (mouseRepresentation.getMouse().getBoundaries() instanceof Subway) {
                SubwayRepresentation subwayRepresentation = new SubwayRepresentation((Subway) mouseRepresentation.getMouse().getBoundaries());
                mouseRepresentation.getMouse().setPingedExit(subwayRepresentation.getPingedExit(mouseRepresentation.getMouse()));
            }
        }
    }

    public void moveTo(int x, int y) {
        mouseRepresentation.getMouse().setX(x- mouseRepresentation.getWidth()/2);
        mouseRepresentation.getMouse().setY(y- mouseRepresentation.getHeight()/2);
    }

    public MouseRepresentation getMouseRepresentation() {
        return mouseRepresentation;
    }

    public void setMouseRepresentation(MouseRepresentation mouseRepresentation) {
        this.mouseRepresentation = mouseRepresentation;
    }
}
