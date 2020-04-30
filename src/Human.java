public class Human {
    private static int statIncrease[] = {1, 1, 1, 1, 1, 1};
    private static int speed = 30;
    private static int darkvisionRange = 0;

    private static String languages[] = {"Common", "choice"};
    private static String skills[] = {};
    private static String tools[] = {};
    private static String weapons[] = {};

//    public void applyAncestry(Stats stats)
//    {
//        for (int i = 0; i < stats.scores.length; i++)
//        {
//            className.assignedStats[i] += statIncrease[i];
//        }
//
//        className.setSpeed(speed);
//        className.setDarkvisionRange(darkvisionRange);
//    }
    public static void applyAncestry(Stats stats, Proficiency proficiency, Features features)
    {
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];
        }

        proficiency.addLanguage(languages);
        features.setDarkvisionRange(darkvisionRange);
        features.setSpeed(speed);
    }
}
