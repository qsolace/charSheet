//The Fighter is one of the three "class" classes. It has unique features such as primary stat determination, weapon
//selection, and Fighting Style determination.

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//this is one of 3 classes included in thsi build.
public class Paladin extends JFrame{

    Paladin (Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }//this is the constructor for this clas. It requires the stat list and the ancestry. If an ancestry is blank, it'll generate one.


    public Stats stat;
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};//various declarations
    private int[] proficientSave = {4, 5};
    private int[] saveBonus = {0, 0, 0, 0, 0, 0};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus = 2;
    private int hitDie = 10;
    private int bonusHP = 0;
    private int skillNumber = 2;
    private String ancestry;
    private int fightingStyle;
    private String background;

    private String equippedArmor = "";
    private int armorClass = 10;
    private boolean shieldEquip = false;


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


    public String getSkills() {//various get statements
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


    public void fileOutput() throws IOException {//this whole thing prints the class to a .txt file
        Writer print = new StringWriter();//this puts everything into a String.
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + " Paladin " + level + "        Background: "+background+"\n\n");//adds name, ancestry, class, and level
        print.write("AC: " + armorClass + "         HP: " + (hitPoints +bonusHP)+ "       Speed: " + features.getSpeed() + "ft\n\n");//adds Armor Class, hitpoints, and speed
        for (int i = 0; i < stat.modifier.length; i++) {//adds names of Ability Score names, statistics, and modifiers
            print.write(statNames[i] + ": " + stat.scores[i] + "(");
            if (stat.modifier[i] >= 0) {
                print.write("+");
            }
            print.write(stat.modifier[i] + ")" + "      ");
            if (i == 2) {
                print.write("\n");
            }
        }

        print.write("\n-------------------\nSaving Throws:\n\n");//adds proficient saves
        for (int i = 0; i < saveBonus.length; i++) {
            if (i == proficientSave[0] || i == proficientSave[1]) {
                print.write(statNames[i] + " Saving Throw: ");
                if (stat.saves[i] >= 0) {
                    print.write("+");
                }
                print.write(stat.saves[i] + "\n");
            }
        }

        print.write("-------------------\nSkills:\n\n");//adds proficient skills
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

        print.write("-------------------\nWeapons:\n\n");//shows all the weapons with their damage and traits
        for (int i = 0; i < inventory.getWeapons().length; i++) {//does this for every weapon in inventory
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
                        damageBonus += 2;//apply Dueling FS
                    }
                    outputString += (damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));//damage type
                }
                else
                {
                    bonusSign = '-';
                }
                print.write(outputString);

                if (!Weapons.versatile(inventory.getWeapons()[i]).equalsIgnoreCase("Not Versatile")) {//adds versatile damage.

                    print.write("\n" + inventory.getWeapons()[i] + "(two-handed) " + attackSign + attackBonus + " " + Weapons.versatile(inventory.getWeapons()[i]) +
                            bonusSign + damageBonus + " " + Weapons.damageType(inventory.getWeapons()[i]));
                }

                outputString = Weapons.thrown(inventory.getWeapons()[i]) + Weapons.ranged(inventory.getWeapons()[i]);
                if (!outputString.isEmpty()) {//adds traits to the weapon listing
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
        print.write(features.getFeatures());//prints all the features from the "Features" class

        print.write("\n\n\nEquipped Armor:\n" + equippedArmor);
        if (shieldEquip) {
            print.write(", Shield\n");
        }

        print.write("\n\n\n\nINVENTORY:\n");//shows everything in the inventory
        print.write("   Weapons:\n      " + inventory.getWeaponsToString());
        print.write("\n   Armor:\n      " + inventory.getArmorToString());
        print.write("\n   Tools:\n      " + inventory.getToolsToString());
        print.write("\n   Other:\n      " + inventory.getMiscToString());
        print.write("\n\n   "+inventory.getMoneyToString("\n   "));


        print.write("\n\n\n\n\n" +
                "Weapon Proficiencies:\n");//shows all the weapon proficiencies
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
                "Armor Proficiencies:\n");//shows all the armor proficiencies
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
                "Tool Proficiencies:\n");//all the tool proficiencies
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
        //displays the character and prompts the user to say ok

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(300, 300));
        JFileChooser jFileChooser = new JFileChooser("C:");
        jFileChooser.setDialogTitle("Where do you want to save " + Character.seedString.stripTrailing() + "?");
        jFileChooser.showSaveDialog(frame);//file save dialog for where to save the character file

        String path = "";
        try {
            path = jFileChooser.getSelectedFile().getAbsolutePath();
        }
        catch (NullPointerException e)
        {
            System.exit(0);
        }//makes sure the file is okay

        File outputFile = new File (path+".txt");//add that file
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



        assignedStats[0] = stats[5];

        if (Character.rand.nextBoolean()) {
            assignedStats[2] = stats[4];//Assign Con as second highest stat.
            assignedStats[5] = stats[3];//Assign Cha as third highest
        }
        else
        {
            assignedStats[5] = stats[4];//Assign Cha as second highest stat.
            assignedStats[2] = stats[3];//Assign Con as third highest
        }



        switch (Character.rand.nextInt(6)) {//the worst thing I've ever done. This will look better later
            case 0:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[0];
                assignedStats[1] = stats[1];
                break;
            case 1:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[1];
                assignedStats[1] = stats[0];
                break;
            case 2:
                assignedStats[3] = stats[1];
                assignedStats[4] = stats[2];
                assignedStats[1] = stats[0];
                break;
            case 3:
                assignedStats[3] = stats[1];
                assignedStats[4] = stats[0];
                assignedStats[1] = stats[2];
                break;
            case 4:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[1];
                assignedStats[1] = stats[2];
                break;
            case 5:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[2];
                assignedStats[1] = stats[1];
                break;


        }

        stat.setScores(assignedStats);
    }


    public void createFirstLevel() throws IOException {//this is what runs when the class is selected.

        assignStats();//this assigns the stats in an optimal manner, specific to the class.
        ancestry = decideAncestry(ancestry);//if an ancestry isn't given, this decides it.
        stat.createModifiers();//since ancestries change statistics, the modifiers need to be updated

        background = decideBackground();
        Backgrounds.addBackground(background, proficiency, inventory, features);

        decideSkills(skillNumber);//this determines which skill the fighter will have
        proficiency.addWeapon(assignWeaponProf());//this adds both the weapon and armor proficiencies to the "Proficiency" class
        proficiency.addArmor(assignArmorProf());


        hitPoints = hitDie + stat.modifier[2];//This determines the hitpoints: it's the max of the hit die, plus the Constitution modifier (the 3rd score)

        equippedArmor = "Chain Mail";
        armorClass = 16;




        boolean shieldMartial = Character.rand.nextBoolean();//this randomly determines whether the character uses a shield or not.
        int weaponAmount = 1;
        String[] weaponOptions;


        if (shieldMartial) {
            weaponOptions = Weapons.oneHandedWeapon(Weapons.martialStr());
            inventory.addArmor("Shield");
            armorClass+=2;
            shieldEquip = true;
        } else {
            weaponOptions = Weapons.martialStr();
            weaponAmount = 2;
        }



        String weaponChoice = "";
        for (int i = 0; i < weaponAmount; i++) {//this chooses those weapons and adds them to the Inventory class
            weaponChoice = weaponOptions[Character.rand.nextInt(weaponOptions.length)];
            inventory.addWeapon(weaponChoice);
        }

        if (Character.rand.nextBoolean())
        {
            inventory.addWeapon("Javelin", "Javelin", "Javelin", "Javelin", "Javelin");
        }
        else
        {
            String[] simpleWeaponList = Weapons.simpleMeleeList();
            inventory.addWeapon(simpleWeaponList[Character.rand.nextInt(simpleWeaponList.length)]);
        }

        if (Character.rand.nextBoolean())//this randomly chooses what miscellaneous items are chosen.
        {
            inventory.addPriestsPack();
        }
        else
        {
            inventory.addExplorersPack();
        }

        inventory.addMisc("Holy Symbol");

        features.addFeature("Divine Sense", "The presence of strong evil registers on your senses like a " +
                "noxious odor, and powerful good rings like heavenly music in your ears. As an action, you can open your" +
                " awareness to detect such forces. Until the end of your next turn, you know the location of any " +
                "celestial, fiend, or undead within 60 feet of you that is not behind total cover. You know the type " +
                "(celestial, fiend, or undead) of any being whose presence you sense, but not its identity (the vampire " +
                "Count Strahd von Zarovich, for instance). Within the same radius, you also detect the presence of any " +
                "place or object that has been consecrated or desecrated, as with the hallow spell.\n" +
                "\n" +
                "You can use this feature a number of times equal to 1 + your Charisma modifier. When you finish a " +
                "long rest, you regain all expended uses.", Math.max(1+stat.getMods()[5], 1));//this adds a feature to the Features class

        features.addFeature("Lay on Hands", "Your blessed touch can heal wounds. You have a pool of " +
                "healing power that replenishes when you take a long rest. With that pool, you can restore a total " +
                "number of hit points equal to your paladin level × 5.\n" +
                "\n" +
                "As an action, you can touch a creature and draw power from the pool to restore a number of " +
                "hit points to that creature, up to the maximum amount remaining in your pool.\n" +
                "\n" +
                "Alternatively, you can expend 5 hit points from your pool of healing to cure the target of one disease " +
                "or neutralize one poison affecting it. You can cure multiple diseases and neutralize multiple poisons " +
                "with a single use of Lay on Hands, expending hit points separately for each one.\n" +
                "\n" +
                "This feature has no effect on undead and constructs.", 5, 1);

        inventory.addArmor(equippedArmor);//this adds the armor to the inventory
        stat.addSaveProficiency(proficientSave);//and this adds the save proficiencies (which are declared at the very top of the program)
        fileOutput();//finally, this prints all of this information as a .txt file
    }

    private String decideBackground()
    {
        String[] backgroundOptions = {"Acolyte", "Criminal", "Folk Hero", "Noble", "Sage", "Soldier"};
        int[] backgroundChance = {7, 2, 5, 5, 4, 10};

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
    private String decideAncestry(String userAncestry) {//this decides the ancestry
        String ancestry = userAncestry;
        if (ancestry.isEmpty()) {//if the user inputs an ancestry, this is skipped
            int[] stats = stat.scores;
            int ancestryChance[] = {2, 2, 1, 1, 1, 1, 1};
            String ancestryName[] = {"Mountain Dwarf", "Hill Dwarf", "Human", "High Elf", "Wood Elf", "Lightfoot Halfling", "Stout Halfling"};



            int totalWeight = 0;
            for (int i = 0; i < ancestryChance.length; i++) {//this determines the total weight of all of the ancestries
                totalWeight += ancestryChance[i];
            }

            int decidedNumber = Character.rand.nextInt(totalWeight) + 1;//this randomizes it

            int ancestryNumber = -1;
            while (decidedNumber > 0) {
                ancestryNumber++;
                decidedNumber -= ancestryChance[ancestryNumber];//and this selects it
            }

            ancestry = ancestryName[ancestryNumber];
        }

        if (ancestry.compareToIgnoreCase("human") == 0) {//then, this area applies that ancestry's traits, by going off into different classes
            Human.applyAncestry(stat, proficiency, features);
        }
        else if (ancestry.equalsIgnoreCase("Hill Dwarf"))
        {
            bonusHP++;//since the Hill Dwarf gets extra hitpoints per level, this reflects that
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

    public void decideSkills(int numberOfSkills) {//this determines what skills are assigned
        String[][] skillList = {//all skills are tied to a certain ability score. This multidimensional array pairs each skill with its respective score
                {"Athletics"},
                {},
                {},
                {"Religion"},
                {"Medicine", "Insight"},//ie: all of these are Wisdom skills, so they are grouped together
                {"Intimidation", "Persuasion"}//whereas this is a Charisma skill, so it is separate.
        };

        for (int j = 0; j < numberOfSkills; j++) {//this randomizes the skill
            int skillNumber = Character.rand.nextInt(Proficiency.countMultArray(skillList));

            for (int i = 0; i < skillList.length; i++) {
                if (skillList[i].length > skillNumber) {
                    int rerun = proficiency.addSkill(i, skillList[i][skillNumber]);
                    if (rerun > 0) {
                        decideSkills(rerun);//if the same skill is selected, this recalls this function, until a unique skill is selected
                    }
                    break;
                } else {
                    skillNumber -= skillList[i].length;
                }
            }
        }
    }

    private String[] assignArmorProf() {//this list of armors is added to the proficiency class
        String armorList[] = {"Leather", "Studded Leather", "Padded", "Hide", "Breastplate", "Chain Shirt", "Half Plate", "Ring Mail", "Scale Mail", "Spiked Armor", "Chain Mail", "Splint", "Plate", "Shield"};

        proficiency.addArmor(armorList);
        return armorList;
    }

    private String[] assignWeaponProf() {//same for the weapons here
        String weaponList[] = {"Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear", "Light Crossbow", "Dart", "Shortbow", "Sling", "Battleaxe", "Flail", "Glaive",
                "Greataxe", "Greatsword", "Halberd", "Lance", "Longsword", "Maul", "Morningstar", "Pike", "Rapier", "Scimitar", "Shortsword", "Trident", "War Pick", "Warhammer", "Whip", "Blowgun", "Hand Crossbow", "Heavy Crossbow",
                "Longbow", "Net"};
        proficiency.addWeapon(weaponList);
        return weaponList;
    }


//----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Ability Fun Times<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    private int fightingStyleDetermination (String weapon)//this determines the Fighting Styles, based on what type of weapon is inputted.
    {
        int[] FSChance = {2, 0, 0, 0, 0, 0};//this array holds a list of the fighting styles, which have been given a number. Since defense (Fighting Style number 2)
        //will always be applicable, it is automatically added
        int FSIndex = 1;

        if (Weapons.isMelee(weapon))//all of this is based on one weapon, as that ensures that it will be applicable to at least one weapon, rather than random.
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

        fightingStyle = FSChance[Character.rand.nextInt(FSIndex)];//this randomizes which Fighting Style is selected, using only the ones that have been added
        if (fightingStyle == 2)
        {
            armorClass+=1;
        }

        switch (fightingStyle){//this adds the description to the "Features" class
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

        return fightingStyle;//this returns the number of the Fighting Style

    }

}

