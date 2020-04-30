public class Halfling {

    private static int statIncrease[] = {0, 2, 0, 0, 0, 0};
    private static int speed = 25;
    private static int darkvisionRange = 0;

    private static String languages[] = {"Common", "Halfling"};
    private static String tools[] = {};
    private static String weapons[] = {};


    public static void applyAncestry(String subclass, Stats stats, Proficiency proficiency, Features features) {
        if (subclass.equalsIgnoreCase("Lightfoot Halfling")) {
            statIncrease[5] = 1;


            features.addFeature("Naturally Stealthy", "You can attempt to hide even when you are obscured only by a creature that is at least one size larger than you.");
        } else if (subclass.equalsIgnoreCase("Stout Halfling")) {
            statIncrease[2] = 1;
            features.addFeature("Stout Resilience", "You have advantage on saving throws against poison, and you have resistance against poison damage.");
        }
        for (int i = 0; i < stats.scores.length; i++) {
            stats.scores[i] += statIncrease[i];
        }

        proficiency.addWeapon(weapons);
        proficiency.addLanguage(languages);

        features.addFeature("Lucky", "When you roll a 1 on the d20 for an attack roll, ability check, or saving throw, you can reroll the die and must use the new roll.");
        features.addFeature("Brave", "You have advantage on saving throws against being frightened.");
        features.addFeature("Halfling Nimbleness", "You can move through the space of any creature that is of a size larger than yours.");

        features.setDarkvisionRange(darkvisionRange);
        features.setSpeed(speed);

        features.setMedium(false);
    }


}
