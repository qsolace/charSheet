import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Fighter extends JFrame{

    Fighter (Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }

    Fighter (Stats stat)
    {
        this.stat = stat;
    }

    //    private int[] stats = {0, 0, 0, 0, 0, 0};
//    public int[] assignedStats = {0, 0, 0, 0, 0, 0};//{Str, Dex, Con, Int, Wis, Cha}
//    private int[] statModifiers = {0, 0, 0, 0, 0, 0};
    public Stats stat;
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private int[] proficientSave = {0, 2};
    private int[] saveBonus = {0, 0, 0, 0, 0, 0};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus = 2;
    private int hitDie = 10;
    private int bonusHP = 0;
    private int skillNumber = 2;
    private String ancestry;
    private int fightingStyle;

    private String equippedArmor = "";
    private int armorClass = 10;
    private boolean shieldEquip = false;


    private Proficiency proficiency = new Proficiency();


    private boolean strFighter;
    private boolean isEKnight = false;

    private Inventory inventory = new Inventory();

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


    public void fileOutput() throws IOException {
//        File output = new File("C:\\Users\\ryan_\\OneDrive\\Desktop\\Greenhill-DESKTOP-7HM548H\\10th Grade\\AP Comp Sci\\" + Character.seedString.stripTrailing() + ".txt");
//        FileWriter print = new FileWriter(output);
        Writer print = new StringWriter();
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + " Fighter " + level + "\n\n");
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
        for (int i = 0; i < saveBonus.length; i++) {
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
                    print.write(proficiency.getSkills()[i][j] + ": ");
                    if (bonus >= 0) {
                        print.write("+");
                    }
                    print.write(bonus + "\n");
                }
            }
        }

        print.write("-------------------\nWeapons:\n\n");
        for (int i = 0; i < inventory.getWeapons().length; i++) {
            if (!inventory.getWeapons()[i].isEmpty()) {
                String outputString = "";
                int attackBonus = 0;
                char attackSign = ' ';
                int damageBonus = 0;
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
                } else {
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
                if (!Weapons.ranged(inventory.getWeapons()[i]).isEmpty() && fightingStyle == 1)
                {
                    attackBonus+=2;
                }
                if (attackBonus >= 0) {
                    outputString += ("+");
                    attackSign = '+';
                }
                System.out.println(attackBonus);

                outputString += (attackBonus + "  " + Weapons.damage(inventory.getWeapons()[i]));//print damage die
                if (!inventory.getWeapons()[i].equalsIgnoreCase("net")) {
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
                    }

                    if (Weapons.isMelee(inventory.getWeapons()[i]) && !Weapons.isTwoHanded(inventory.getWeapons()[i]) && fightingStyle == 3) {
                        damageBonus += 2;
                    }
                    outputString += (damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));//damage type
                }
                else
                {
                    bonusSign = '-';
                }
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
                print.write("\n");
            }

        }
        print.write("\n\n\n");
        print.write(features.getFeatures());

        print.write("\n\n\nEquipped Armor:\n" + equippedArmor);
        if (shieldEquip) {
            print.write(", Shield\n");
        }

        print.write("\n\n\n\nINVENTORY:\n");
        print.write("   Weapons:\n      " + inventory.getWeaponsToString());
        print.write("\n   Armor:\n      " + inventory.getArmorToString());
        print.write("\n   Tools:\n      " + inventory.getToolsToString());
        print.write("\n   Other:\n      " + inventory.getMiscToString());




        print.write("\n\n\n\n\n" +
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
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\ryan_\\OneDrive\\Desktop\\Greenhill-DESKTOP-7HM548H\\10th Grade\\AP Comp Sci");
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

        strFighter = Character.rand.nextBoolean();//randomly determines if Str should be highest.

        if (strFighter)//if Str should be highest, does that and makes dex 3rd lowest
        {
            assignedStats[0] = stats[5];
            assignedStats[1] = stats[2];
        } else//otherwise, flip them
        {
            assignedStats[1] = stats[5];
            assignedStats[0] = stats[2];
        }

        assignedStats[2] = stats[4];//Assign Con as second highest stat.

        if (isEKnight)//if EK, Int is highest mental, then W/C randomly
        {
            assignedStats[3] = stats[3];

            int randomNum = Character.rand.nextInt(2);

            assignedStats[4] = stats[randomNum];
            assignedStats[5] = stats[Math.abs(randomNum - 1)];
        } else {//assigning mental stats. Random which is which.

            switch (Character.rand.nextInt(6)) {//the worst thing I've ever done. This will look better later
                case 0:
                    assignedStats[3] = stats[3];
                    assignedStats[4] = stats[0];
                    assignedStats[5] = stats[1];
                    break;
                case 1:
                    assignedStats[3] = stats[3];
                    assignedStats[4] = stats[1];
                    assignedStats[5] = stats[0];
                    break;
                case 2:
                    assignedStats[3] = stats[1];
                    assignedStats[4] = stats[3];
                    assignedStats[5] = stats[0];
                    break;
                case 3:
                    assignedStats[3] = stats[1];
                    assignedStats[4] = stats[0];
                    assignedStats[5] = stats[3];
                    break;
                case 4:
                    assignedStats[3] = stats[0];
                    assignedStats[4] = stats[1];
                    assignedStats[5] = stats[3];
                    break;
                case 5:
                    assignedStats[3] = stats[0];
                    assignedStats[4] = stats[3];
                    assignedStats[5] = stats[1];
                    break;


            }
        }
        stat.setScores(assignedStats);
    }


    public void createFirstLevel() throws IOException {

        assignStats();
        ancestry = decideAncestry(ancestry);
        stat.createModifiers();

//        skillIndex = concatenateArray(skills, skillIndex, decideSkills(2));
//        armorIndex = concatenateArray(armor, armorIndex, assignArmorProf());
//        weaponIndex = concatenateArray(weapons, weaponIndex, assignWeaponProf());
//
//        for (int i = 0; i <weaponIndex; i ++)
//        {
//            System.out.println(weapons[i]);
//        }

        System.out.println(isEKnight);
        decideSkills(2);
        proficiency.addWeapon(assignWeaponProf());
        proficiency.addArmor(assignArmorProf());


        hitPoints = hitDie + stat.modifier[2];
        if (strFighter) {
            equippedArmor = "Chain Mail";
            armorClass = 16;
        } else {
            equippedArmor = "Leather Armor";
            armorClass = 11 + stat.modifier[1];

            inventory.addWeapon("Longbow");
//            weaponInventoryIndex
        }

        if (strFighter) {
            inventory.addWeapon("Handaxe", "Handaxe");
        } else {
            inventory.addWeapon("Light Crossbow");
            inventory.addMisc("20 bolts");
        }

        boolean shieldMartial = Character.rand.nextBoolean();
        int weaponAmount = 1;
        String[] weaponOptions;
        if (!strFighter) {
            if (shieldMartial) {
                weaponOptions = Weapons.oneHandedWeapon(Weapons.martialDex());
                inventory.addArmor("Shield");
                armorClass+=2;
                shieldEquip = true;
            } else {
                weaponOptions = Weapons.martialDex();
                weaponAmount = 2;
            }
        } else {
            if (shieldMartial) {
                weaponOptions = Weapons.oneHandedWeapon(Weapons.martialStr());
                inventory.addArmor("Shield");
                armorClass+=2;
                shieldEquip = true;
            } else {
                weaponOptions = Weapons.martialStr();
                weaponAmount = 2;
            }
        }
        String weaponChoice = "";
        for (int i = 0; i < weaponAmount; i++) {
            weaponChoice = weaponOptions[Character.rand.nextInt(weaponOptions.length)];
            inventory.addWeapon(weaponChoice);
        }

        if (Character.rand.nextBoolean())
        {
            inventory.addDungeonerPack();
        }
        else
        {
            inventory.addExplorersPack();
        }
        fightingStyleDetermination(weaponChoice);
        features.addFeature("Second Wind", "You have a limited well of stamina that you can draw on to protect yourself from harm. " +
                "On your turn, you can use a bonus action to regain hit points equal to 1d10 + your fighter level. " +
                "Once you use this feature, you must finish a short or long rest before you can use it again.");

        inventory.addArmor(equippedArmor);
        stat.addSaveProficiency(proficientSave);
        fileOutput();
    }


    private String decideAncestry(String userAncestry) {
        String ancestry = userAncestry;
        if (ancestry.isEmpty()) {
            int[] stats = stat.scores;
            int ancestryChance[] = {2, 2, 1, 1, 1, 1, 1};
            String ancestryName[] = {"Mountain Dwarf", "Hill Dwarf", "Human", "High Elf", "Wood Elf", "Lightfoot Halfling", "Stout Halfling"};
            //mountain dwarf, hill dwarf, human, high elf, wood elf, lightfoot halfling, stout halfling
            if (strFighter) {
                ancestryChance[0] += 4;
                ancestryChance[1] += 2;

                if (stats[0] % 2 == 1) {
                    ancestryChance[2] += 4;
                }
                if (stats[2] % 2 == 1) {
                    ancestryChance[2] += 2;
                }
                if (isEKnight && stats[3] % 2 == 1) {
                    ancestryChance[2] += 2;
                    ancestryChance[3] += 2;
                }
            } else {
                ancestryChance[3] += 2;
                ancestryChance[4] += 2;
                ancestryChance[5] += 2;

                if (stats[2] % 2 == 1) {
                    ancestryChance[6] += 4;
                } else {
                    ancestryChance[6] += 2;
                }

                if (stats[1] % 2 == 1) {
                    ancestryChance[2] += 4;
                }
                if (stats[2] % 2 == 1) {
                    ancestryChance[2] += 2;
                }
                if (isEKnight && stats[3] % 2 == 1) {
                    ancestryChance[2] += 2;
                    ancestryChance[3] += 4;
                }
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
            System.out.println(ancestry);
        }

        if (ancestry.compareToIgnoreCase("human") == 0) {
            Human.applyAncestry(stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Hill Dwarf"))
        {
            bonusHP++;
            Dwarf.applyAncestry("Hill Dwarf", stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Mountain Dwarf"))
        {
            Dwarf.applyAncestry("Mountain Dwarf", stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Lightfoot Halfling"))
        {
            Halfling.applyAncestry("Lightfoot Halfling", stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Stout Halfling"))
        {
            Halfling.applyAncestry("Stout Halfling", stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("High Elf"))
        {
            Elf.applyAncestry("High Elf", stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Wood Elf"))
        {
            Elf.applyAncestry("Wood Elf", stat, proficiency, features);
        }


        return ancestry;


    }

    public void decideSkills(int numberOfSkills) {
        String[][] skillList = {
                {"Athletics"},
                {"Acrobatics"},
                {},
                {"History"},
                {"Animal Handling", "Insight", "Perception", "Survival"},
                {"Intimidation"}
        };

        for (int j = 0; j < numberOfSkills; j++) {
            int skillNumber = Character.rand.nextInt(Proficiency.countMultArray(skillList));

            for (int i = 0; i < skillList.length; i++) {
                if (skillList[i].length > skillNumber) {
                    int rerun = proficiency.addSkill(i, skillList[i][skillNumber]);
                    System.out.println(rerun);
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
        String armorList[] = {"Leather", "Studded Leather", "Padded", "Hide", "Breastplate", "Chain Shirt", "Half Plate", "Ring Mail", "Scale Mail", "Spiked Armor", "Chain Mail", "Splint", "Plate", "Shield"};

        proficiency.addArmor(armorList);
        return armorList;
    }

    private String[] assignWeaponProf() {
        String weaponList[] = {"Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear", "Light Crossbow", "Dart", "Shortbow", "Sling", "Battleaxe", "Flail", "Glaive",
                "Greataxe", "Greatsword", "Halberd", "Lance", "Longsword", "Maul", "Morningstar", "Pike", "Rapier", "Scimitar", "Shortsword", "Trident", "War Pick", "Warhammer", "Whip", "Blowgun", "Hand Crossbow", "Heavy Crossbow",
                "Longbow", "Net"};
        proficiency.addWeapon(weaponList);
        return weaponList;
    }


//----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Ability Fun Times<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    private int fightingStyleDetermination (String weapon)
    {
        int[] FSChance = {2, 0, 0, 0, 0, 0};
        int FSIndex = 1;

        if (Weapons.isMelee(weapon))
        {
            if (Weapons.isTwoHanded(weapon)) {
                FSChance[FSIndex] = 4;
            }
            else
            {
                FSChance[FSIndex] = 3;
            }
            FSIndex++;
        }
        if (!Weapons.ranged(weapon).isEmpty())
        {
            FSChance[FSIndex] = 1;
            FSIndex++;
        }
        if (shieldEquip)
        {
            FSChance[FSIndex] = 5;
            FSIndex++;
        }
        if (Weapons.isLight(weapon)&&Weapons.isMelee(weapon))
        {
            FSChance[FSIndex] = 6;
            FSIndex++;
        }

        fightingStyle = FSChance[Character.rand.nextInt(FSIndex)];
        if (fightingStyle == 2)
        {
            armorClass+=1;
        }

        switch (fightingStyle){
            case 1:
                features.addFeature("Fighting Style: Archery", "You gain a +2 bonus to attack rolls you make with ranged weapons.");
                break;
            case 2:
                features.addFeature("Fighting Style: Defense", "While you are wearing armor, you gain a +1 bonus to AC");
                break;
            case 3:
                features.addFeature("Fighting Style: Dueling", "When you are wielding a melee weapon in one hand and no other weapons, you gain a +2 bonus to damage rolls with that weapon.");
                break;
            case 4:
                features.addFeature("Fighting Style: Great Weapon Fighting", "When you roll a 1 or 2 on a damage die for an attack you make with a melee weapon that you are wielding with two hands, you can reroll the die and must use the new roll, " +
                        "even if the new roll is a 1 or a 2. The weapon must have the two-handed or versatile property for you to gain this benefit.");
                break;
            case 5:
                features.addFeature("Fighting Style: Protection", "When a creature you can see attacks a target other than you that is within 5 feet of you, " +
                        "you can use your reaction to impose disadvantage on the attack roll. You must be wielding a shield.");
                break;
            case 6:
                features.addFeature("Fighting Style: Two-Weapon Fighting", "When you engage in two-weapon fighting, you can add your ability modifier to the damage of the second attack.");
        }

        return fightingStyle;

    }

}

