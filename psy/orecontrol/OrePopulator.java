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

    public boolean a(net.minecraft.server.v1_5_R3.World world, java.util.Random random, int i, int j, int k){
        float f = random.nextFloat() * 3.141593F;
        double d = (i + 8) + Math.sin(f) * veinSize/8;
        double d1 = (i + 8) - Math.sin(f) * veinSize/8;
        double d2 = (k + 8) + Math.cos(f) * veinSize/8;
        double d3 = (k + 8) - Math.cos(f) * veinSize/8;
        double d4 = (j + random.nextInt(3)) - 2;
        double d5 = (j + random.nextInt(3)) - 2;
        for(int oreNumber = 0; oreNumber <= veinSize; oreNumber++){
            double d6 = d + ((d1 - d) * oreNumber) / veinSize;
            double d7 = d4 + ((d5 - d4) * oreNumber) / veinSize;
            double d8 = d2 + ((d3 - d2) * oreNumber) / veinSize;
            double d9 = (random.nextDouble() * veinSize) / 16;
            double d10 = (Math.sin((oreNumber * 3.141593) / veinSize) + 1) * d9 + 1;
            double d11 = (Math.sin((oreNumber * 3.141593) / veinSize) + 1) * d9 + 1;
            int i1 = (int)Math.floor(d6 - d10 / 2);
            int j1 = (int)Math.floor(d7 - d11 / 2);
            int k1 = (int)Math.floor(d8 - d10 / 2);
            int l1 = (int)Math.floor(d6 + d10 / 2);
            int i2 = (int)Math.floor(d7 + d11 / 2);
            int j2 = (int)Math.floor(d8 + d10 / 2);
            for(int outX = i1; outX <= l1; outX++){
                double d12 = ((outX + 0.5) - d6) / (d10 / 2);
                if(d12 >= 1)
                    continue;
                for(int outY = j1; outY <= i2; outY++){
                    double d13 = ((outY + 0.5) - d7) / (d11 / 2);
                    if(d12 * d12 + d13 * d13 >= 1)
                        continue;
                    for(int outZ = k1; outZ <= j2; outZ++){
                        double d14 = ((outZ + 0.5) - d8) / (d10 / 2);
                        if(d12 * d12 + d13 * d13 + d14 * d14 < 1 && world.getTypeId(outX, outY, outZ) == replacedID)
                            world.setTypeIdAndData(outX, outY, outZ, blockID, 0, 2);
                    }
                }
            }
        }
        return true;
    }

    private int blockID;
    private int veinSize;
    private int replacedID;
}
