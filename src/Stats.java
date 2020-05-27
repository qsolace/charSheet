//This class handles all of the base statistics and modifiers for the game. It generates them and provides them when other classes need them

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
        createModifiers();//this sets the scores for the statistics. This is used if the user enters them
    }

    public int[] getScores() {
        return scores;//this returns the array with the scores.
    }
    public String getScoresToString ()//this gets the scores in a string. It was only really used for testing
    {
        String output = "";
        for (int i = 0; i < scores.length; i++)
        {
            output = output + scores[i] + " ";
        }
        return output;
    }

    public int[] getMods() {//this gets the statistic modifiers as an array
        return modifier;
    }
    public String getModsToString ()//this gets the mods as a string, again, only used for testing
    {
        String output = "";
        for (int i = 0; i < modifier.length; i++)
        {
            output = output + modifier[i] + " ";
        }
        return output;
    }

    public int[] getSaves() {//this gets the saves as an array
        return saves;
    }
    public String getSavesToString ()//gets the save modifiers as a string
    {
        String output = "";
        for (int i = 0; i < saves.length; i++)
        {
            output = output + saves[i] + " ";
        }
        return output;
    }

    public void sortScores ()//this sorts the scores from the largest to the smallest.
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

    public void createModifiers ()//this converts the statistics into the modifiers. This is achieved by subtracting 10 from the score, dividing by 2 and
            //rounding down.
    {
        for (int i = 0; i < modifier.length; i ++)
        {
            modifier[i] = (int)Math.floor((scores[i]-10)/2.0);
        }
        ArrayFunctions.copyArray(saves, modifier);
    }
    public void addSaveProficiency (int... proficiencyLocation)//This will add a number to the save modifier if a character is proficient by that save,
            //determined by the class.
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
