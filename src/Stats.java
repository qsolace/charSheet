import java.util.Random;


public class Stats {
    public int[] scores = {0, 0, 0, 0, 0, 0};
    public int[] modifier = {0, 0, 0, 0, 0, 0};
    public int[] saves = {0, 0, 0, 0, 0, 0};



    public void generate()//this generates one statistic by generating 4 random numbers from 1-6 and drops the lowest.
    {
        for (int j = 0; j < scores.length; j ++) {

            int[] rolls = {0, 0, 0, 0};//array to store the values
            int min = 20;//temporary min that will be higher than any possible rolled number
            int total = 0;//total of the entire stat.
            for (int i = 0; i < rolls.length; i++)//generates, determines the minimum, and adds the numbers together.
            {
                rolls[i] = Character.rand.nextInt(6) + 1;
                min = Math.min(min, rolls[i]);
                total += rolls[i];
            }

            scores[j] = total-min;//removes the minimum then returns the value.
        }
    }

    public void setScores(int[] scores) {
        this.scores = scores;
        createModifiers();
    }

    public int[] getScores() {
        return scores;
    }
    public String getScoresToString ()
    {
        String output = "";
        for (int i = 0; i < scores.length; i++)
        {
            output = output + scores[i] + " ";
        }
        return output;
    }

    public int[] getMods() {
        return modifier;
    }
    public String getModsToString ()
    {
        String output = "";
        for (int i = 0; i < modifier.length; i++)
        {
            output = output + modifier[i] + " ";
        }
        return output;
    }

    public int[] getSaves() {
        return saves;
    }
    public String getSavesToString ()
    {
        String output = "";
        for (int i = 0; i < saves.length; i++)
        {
            output = output + saves[i] + " ";
        }
        return output;
    }

    public void sortScores ()
    {
        int length = scores.length;//sorts the array into order
        for (int i =0; i < length; i++)
        {
            int currentStat = scores[i];
            int previous = i -1;

            while (previous >= 0 && scores[previous]>=currentStat)
            {
                scores[previous+1]=scores[previous];
                previous--;
            }
            scores[previous+1] = currentStat;
        }
    }

    public void createModifiers ()
    {
        for (int i = 0; i < modifier.length; i ++)
        {
            modifier[i] = (int)Math.floor((scores[i]-10)/2.0);
        }
        ArrayFunctions.copyArray(saves, modifier);
    }
    public void addSaveProficiency (int... proficiencyLocation)
    {

        for (int i = 0; i < proficiencyLocation.length; i++)
        {
            if (proficiencyLocation[i]<6)
            {
                saves[proficiencyLocation[i]] += 2;
            }
        }
    }
}
