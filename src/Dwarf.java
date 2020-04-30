public class Dwarf {
    private static int statIncrease[] = {0, 0, 2, 0, 0, 0};
    private static int speed = 25;
    private static int darkvisionRange = 60;

    private static String languages[] = {"Common", "Dwarvish"};
    private static String tools[] = {"Smith's Tools", "Brewer's Supplies", "Mason's Tools"};
    private static String weapons[] = {"Battleaxe", "Handaxe", "Light Hammer", "Warhammer"};


    public static void applyAncestry(String subclass, Stats stats, Proficiency proficiency, Features features)
    {
        if (subclass.equalsIgnoreCase("Hill Dwarf"))
        {
            statIncrease[4] = 1;

            proficiency.addLanguage("choice");

        }
        else if(subclass.equalsIgnoreCase("Mountain Dwarf"))
        {
            statIncrease[0] = 2;
            proficiency.addArmor("Breastplate", "Chain Shirt", "Half Plate", "Hide", "Leather", "Padded", "Scale Mail", "Studded Leather");
        }
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];
        }

        proficiency.addWeapon(weapons);
        proficiency.addLanguage(languages);
        String tool = tools[Character.rand.nextInt(tools.length)];
        System.out.println("YO, Friend"+tool);
        proficiency.addTool(tool);

        features.addFeature("Dwarven Resilience", "You have advantage on saving throws against poison, and you have resistance against poison damage");
        features.addFeature("Stonecunning", "Whenever you make an Intelligence (History) check related to the origin of stonework, " +
                "you are considered proficient in the History skill and add double your proficiency bonus to the check, instead of your normal proficiency bonus.");

        features.setDarkvisionRange(darkvisionRange);
        features.setSpeed(speed);
    }
}
