package at.ac.tuwien.catsandmice.server.computer;

import at.ac.tuwien.catsandmice.dto.world.World;

public interface IComputerPlayer {
    /**
     * moves the ai controller player in the world
     * @param world the world to move in
     */
    void move(World world);
}
