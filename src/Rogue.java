import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Rogue {
    public Stats stat = new Stats();
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private int[] proficientSave = {1, 3};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus =2;
    private int hitDie = 8;
    private int bonusHP = 0;
    private int skillNumber = 4;
    private String ancestry;

    private String equippedArmor = "";
    private int armorClass = 10;


    private Proficiency proficiency = new Proficiency();




    private Inventory inventory = new Inventory();

    Rogue (Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }

    Rogue ()
    {}

//----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INPUT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------


    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
    }


    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUTPUT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------


    public String getSkills() {
        return proficiency.getSkillsToString();
    }

    public int getHitDie() {
        return hitDie;
    }


    public int getHitPoints() {
        return hitPoints;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public String getLanguages() {
        return proficiency.getLanguageToString();
    }


    public void fileOutput() throws IOException{
        Writer print = new StringWriter();
//        FileWriter print = new FileWriter(output);
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + " Rogue " + level + "\n\n");
        print.write("AC: " + armorClass + "         HP: " + (hitPoints +bonusHP)+ "       Speed: " + features.getSpeed() + "ft\n\n");
        for (int i = 0; i < stat.modifier.length; i++) {
            print.write(statNames[i] + ": " + stat.scores[i] + "(");
            if (stat.modifier[i] >= 0) {
                print.write("+");
            }
            print.write(stat.modifier[i] + ")" + "      ");
            if (i == 2) {
                print.write("\n");
            }
        }

        print.write("\n-------------------\nSaving Throws:\n\n");
        for (int i = 0; i < statNames.length; i++) {
            if (i == proficientSave[0] || i == proficientSave[1]) {
                print.write(statNames[i] + " Saving Throw: ");
                if (stat.saves[i] >= 0) {
                    print.write("+");
                }
                print.write(stat.saves[i] + "\n");
            }
        }

        print.write("-------------------\nSkills:\n\n");
        for (int i = 0; i < proficiency.getSkills().length; i++) {
            for (int j = 0; j < proficiency.getSkills()[i].length; j++) {
                if (!proficiency.getSkills()[i][j].isEmpty()) {
                    int bonus = profBonus + stat.modifier[i];
                    if (proficiency.isExpertise(proficiency.getSkills()[i][j]))
                    {
                        bonus += profBonus;
                    }
                    print.write(proficiency.getSkills()[i][j] + ": ");
                    if (bonus >= 0) {
                        print.write("+");
                    }
                    print.write(bonus + "\n");
                }
            }


        }
        print.write("Thieves' Tools: ");
        int theivesBonus = stat.modifier[1] + profBonus;
        if (proficiency.isExpertise("Thieves' Tools"))
        {
            theivesBonus += profBonus;
        }
        if (theivesBonus >= 0) {
            print.write("+");
        }
        print.write(theivesBonus + "\n");

        print.write("-------------------\nWeapons:\n\n");
        for (int i = 0; i < inventory.getWeapons().length; i++) {
            if (!inventory.getWeapons()[i].isEmpty()) {
                String outputString = "";
                int attackBonus = 0;
                char attackSign = ' ';
                int damageBonus;
                char bonusSign = ' ';
                boolean isStr;
                outputString += (inventory.getWeapons()[i] + "  ");//print weapon name
                if (stat.modifier[0] > stat.modifier[1])//determine attack bonus
                {
                    if (Weapons.isStr(inventory.getWeapons()[i])) {
                        attackBonus += stat.modifier[0];
                        isStr = true;
                    } else {
                        attackBonus += stat.modifier[1];
                        isStr = false;
                    }
                }
                else {
                    if (Weapons.isDex(inventory.getWeapons()[i])) {
                        attackBonus += stat.modifier[1];
                        isStr = false;
                    } else {
                        attackBonus += stat.modifier[0];
                        isStr = true;
                    }
                }
                if (proficiency.isProficientWeapon(inventory.getWeapons()[i]))//determine if proficient
                {
                    attackBonus += profBonus;
                }

                if (attackBonus >= 0) {
                    outputString += ("+");
                    attackSign = '+';
                }
                System.out.println(attackBonus);

                outputString += (attackBonus + "  " + Weapons.damage(inventory.getWeapons()[i]));//print damage die

                if (isStr) {
                    if (stat.modifier[0] >= 0) {
                        outputString += ("+");
                        bonusSign = '+';
                    }
                    damageBonus = stat.modifier[0];
                } else {
                    if (stat.modifier[1] >= 0) {
                        outputString += ("+");
                        bonusSign = '+';
                    }
                    damageBonus = stat.modifier[1];//adding damage modifier
                    System.out.println("MOD:" +stat.modifier[1]);
                    System.out.println("BONUS: " + damageBonus);
                }

                outputString += (damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));//damage type
                print.write(outputString);
                if (!Weapons.versatile(inventory.getWeapons()[i]).equalsIgnoreCase("Not Versatile")) {

                    print.write("\n" + inventory.getWeapons()[i] + "(two-handed) " + attackSign + attackBonus + " " + Weapons.versatile(inventory.getWeapons()[i]) +
                            bonusSign + damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));
                }

                outputString = Weapons.thrown(inventory.getWeapons()[i]) + Weapons.ranged(inventory.getWeapons()[i]);
                if (!outputString.isEmpty()) {
                    print.write("  |  " + outputString);
                }
                if (Weapons.isLight(inventory.getWeapons()[i])) {
                    print.write("  |  Light");
                }
                if (Weapons.isHeavy(inventory.getWeapons()[i])) {
                    print.write("  |  Heavy");
                }
                if (Weapons.isDex(inventory.getWeapons()[i]))
                {
                    print.write("  |  Sneak Attack: " + (int)Math.floor((level+1)/2.0)+"d6");
                }
                print.write("\n");
            }

        }
        print.write("\n\n\n");
        print.write(features.getFeatures());

        print.write("\n\n\nEquipped Armor:\n" + equippedArmor);


        print.write("\n\n\n\nINVENTORY:\n");
        print.write("   Weapons:\n      " + inventory.getWeaponsToString());
        print.write("\n   Armor:\n      " + inventory.getArmorToString());
        print.write("\n   Tools:\n      " + inventory.getToolsToString());
        print.write("\n   Other:\n      " + inventory.getMiscToString());




        print.write("\n\n\n\n\n\n\n\n" +
                "Weapon Proficiencies:\n");
        for (int i = 0; i < proficiency.getWeapons().length; i++) {
            if (!proficiency.getWeapons()[i].isEmpty()) {
                if (i > 0) {
                    print.write(", ");
                }
                print.write(proficiency.getWeapons()[i]);
            } else {
                if (i == 0) {
                    print.write("none");
                    break;
                }
            }
        }

        print.write("\n\n-------------------\n" +
                "Armor Proficiencies:\n");
        for (int i = 0; i < proficiency.getArmor().length; i++) {
            if (!proficiency.getArmor()[i].isEmpty()) {
                if (i > 0) {
                    print.write(", ");
                }
                print.write(proficiency.getArmor()[i]);
            } else {
                if (i == 0) {
                    print.write("none");
                    break;
                }
            }
        }
        print.write("\n\n-------------------\n" +
                "Tool Proficiencies:\n");
        for (int i = 0; i < proficiency.getTools().length; i++) {
            if (!proficiency.getTools()[i].isEmpty()) {
                if (i > 0) {
                    print.write(", ");
                }
                print.write(proficiency.getTools()[i]);
            } else {
                if (i == 0) {
                    print.write("none");
                    break;
                }
            }
        }

        print.close();

        JTextArea label = new JTextArea(print.toString());
        label.setFont(new Font("Comic Sans", Font.PLAIN, 10));
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setPreferredSize( new Dimension( 900, 700) );
        JOptionPane.showMessageDialog(null, scrollPane, Character.seedString.stripTrailing(), JOptionPane.PLAIN_MESSAGE);


        JFrame frame = new JFrame();
        frame.setSize(new Dimension(300, 300));
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Where do you want to save " + Character.seedString.stripTrailing() + "?");
        jFileChooser.showSaveDialog(frame);
        String path = "";
        try {
            path = jFileChooser.getSelectedFile().getAbsolutePath();
        }
        catch (java.lang.NullPointerException e)
        {
            System.exit(0);
        }

        File outputFile = new File (path+".txt");
        FileWriter outputWrite = new FileWriter(outputFile);
        outputWrite.write(print.toString());
        outputWrite.close();
        System.exit(0);
    }

    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MANIPULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    private void assignStats() {
        stat.sortScores();
        int[] stats = stat.scores;
        int[] assignedStats = new int[6];



        assignedStats[1] = stats[5];//Dex highest
        assignedStats[0] = stats[1];//Str 2nd lowest


        assignedStats[2] = stats[3];//Assign Con as third highest stat.


        switch (Character.rand.nextInt(6)) {//the worst thing I've ever done. This will look better later
            case 0:
                assignedStats[3] = stats[4];
                assignedStats[4] = stats[2];
                assignedStats[5] = stats[0];
                break;
            case 1:
                assignedStats[3] = stats[4];
                assignedStats[4] = stats[0];
                assignedStats[5] = stats[2];
                break;
            case 2:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[4];
                assignedStats[5] = stats[0];
                break;
            case 3:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[0];
                assignedStats[5] = stats[4];
                break;
            case 4:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[2];
                assignedStats[5] = stats[4];
                break;
            case 5:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[4];
                assignedStats[5] = stats[2];
                break;


        }
        stat.setScores(assignedStats);
        }




    public void createFirstLevel() throws IOException {

        stat.generate();
        assignStats();
        ancestry = decideAncestry();
        stat.createModifiers();


        decideSkills(skillNumber);
        System.out.println(proficiency.getSkillsToString());

        proficiency.addWeapon(assignWeaponProf());
        proficiency.addArmor(assignArmorProf());


        hitPoints = hitDie + stat.modifier[2];

        equippedArmor = "Leather Armor";
        armorClass = 11 + stat.modifier[1];

        inventory.addWeapon("Dagger", "Dagger");
        inventory.addTool("Thieves' Tools");
//            weaponInventoryIndex

        if (Character.rand.nextBoolean())
        {
            inventory.addWeapon("Shortbow", "Rapier");
            inventory.addMisc("Quiver with 20 arrows");
        }
        else
        {
            inventory.addWeapon("Shortsword", "Shortsword");
        }


        switch (Character.rand.nextInt(3))
        {
            case 0:
                inventory.addExplorersPack();
                break;
            case 1:
                inventory.addDungeonerPack();
                break;
            case 2:
                inventory.addBurglarsPack();
                break;
        }
        //expertise
        String[] skillsT = proficiency.skillsOneDArray();
        System.out.println("The thing: " + proficiency.skillsOneDArray().length);
        int randomization = -1;
        for (int i = 0; i < 2; i++) {
            int tempInt = Character.rand.nextInt(skillsT.length + 1);
            System.out.println(tempInt + " " + randomization);
            if (tempInt!=randomization) {
                randomization = tempInt;
                if (randomization == skillsT.length) {
                    proficiency.addExpertise("Thieves' Tools");
                } else {
                    proficiency.addExpertise(skillsT[randomization]);
                }
            }
            else
            {
                i--;
            }
        }



        features.addFeature("Sneak Attack", "Beginning at 1st level, you know how to strike subtly and exploit a foe’s distraction. " +
                "Once per turn, you can deal an extra 1d6 damage to one creature you hit with an attack if you have advantage on the attack roll. " +
                "The attack must use a finesse or a ranged weapon.\n\n"+
                "You don’t need advantage on the attack roll if another enemy of the target is within 5 feet of it, that enemy isn’t incapacitated, and you don’t have disadvantage on the attack roll.\n\n" +
                "The amount of the extra damage increases as you gain levels in this class, as shown in the Sneak Attack column of the Rogue table.");

        features.addFeature("Thieves' Cant", "During your rogue training you learned thieves’ cant, a secret mix of dialect, jargon, and code that allows you to hide messages in seemingly normal conversation. " +
                "Only another creature that knows thieves’ cant understands such messages. " +
                "It takes four times longer to convey such a message than it does to speak the same idea plainly.\n" +
                "\n" +
                "In addition, you understand a set of secret signs and symbols used to convey short, simple messages, such as whether an area is dangerous or the territory of a thieves’ guild, " +
                "whether loot is nearby, or whether the people in an area are easy marks or will provide a safe house for thieves on the run.");

        inventory.addArmor(equippedArmor);
        stat.addSaveProficiency(proficientSave);
        fileOutput();
    }


    private String decideAncestry() {
        int[] stats = stat.scores;

        if (ancestry.isEmpty()) {
            int ancestryChance[] = {1, 1, 1, 2, 2, 2, 2};
            String ancestryName[] = {"Mountain Dwarf", "Hill Dwarf", "Human", "High Elf", "Wood Elf", "Lightfoot Halfling", "Stout Halfling"};


            if (stat.scores[2]%2==1)
            {
                ancestryChance[2] += 1;
                ancestryChance[6] += 2;
            }
            if (stat.scores[3]%2==1)
            {
                ancestryChance[2] += 1;
                ancestryChance[3] += 1;
            }

            int totalWeight = 0;
            for (int i = 0; i < ancestryChance.length; i++) {
                totalWeight += ancestryChance[i];
            }

            int decidedNumber = Character.rand.nextInt(totalWeight) + 1;

            int ancestryNumber = -1;
            while (decidedNumber > 0) {
                ancestryNumber++;
                decidedNumber -= ancestryChance[ancestryNumber];
            }

            ancestry = ancestryName[ancestryNumber];
        }
            System.out.println("ANCESTRY!!!!" + ancestry);

            if (ancestry.compareToIgnoreCase("human") == 0) {
                Human.applyAncestry(stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("Hill Dwarf")) {
                bonusHP++;
                Dwarf.applyAncestry("Hill Dwarf", stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("Mountain Dwarf")) {
                Dwarf.applyAncestry("Mountain Dwarf", stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("Lightfoot Halfling")) {
                Halfling.applyAncestry("Lightfoot Halfling", stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("Stout Halfling")) {
                Halfling.applyAncestry("Stout Halfling", stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("High Elf")) {
                Elf.applyAncestry("High Elf", stat, proficiency, features);
            } else if (ancestry.equalsIgnoreCase("Wood Elf")) {
                Elf.applyAncestry("Wood Elf", stat, proficiency, features);
            }




        return ancestry;


    }

    public void decideSkills(int numberOfSkills) {
        String[][] skillList = {
                {"Athletics"},
                {"Acrobatics", "Sleight of Hand", "Stealth"},
                {},
                {"Investigation"},
                {"Insight", "Perception"},
                {"Deception", "Performance", "Intimidation", "Persuasion"}
        };
        System.out.println("THing 1:" + numberOfSkills);
        for (int j = 0; j < numberOfSkills; j++) {
            int skillNumber = Character.rand.nextInt(Proficiency.countMultArray(skillList));

            for (int i = 0; i < skillList.length; i++) {
                if (skillList[i].length > skillNumber) {
                    int rerun = proficiency.addSkill(i, skillList[i][skillNumber]);

                    System.out.println(skillList[i][skillNumber]);
                    if (rerun > 0) {
                        decideSkills(rerun);
                    }
                    break;
                } else {
                    skillNumber -= skillList[i].length;
                }
            }
        }
    }

    private String[] assignArmorProf() {
        String armorList[] = {"Leather", "Studded Leather", "Padded", "Hide"};

        proficiency.addArmor(armorList);
        return armorList;
    }

    private String[] assignWeaponProf() {
        String weaponList[] = {"Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear", "Light Crossbow", "Dart", "Shortbow", "Sling", "Hand Crossbow",
        "Longsword", "Rapier", "Shortsword"};
        proficiency.addWeapon(weaponList);
        return weaponList;
    }
    private String[] assignToolProf(){
        String toolList[] = {"Thieves' Tools"};
        proficiency.addWeapon(toolList);
        return toolList;
    }


    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Ability Fun Times<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------

}
