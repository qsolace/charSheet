//This is one of a few ancestries. It has literally no fun things. It's a human. Humans are boring...
//Unless they can turn into a super magical human and have a partner who is part cat. That's a reference.
//If you don't get it, either I did a bad job, or you really need to watch She-Ra: Princesses of Power.
//Season 5 released a little while ago, and it's really good. It has given me the warm fuzzies for multiple days.
//I mean... nothing is happening in this class. It is the most boring of them all...

public class Human {
    private static int statIncrease[] = {1, 1, 1, 1, 1, 1};//increase all scores by 1
    private static int speed = 30;//speed
    private static int darkvisionRange = 0;//no darkvision = 0

    private static String languages[] = {"Common", "choice"};//languages
    private static String skills[] = {};
    private static String tools[] = {};
    private static String weapons[] = {};


    public static void applyAncestry(Stats stats, Proficiency proficiency, Features features)
    {
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];//modifies ability scores
        }

        proficiency.addLanguage(languages);//adds languages, darkvision, and speed
        features.setDarkvisionRange(darkvisionRange);
        features.setSpeed(speed);
    }//Pretty sad, right. Humans don't get any cool things. Would be a shame to be a human...
}
