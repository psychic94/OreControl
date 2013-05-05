package psy.orecontrol;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used to store which chunks have populated with a new ore.
 * Each ore will have an instance of PopulatedChunkList.
 * 
 * @author psychic94
 * @version 0.1
 */
public class PopulatedChunkList extends ArrayList<String> implements Serializable{

    /**
     * Creates a new PopulatedChunkList with an empty {@link Set}
     */
    public PopulatedChunkList()
    {
        super();
    }
    
    /**
     * Adds a chunk to the list
     * Used when a chunk generates the ore
     * @param x The x coordinate of the chunk
     * @param z The z coordinate of the chunk
     * @param world The world the chunk is in
     */
    public void add(int x, int z, String world){
        super.add(world + "(" + x + "," + z + ")");
    }
    
    /**
     * Remove a chunk from the list
     * Used when a chunk degenerates the ore
     * @param x The x coordinate of the chunk
     * @param z The z coordinate of the chunk
     * @param world The world the chunk is in
     */
    public void remove(int x, int z, String world){
        super.remove(world + "(" + x + "," + z + ")");
    }
}