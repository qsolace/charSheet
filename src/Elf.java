public class Elf {
    private static int statIncrease[] = {0, 2, 0, 0, 0, 0};
    private static int speed = 30;
    private static int darkvisionRange = 60;

    private static String languages[] = {"Common", "Elvish"};
    private static String tools[] = {};
    private static String weapons[] = {"Longsword", "Shortsword", "Shortbow", "Longbow"};


    public static void applyAncestry(String subclass, Stats stats, Proficiency proficiency, Features features)
    {
        if (subclass.equalsIgnoreCase("High Elf"))
        {
            statIncrease[3] = 1;

            proficiency.addLanguage("choice");

            String[] wizardCantrip = {"Booming Blade", "Control Flames", "Friends", "Green-Flame Blade", "Gust", "Mage Hand", "Mending", "Message", "Minor Illusion", "Mold Earth",
            "Prestidigitation", "True Strike"};
            features.addFeature("Cantrip", "You know the cantrip \"" + wizardCantrip[Character.rand.nextInt(wizardCantrip.length)]+"\" " +
                    "from the Wizard spell list. Intelligence is your spellcasting ability for it.");
        }
        else if(subclass.equalsIgnoreCase("Wood Elf"))
        {
            statIncrease[4] = 1;
            speed = 35;
            features.addFeature("Mask of the Wild", "You can attempt to hide even when you are only lightly obscured by foliage, " +
                    "heavy rain, falling snow, mist, and other natural phenomena.");
        }
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];
        }

        proficiency.addWeapon(weapons);
        proficiency.addLanguage(languages);

        proficiency.addSkill(4, "Perception");
        features.addFeature("Fey Ancestry", "You have advantage on saving throws against being charmed, and magic can’t put you to sleep.");
        features.addFeature("Trance", "Elves don’t need to sleep. Instead, they meditate deeply, remaining semiconscious, for 4 hours a day. " +
                "(The Common word for such meditation is “trance.”) While meditating, you can dream after a fashion; " +
                "such dreams are actually mental exercises that have become reflexive through years of practice. " +
                "After resting in this way, you gain the same benefit that a human does from 8 hours of sleep.");

        features.setDarkvisionRange(darkvisionRange);
        features.setSpeed(speed);
    }
}
