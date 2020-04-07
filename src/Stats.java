import java.util.Random;


public class Stats {




    public static int generate()//this generates one statistic by generating 4 random numbers from 1-6 and drops the lowest.
    {
        Random rand = new Random();//create the random class object thing

        int[] rolls = {0,0,0,0};//array to store the values
        int min = 20;//temporary min that will be higher than any possible rolled number
        int total = 0;//total of the entire stat.
        for (int i = 0; i < rolls.length; i++)//generates, determines the minimum, and adds the numbers together.
        {
            rolls[i] = rand.nextInt(6)+1;
            min = Math.min(min, rolls[i]);
            total += rolls[i];
        }



        return total-min;//removes the minimum then returns the value.
    }
}
