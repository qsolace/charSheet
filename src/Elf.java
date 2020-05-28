//This is one of a few ancestries. It has fun things like cantrip selection and skill additions that other
//ancestries don't have.

public class Elf {
    private static int statIncrease[] = {0, 2, 0, 0, 0, 0};//dexterity increase
    private static int speed = 30;//speed
    private static int darkvisionRange = 60;//darkvision
//proficiencies to be added
    private static String languages[] = {"Common", "Elvish"};
    private static String tools[] = {};
    private static String weapons[] = {"Longsword", "Shortsword", "Shortbow", "Longbow"};


    public static void applyAncestry(String subclass, Stats stats, Proficiency proficiency, Features features, Spells... spells)
    {
        if (subclass.equalsIgnoreCase("High Elf"))
        {
            statIncrease[3] = 1;//another ability score increase

            proficiency.addLanguage("choice");//choice of language

            String[] wizardCantrip = {"Booming Blade", "Control Flames", "Friends", "Green-Flame Blade", "Gust", "Mage Hand", "Mending", "Message", "Minor Illusion", "Mold Earth",
            "Prestidigitation", "True Strike"};//list of possible cantrups
            String cantripName = wizardCantrip[Character.rand.nextInt(wizardCantrip.length)];
            features.addFeature("Cantrip", "You know the cantrip \"" + cantripName+"\" " +
                    "from the Wizard spell list. Intelligence is your spellcasting ability for it.");//randomly chooses one of the above cantrips to be added
            if (spells.length == 1)
            {
                spells[0].addCantrip(cantripName + "—Intelligence");
            }
        }
        else if(subclass.equalsIgnoreCase("Wood Elf"))
        {
            statIncrease[4] = 1;//ability score increase
            speed = 35;//speed change
            features.addFeature("Mask of the Wild", "You can attempt to hide even when you are only lightly obscured by foliage, " +
                    "heavy rain, falling snow, mist, and other natural phenomena.");//added feature
        }
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];//modifies the ability scores
        }

        proficiency.addWeapon(weapons);//adds all the proficiencies
        proficiency.addLanguage(languages);

        proficiency.addSkill(4, "Perception");//adds a skill and the features
        features.addFeature("Fey Ancestry", "You have advantage on saving throws against being charmed, and magic can’t put you to sleep.");
        features.addFeature("Trance", "Elves don’t need to sleep. Instead, they meditate deeply, remaining semiconscious, for 4 hours a day. " +
                "(The Common word for such meditation is “trance.”) While meditating, you can dream after a fashion; " +
                "such dreams are actually mental exercises that have become reflexive through years of practice. " +
                "After resting in this way, you gain the same benefit that a human does from 8 hours of sleep.");

        features.setDarkvisionRange(darkvisionRange);//adds darkvision and speed
        features.setSpeed(speed);
    }
}
