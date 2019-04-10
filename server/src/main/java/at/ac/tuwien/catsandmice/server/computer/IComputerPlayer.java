package at.ac.tuwien.catsandmice.server.computer;

import at.ac.tuwien.catsandmice.dto.world.World;

public interface IComputerPlayer {
    void move(World world);
    void setSpeed(int speed);
    int getSpeed();
}
