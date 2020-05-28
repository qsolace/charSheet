//The Barbarian is one of the three "class" classes. It has Rage bonus, a unique feature, as well as a secondary
//Armor Class determination method.
import javax.swing.*;
import java.awt.*;
import java.io.*;
//mostly the same as the fighter. I'll mark any differences
public class Barbarian extends JFrame{

    Barbarian(Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }

    Barbarian(Stats stat)
    {
        this.stat = stat;
    }

    public Stats stat;
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private int[] proficientSave = {0, 2};//different saving throws
    private int[] saveBonus = {0, 0, 0, 0, 0, 0};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus = 2;
    private int hitDie = 12;//different hit dice
    private int bonusHP = 0;
    private int skillNumber = 2;
    private String ancestry;
    private String background;
    private int rageBonus = 2;//rage bonus is used when adding weapons
    private boolean shieldEquip = false;
    private boolean isMedium = true;
    private int rageUses = 2;//rage uses is used when adding the "rage" feature.

    private String equippedArmor = "";
    private int armorClass = 10;


    private Proficiency proficiency = new Proficiency();
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
        return proficiency.getLanguageToString(" ");
    }


    public void fileOutput() throws IOException {

        Writer print = new StringWriter();
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + " Barbarian " + level + "        Background: "+background+"\n\n");//different class name
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
                    print.write(bonus+"");

                    if (proficiency.getSkills()[i][j].equalsIgnoreCase("Stealth")&&equippedArmor.matches("Ring Mail|Chain Mail|Splint|Plate"))
                    {
                        print.write(" (disadvantage)");
                    }
                    print.write("\n");
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


                    outputString += (damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));//damage type
                }
                else
                {
                    bonusSign = '-';
                }
                print.write(outputString);
                print.write("  |  Rage Bonus: " + rageBonus);//adds rage bonus if it is a strength weapon

                if (!Weapons.versatile(inventory.getWeapons()[i]).equalsIgnoreCase("Not Versatile")) {

                    print.write("\n" + inventory.getWeapons()[i] + "(two-handed) " + attackSign + attackBonus + " " + Weapons.versatile(inventory.getWeapons()[i]) +
                            bonusSign + damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));
                    print.write("  |  Rage Bonus: " + rageBonus);//adds rage bonus if it is a strength weapon
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
        print.write("\n\n   "+inventory.getMoneyToString("\n   "));



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
        print.write("\n\n-------------------\n" +
                "Languages:\n"+proficiency.getLanguageToString(", "));

        print.close();
        JTextArea label = new JTextArea(print.toString());
        label.setFont(new Font("Comic Sans", Font.PLAIN, 10));
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setPreferredSize( new Dimension( 900, 700) );
        JOptionPane.showMessageDialog(null, scrollPane, Character.seedString.stripTrailing(), JOptionPane.PLAIN_MESSAGE);


        JFrame frame = new JFrame();
        frame.setSize(new Dimension(300, 300));
        JFileChooser jFileChooser = new JFileChooser("C:");
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



        assignedStats[0] = stats[5];//strength as highest stat
        assignedStats[1] = stats[3];//dex as third highest
        assignedStats[2] = stats[4];//Assign Con as second highest stat.


        switch (Character.rand.nextInt(6)) {//the worst thing I've ever done. Assign mental stats
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
        stat.setScores(assignedStats);
    }


    public void createFirstLevel() throws IOException {

        assignStats();
        ancestry = decideAncestry(ancestry);
        stat.createModifiers();

        background = decideBackground();
        Backgrounds.addBackground(background, proficiency, inventory, features);

        decideSkills(2);
        proficiency.addWeapon(assignWeaponProf());
        proficiency.addArmor(assignArmorProf());
        armorClass = stat.modifier[1] + stat.modifier[2] + 10;//AC is determined with: 10 + Dex bonus + Con bonus; no armor is added

        hitPoints = hitDie + stat.modifier[2];


        int weaponAmount = 1;
        String[] weaponOptions;
        if (isMedium)//chooses weapons based on size.
        {
            weaponOptions = new String[]{"Greataxe", "Greatsword", "Maul"};
        }
        else
        {
            weaponOptions = new String[]{"Longsword", "Battleaxe", "Warhammer"};
        }
        String weaponChoice = "";
        for (int i = 0; i < weaponAmount; i++) {
            weaponChoice = weaponOptions[Character.rand.nextInt(weaponOptions.length)];
            inventory.addWeapon(weaponChoice);
        }



        inventory.addExplorersPack();//add pack: there is no choice
        inventory.addWeapon("Handaxe", "Handaxe","Javelin", "Javelin", "Javelin", "Javelin");//adds weapons to inventory: there is no choice
        //add feature descriptions
        features.addFeature("Rage", "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action.\n" +
                "\n" +
                "While raging, you gain the following benefits if you aren’t wearing heavy armor:\n" +
                "\n" +
                "- You have advantage on Strength checks and Strength saving throws.\n" +
                "- When you make a melee weapon attack using Strength, you gain a bonus to the damage roll that increases as you gain levels as a barbarian, as shown in the Rage Damage column of the Barbarian table ("+rageBonus+").\n" +
                "- You have resistance to bludgeoning, piercing, and slashing damage.\n" +
                "If you are able to cast spells, you can’t cast them or concentrate on them while raging.\n" +
                "\n" +
                "Your rage lasts for 1 minute. It ends early if you are knocked unconscious or if your turn ends and you haven’t attacked a hostile creature since your last turn or taken damage since then. You can also end your rage on your turn as a bonus action.\n" +
                "\n" +
                "Once you have raged "+rageUses+" times, you must finish a long rest before you can rage again.", rageUses);

        features.addFeature("Unarmored Defense", "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your Constitution modifier. You can use a shield and still gain this benefit.");

        inventory.addArmor(equippedArmor);
        stat.addSaveProficiency(proficientSave);
        fileOutput();
    }

    private String decideBackground()
    {
        String[] backgroundOptions = {"Acolyte", "Criminal", "Folk Hero", "Noble", "Sage", "Soldier"};
        int[] backgroundChance = {3, 7, 7, 3, 5, 6};

        int totalWeight = 0;
        for (int i = 0; i < backgroundChance.length; i++) {
            totalWeight += backgroundChance[i];
        }
        int decidedNumber = Character.rand.nextInt(totalWeight) + 1;

        int backgroundNumber = -1;
        while (decidedNumber > 0) {
            backgroundNumber++;
            decidedNumber -= backgroundChance[backgroundNumber];
        }

        return backgroundOptions[backgroundNumber];


    }
    private String decideAncestry(String userAncestry) {
        String ancestry = userAncestry;
        if (ancestry.isEmpty()) {
            int[] stats = stat.scores;
            int ancestryChance[] = {3, 3, 2, 2, 2, 1, 1};
            String ancestryName[] = {"Mountain Dwarf", "Hill Dwarf", "Human", "High Elf", "Wood Elf", "Lightfoot Halfling", "Stout Halfling"};
            //mountain dwarf, hill dwarf, human, high elf, wood elf, lightfoot halfling, stout halfling
            //there aren't really any modifications to be made...


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
            isMedium = false;
        }
        else if (ancestry.equalsIgnoreCase("Stout Halfling"))
        {
            Halfling.applyAncestry("Stout Halfling", stat, proficiency, features);
            isMedium = false;
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

    public void decideSkills(int numberOfSkills) {//new skill list!
        String[][] skillList = {
                {"Athletics"},
                {},
                {},
                {"Nature"},
                {"Animal Handling", "Perception", "Survival"},
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
//proficiency assignments.
    private String[] assignArmorProf() {
        String armorList[] = {"Leather", "Studded Leather", "Padded", "Hide", "Breastplate", "Chain Shirt", "Half Plate", "Scale Mail", "Spiked Armor", "Shield"};

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



}

