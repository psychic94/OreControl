package psy.orecontrol;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Populates the generating chunk with one(1) type of ore.
 * Each type of ore being populated will have its own instance of OrePopulator.
 * 
 * @author psychic94
 * @version 0.1
 */
public class OrePopulator implements Runnable{
    /*
     * The configuration of the ore this instance populates
     */
    private ConfigurationSection oreConfig;
    
    public OrePopulator(ConfigurationSection oreConfig){
        this.oreConfig = oreConfig;
    }
    
    /*
     * Populates ore in 3 steps:
     * 1. Compares a random integer to a Poisson distribution to select the number of veins to generate
     * 2. Calls calcVein the selected number of times and adds the return value to the queue
     * 3. Iterates through the queue, changing the selected locations to the ore being populated
     */
    public void run(){
        int count = poissonRandom(oreConfig.getDouble("veinFrequency"));
    }
    
    /*
     * This is used to generate a random number weighted by a Poisson distribution.
     * 
     * @param mean The average number of veins per chunk
     * @return The number of veins to be generated in this chunk
     */
    private int poissonRandom(double mean){
        double sum = 0;
        double rand;
        int veins;
        do{
            rand = Math.random();
        }while(rand==0 || rand==1);
        for(int i=0; true; i++){
            sum +=Math.pow(mean, i)/factorial(i);
            if(sum>mean) return i-1;
        }        
    }    
    
    private int factorial(int in){
        return in * factorial(in-1);
    }
}
