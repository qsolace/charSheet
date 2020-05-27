//This is one of a few ancestries. It has fun things like randomized tool proficiency determination that other
//ancestries don't have.


public class Dwarf {
    private static int statIncrease[] = {0, 0, 2, 0, 0, 0};//increase Con
    private static int speed = 25;//25 speed
    private static int darkvisionRange = 60;//darkvision to 60 ft

    private static String languages[] = {"Common", "Dwarvish"};//these are the proficiencies that Dwarves get.
    private static String tools[] = {"Smith's Tools", "Brewer's Supplies", "Mason's Tools"};
    private static String weapons[] = {"Battleaxe", "Handaxe", "Light Hammer", "Warhammer"};


    public static void applyAncestry(String subclass, Stats stats, Proficiency proficiency, Features features)
    {
        if (subclass.equalsIgnoreCase("Hill Dwarf"))
        {
            statIncrease[4] = 1;//another increase

            proficiency.addLanguage("choice");//choice of language

        }
        else if(subclass.equalsIgnoreCase("Mountain Dwarf"))
        {
            statIncrease[0] = 2;//strength increase
            proficiency.addArmor("Breastplate", "Chain Shirt", "Half Plate", "Hide", "Leather", "Padded", "Scale Mail", "Studded Leather");//armor proficiencies
        }
        for (int i = 0; i < stats.scores.length; i++)
        {
            stats.scores[i] += statIncrease[i];//this modifies the stats accordingly
        }
//this adds the proficienccies
        proficiency.addWeapon(weapons);
        proficiency.addLanguage(languages);
        String tool = tools[Character.rand.nextInt(tools.length)];//chooses a tool to add at random
        proficiency.addTool(tool);
//adds the Dwarf traits to be displayed
        features.addFeature("Dwarven Resilience", "You have advantage on saving throws against poison, and you have resistance against poison damage");
        features.addFeature("Stonecunning", "Whenever you make an Intelligence (History) check related to the origin of stonework, " +
                "you are considered proficient in the History skill and add double your proficiency bonus to the check, instead of your normal proficiency bonus.");

        features.setDarkvisionRange(darkvisionRange);//adds darkvision and speed to features
        features.setSpeed(speed);
    }
}
