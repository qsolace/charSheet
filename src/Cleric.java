import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Cleric {
    public Stats stat = new Stats();
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private int[] proficientSave = {4, 5};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus =2;
    private int hitDie = 8;
    private int bonusHP = 0;
    private int skillNumber = 2;
    private String ancestry;
    private String domain;
    private Spells clericSpells = new Spells();
    private String[] domainSpells = new String[10];
    private String background;

    private String equippedArmor = "";
    private int armorClass = 10;


    private Proficiency proficiency = new Proficiency();




    private Inventory inventory = new Inventory();

    Cleric(Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }

    Cleric()
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
        return proficiency.getLanguageToString(" ");
    }


    public void fileOutput() throws IOException{

        //CHARACTER SHEET:
        Writer print = new StringWriter();
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + " " + domain+" Cleric " + level + "        Background: "+background+"\n\n");
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
                    print.write(bonus+"");

                    if (proficiency.getSkills()[i][j].equalsIgnoreCase("Stealth")&&equippedArmor.matches("Ring Mail|Chain Mail|Splint|Plate"))
                    {
                        print.write(" (disadvantage)");
                    }
                    print.write("\n");
                }
            }
        }

        if (equippedArmor.matches("Ring Mail|Chain Mail|Splint|Plate"))
        {
            int bonus = stat.modifier[1];
            if (!proficiency.isSkill("Stealth")) {
                print.write("Stealth: ");
                if (bonus >= 0) {
                    print.write("+");
                }
                print.write(bonus + " (disadvantage)\n");
            }

        }


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
        print.write("\n\n   "+inventory.getMoneyToString("\n   "));



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
        print.write("\n\n-------------------\n" +
                "Languages:\n"+proficiency.getLanguageToString(", "));

        print.close();

        //SPELL SHEET
        Writer spellPrint = new StringWriter();
        spellPrint.write("Name: " + Character.seedString + "      Class: Cleric " + level);
        spellPrint.write("\nSpellcasting Ability Modifier: Wisdom     " + "Spell Attack Bonus: +"+(2+ stat.getMods()[4]));
        spellPrint.write("\nSpell Save DC: "+(8+profBonus+stat.getMods()[4]));
        spellPrint.write("\n\nCANTRIPS:\n");
        spellPrint.write(clericSpells.getCantipsToString("\n"));
        spellPrint.write("\n\nFIRST LEVEL SPELLS (   |   ):\n");
        spellPrint.write(clericSpells.getFirstLevelToString("\n"));

        spellPrint.write("\n\n\n\n\nCANTRIPS\n" +
                "At 1st level, you know three cantrips of your choice from the cleric spell list. You learn additional cleric cantrips of your choice at higher levels, as shown in the Cantrips Known column of the Cleric table.\n" +
                "\n" +
                "PREPARING AND CASTING SPELLS\n" +
                "The Cleric table shows how many spell slots you have to cast your cleric spells of 1st level and higher. To cast one of these spells, you must expend a slot of the spell’s level or higher. You regain all expended spell slots when you finish a long rest.\n" +
                "You prepare the list of cleric spells that are available for you to cast, choosing from the cleric spell list. When you do so, choose a number of cleric spells equal to your Wisdom modifier + your cleric level (minimum of one spell). The spells must be of a level for which you have spell slots.\n" +
                "For example, if you are a 3rd-level cleric, you have four 1st-level and two 2nd-level spell slots. With a Wisdom of 16, your list of prepared spells can include six spells of 1st or 2nd level, in any combination. If you prepare the 1st-level spell cure wounds, you can cast it using a 1st-level or 2nd-level slot. Casting the spell doesn’t remove it from your list of prepared spells.\n" +
                "You can change your list of prepared spells when you finish a long rest. Preparing a new list of cleric spells requires time spent in prayer and meditation: at least 1 minute per spell level for each spell on your list.\n" +
                "\n" +
                "SPELLCASTING ABILITY\n" +
                "Wisdom is your spellcasting ability for your cleric spells. The power of your spells comes from your devotion to your deity. You use your Wisdom whenever a cleric spell refers to your spellcasting ability. In addition, you use your Wisdom modifier when setting the saving throw DC for a cleric spell you cast and when making an attack roll with one.\n" +
                "Spell save DC = 8 + your proficiency bonus + your Wisdom modifier\n" +
                "Spell attack modifier = your proficiency bonus + your Wisdom modifier\n" +
                "\n" +
                "RITUAL CASTING\n" +
                "You can cast a cleric spell as a ritual if that spell has the ritual tag and you have the spell prepared.\n" +
                "\n" +
                "SPELLCASTING FOCUS\n" +
                "You can use a holy symbol (see the Adventuring Gear section) as a spellcasting focus for your cleric spells.\n" +
                "\n" +
                "DOMAIN SPELLS\n" +
                "Each domain has a list of spells — its domain spells — that you gain at the cleric levels noted in the domain description. Once you gain a domain spell, you always have it prepared, and it doesn’t count against the number of spells you can prepare each day.\n" +
                "If you have a domain spell that doesn’t appear on the cleric spell list, the spell is nonetheless a cleric spell for you.\nYour domain spells are:\n"+domainSpells[0]+" and "+domainSpells[1]);

        spellPrint.close();

        JTextArea label = new JTextArea(print.toString() + "\n\n\n\n----------------------------------\n\n\n\n" + spellPrint.toString());
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
        catch (NullPointerException e)
        {
            System.exit(0);
        }

        File outputFile = new File (path+".txt");
        FileWriter outputWrite = new FileWriter(outputFile);
        outputWrite.write(print.toString());
        outputWrite.close();

        File spellOutputFile = new File (path+"'s Spells.txt");
        FileWriter spellOutputWrite = new FileWriter(spellOutputFile);
        spellOutputWrite.write(spellPrint.toString());
        spellOutputWrite.close();
        System.exit(0);
    }

    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MANIPULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    private void assignStats() {
        stat.sortScores();
        int[] stats = stat.scores;
        int[] assignedStats = new int[6];



        assignedStats[4] = stats[5];//Wis highest
        if (Character.rand.nextBoolean())
        {
            assignedStats[0] = stats[3];//Str as 3rd highest and Dex as 2nd lowest
            assignedStats[1] = stats[1];
        }
        else{
            assignedStats[1] = stats[3];//dex as 3rd highest and Str as 2nd lowest
            assignedStats[0] = stats[1];
        }



        assignedStats[2] = stats[4];//Assign Con as second highest stat.

        if (Character.rand.nextBoolean())
        {
            assignedStats[3]=stats[2];//Int as 4th highest and cha as lowest
            assignedStats[5] = stats[0];
        }
        else
        {
            assignedStats[3]=stats[2];//Cha as 4th highest and Int as lowest
            assignedStats[5] = stats[0];
        }
        stat.setScores(assignedStats);
        }




    public void createFirstLevel() throws IOException {

        stat.generate();
        assignStats();
        ancestry = decideAncestry();
        stat.createModifiers();

        background = decideBackground();
        Backgrounds.addBackground(background, proficiency, inventory, features);

        String[] domainList = {"Life"};
        domain = domainList[Character.rand.nextInt(domainList.length)];
        switch (domain){
            case "Life":
                lifeCleric();
                break;
        }

        decideSkills(skillNumber);
        System.out.println(proficiency.getSkillsToString());

        proficiency.addWeapon(assignWeaponProf());
        proficiency.addArmor(assignArmorProf());

        hitPoints = hitDie + stat.modifier[2];

        if (proficiency.isProficientArmor("Chain Mail") && stat.getScores()[0] >= 13)
        {
            equippedArmor = "Chain Mail";
            armorClass = 16;
        }
        else if (stat.getScores()[1]<14)
        {
            equippedArmor = "Scale Mail";
            armorClass = 14 + stat.getMods()[1];
        }
        else
        {

            if(Character.rand.nextBoolean()) {
                equippedArmor = "Leather Armor";
                armorClass = 11 + stat.getMods()[1];
            }
            else {
                equippedArmor = "Scale Mail";
                armorClass = 14 + stat.getMods()[1];
            }


        }

        if (proficiency.isProficientWeapon("Warhammer"))
        {
            inventory.addWeapon("Warhammer");
        }
        else
        {
            inventory.addWeapon("Mace");
        }

        if (stat.getScores()[0]<14)
        {
            inventory.addWeapon("Light Crossbow");
            inventory.addMisc("20 bolts");
        }
        else {
            if (Character.rand.nextBoolean()) {
                inventory.addWeapon("Handaxe");
            }
            else {
                inventory.addWeapon("Spear");
            }
        }

        if (Character.rand.nextBoolean()) {
            inventory.addExplorersPack();
        } else {
            inventory.addPriestsPack();
        }

        inventory.addMisc("Holy Symbol");
        inventory.addArmor("Shield");
        armorClass += 2;

        spellSelection(1+stat.getMods()[4]);

        inventory.addArmor(equippedArmor);
        stat.addSaveProficiency(proficientSave);
        fileOutput();
    }
    private void lifeCleric()
    {
        proficiency.addArmor("Ring Mail", "Chain Mail", "Splint", "Plate");
        clericSpells.addFirstLevel("Bless", "Cure Wounds");
        domainSpells[0] = "Bless";
        domainSpells[1] = "Cure Wounds";
        features.addFeature("Disciple of Life", "Your healing spells are more effective. Whenever you use a spell of 1st level or " +
                "higher to restore hit points to a creature, the creature regains additional hit points equal to 2 + the spell’s level.");
    }

    private String decideBackground()
    {
        String[] backgroundOptions = {"Acolyte", "Criminal", "Folk Hero", "Noble", "Sage", "Soldier"};
        int[] backgroundChance = {10, 1, 4, 4, 6, 2};

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
                Elf.applyAncestry("High Elf", stat, proficiency, features, clericSpells);
            } else if (ancestry.equalsIgnoreCase("Wood Elf")) {
                Elf.applyAncestry("Wood Elf", stat, proficiency, features);
            }




        return ancestry;


    }

    public void decideSkills(int numberOfSkills) {
        String[][] skillList = {
                {},
                {},
                {},
                {"History", "Religion"},
                {"Insight", "Medicine"},
                {"Persuasion"}
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
        String armorList[] = {"Leather", "Studded Leather", "Padded", "Hide", "Breastplate", "Chain Shirt", "Half Plate", "Scale Mail", "Spiked Armor", "Shield"};

        proficiency.addArmor(armorList);
        return armorList;
    }

    private String[] assignWeaponProf() {
        String weaponList[] = {"Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace", "Quarterstaff", "Sickle", "Spear", "Light Crossbow", "Dart", "Shortbow", "Sling"};
        proficiency.addWeapon(weaponList);
        return weaponList;
    }
    private String[] assignToolProf(){
        String toolList[] = {};
        proficiency.addWeapon(toolList);
        return toolList;
    }

    public String[][] spellSelection(int spellNumber)
    {
        String[] cantrips = new String[3];
        String[] firstLevelSpell = new String[spellNumber];
        int firstLevelRedo = 0;

        String[] damageCantrip = {
                "Sacred Flame",
                "Toll the Dead"
        };
        String[] utilityCantrip = {
                "Guidance",
                "Light",
                "Thaumaturgy"
        };
        String[] allCantrips = {
                "Guidance",
                "Light",
                "Mending",
                "Resistance",
                "Sacred Flame",
                "Spare the Dying",
                "Thaumaturgy",
                "Toll the Dead"
        };

        clericSpells.addCantrip(damageCantrip[Character.rand.nextInt(damageCantrip.length)]);
        clericSpells.addCantrip(utilityCantrip[Character.rand.nextInt(utilityCantrip.length)]);
        do {
        }while (clericSpells.addCantrip(allCantrips[Character.rand.nextInt(allCantrips.length)]) > 0);

        String[] heal1 = {
                "Cure Wounds",
                "Healing Word"
        };

        String[] buff1 = {
                "Bless",
                "Sanctuary",
                "Shield of Faith",
                "Protection from Evil and Good"
        };

        String[] debuff1 = {
                "Bane",
                "Command"
        };

        String[] damage1 = {
                "Inflict Wounds",
                "Guiding Bolt"
        };

        String[] all1 = {
                "Inflict Wounds",
                "Guiding Bolt",
                "Bane",
                "Command",
                "Bless",
                "Sanctuary",
                "Shield of Faith",
                "Protection from Evil and Good",
                "Cure Wounds",
                "Healing Word",
                "Create or Destroy Water",
                "Detect Evil and Good",
                "Detect Magic",
                "Detect Poison and Disease",
                "Purify Food and Drink"
        };
        switch (spellNumber)
        {
            case 6:
            case 5:
            case 4:
                firstLevelRedo += clericSpells.addFirstLevel(damage1[Character.rand.nextInt(damage1.length)]);
            case 3:
                firstLevelRedo += clericSpells.addFirstLevel(debuff1[Character.rand.nextInt(debuff1.length)]);
            case 2:
                firstLevelRedo += clericSpells.addFirstLevel(buff1[Character.rand.nextInt(buff1.length)]);
            case 1:
                firstLevelRedo += clericSpells.addFirstLevel(heal1[Character.rand.nextInt(heal1.length)]);

        }

        System.out.println("SPELL RETRY: "+firstLevelRedo);
        for (int i = 4; i < spellNumber; i++)
        {
            firstLevelRedo+= clericSpells.addFirstLevel(all1[Character.rand.nextInt(all1.length)]);
        }
        System.out.println("SPELL RETRY 2: "+firstLevelRedo);
        while (firstLevelRedo != 0){
            System.out.println("RETRYING. FLR = "+firstLevelRedo);
            int retry = firstLevelRedo;
            firstLevelRedo=0;
            for (int j = 0; j < retry;j++){
                firstLevelRedo+= clericSpells.addFirstLevel(all1[Character.rand.nextInt(all1.length)]);
            }
        }

        String[][] returnArray = {
                clericSpells.getCantrips(), clericSpells.getFirstLevel()
        };
        return returnArray;

    }


    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Ability Fun Times<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------

}
