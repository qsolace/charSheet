import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Bard {
    public Stats stat = new Stats();
    public Features features = new Features();

    private String[] statNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private int[] proficientSave = {1, 5};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus =2;
    private int hitDie = 8;
    private int bonusHP = 0;
    private int skillNumber = 3;
    private String ancestry;
    private Spells bardSpells = new Spells();
    private String background;
    private String instrument;

    private String equippedArmor = "";
    private int armorClass = 10;


    private Proficiency proficiency = new Proficiency();




    private Inventory inventory = new Inventory();

    Bard(Stats stat, String ancestry)
    {
        this.ancestry= ancestry;
        this.stat = stat;
    }

    Bard()
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


    public void fileOutput() throws IOException {

        //CHARACTER SHEET:
        Writer print = new StringWriter();
        print.write("Name: " + Character.seedString.stripTrailing() + "        " + ancestry + "        Bard " + level + "        Background: "+background+"\n\n");
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
        spellPrint.write("Name: " + Character.seedString + "      Class: Bard " + level);
        spellPrint.write("\nSpellcasting Ability Modifier: Charisma     " + "Spell Attack Bonus: +"+(2+ stat.getMods()[5]));
        spellPrint.write("\nSpell Save DC: "+(8+profBonus+stat.getMods()[5]));
        spellPrint.write("\n\nCANTRIPS:\n");
        spellPrint.write(bardSpells.getCantipsToString("\n"));
        spellPrint.write("\n\nFIRST LEVEL SPELLS (   |   ):\n");
        spellPrint.write(bardSpells.getFirstLevelToString("\n"));

        spellPrint.write("\n\n\n\n\nCANTRIPS\n" +
                "You know two cantrips of your choice from the bard spell list. You learn additional bard cantrips " +
                "of your choice at higher levels, as shown in the Cantrips Known column of the Bard table.\n" +
                "\n" +
                "SPELL SLOT\n" +
                "The Bard table shows how many spell slots you have to cast your bard spells of 1st level and " +
                "higher. To cast one of these spells, you must expend a slot of the spell’s level or higher. You " +
                "regain all expended spell slots when you finish a long rest.\n" +
                "For example, if you know the 1st-level spell cure wounds and have a 1st-level and a 2nd-level spell " +
                "slot available, you can cast cure wounds using either slot.\n" +
                "\n" +
                "SPELLS KNOWN OF 1ST LEVEL OR HIGHER\n" +
                "You know four 1st-level spells of your choice from the bard spell list.\n" +
                "\n" +
                "The Spells Known column of the Bard table shows when you learn more bard spells of your choice. Each " +
                "of these spells must be of a level for which you have spell slots, as shown on the table. For instance, " +
                "when you reach 3rd level in this class, you can learn one new spell of 1st or 2nd level.\n" +
                "Additionally, when you gain a level in this class, you can choose one of the bard spells you know and " +
                "replace it with another spell from the bard spell list, which also must be of a level for which you " +
                "have spell slots.\n" +
                "\n" +
                "SPELLCASTING ABILITY\n" +
                "Charisma is your spellcasting ability for your bard spells. Your magic comes from the heart and soul " +
                "you pour into the performance of your music or oration. You use your Charisma whenever a spell refers " +
                "to your spellcasting ability. In addition, you use your Charisma modifier when setting the saving " +
                "throw DC for a bard spell you cast and when making an attack roll with one.\n" +
                "Spell save DC = 8 + your proficiency bonus + your Charisma modifier\n" +
                "Spell attack modifier = your proficiency bonus + your Charisma modifier\n" +
                "\n" +
                "RITUAL CASTING\n" +
                "You can cast any bard spell you know as a ritual if that spell has the ritual tag.\n" +
                "\n" +
                "SPELLCASTING FOCUS\n" +
                "You can use a musical instrument (see the Tools section) as a spellcasting focus for your bard spells.");

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



        assignedStats[5] = stats[5];//Cha highest
        if (Character.rand.nextBoolean())
        {
            assignedStats[1] = stats[3];//Con as 2nd highest and Dex as 3rd highest
            assignedStats[2] = stats[4];
        }
        else{
            assignedStats[1] = stats[4];//Dex as 2nd highest and Con as 3rd highest
            assignedStats[2] = stats[3];
        }



        switch (Character.rand.nextInt(6)) {//the worst thing I've ever done. This will look better later
            case 0:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[0];
                assignedStats[0] = stats[1];
                break;
            case 1:
                assignedStats[3] = stats[2];
                assignedStats[4] = stats[1];
                assignedStats[0] = stats[0];
                break;
            case 2:
                assignedStats[3] = stats[1];
                assignedStats[4] = stats[2];
                assignedStats[0] = stats[0];
                break;
            case 3:
                assignedStats[3] = stats[1];
                assignedStats[4] = stats[0];
                assignedStats[0] = stats[2];
                break;
            case 4:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[1];
                assignedStats[0] = stats[2];
                break;
            case 5:
                assignedStats[3] = stats[0];
                assignedStats[4] = stats[2];
                assignedStats[0] = stats[1];
                break;
        }
        stat.setScores(assignedStats);
    }




    public void createFirstLevel() throws IOException {

        assignStats();
        ancestry = decideAncestry();
        stat.createModifiers();

        background = decideBackground();
        Backgrounds.addBackground(background, proficiency, inventory, features);


        decideSkills(skillNumber);
        System.out.println(proficiency.getSkillsToString());

        proficiency.addWeapon(assignWeaponProf());
        proficiency.addArmor(assignArmorProf());
        assignToolProf();

        hitPoints = hitDie + stat.modifier[2];


        equippedArmor = "Leather Armor";
        armorClass = 11 + stat.getMods()[1];


        if (stat.getMods()[0]<=1)
        {
            inventory.addWeapon("Rapier");
        }
        else
        {
            if (Character.rand.nextBoolean())
            {
                inventory.addWeapon("Rapier");
            }
            else
            {
                inventory.addWeapon("Longsword");
            }
        }

        if (Character.rand.nextBoolean()) {
            inventory.addDiplomatsPack();
        } else {
            inventory.addPriestsPack();
        }

        inventory.addWeapon("Dagger");
        inventory.addMisc(instrument);


        spellSelection(4);
        int biUses = stat.modifier[5];
        if (biUses<1){biUses=1;}

        features.addFeature("Bardic Inspiration", "You can inspire others through stirring words or " +
                "music. To do so, you use a bonus action on your turn to choose one creature other than yourself " +
                "within 60 feet of you who can hear you. That creature gains one Bardic Inspiration die, a d6.\n" +
                "\n" +
                "Once within the next 10 minutes, the creature can roll the die and add the number rolled to one " +
                "ability check, attack roll, or saving throw it makes. The creature can wait until after it rolls the " +
                "d20 before deciding to use the Bardic Inspiration die, but must decide before the DM says whether the " +
                "roll succeeds or fails. Once the Bardic Inspiration die is rolled, it is lost. A creature can have " +
                "only one Bardic Inspiration die at a time.\n" +
                "\n" +
                "You can use this feature a number of times equal to your Charisma modifier ("+biUses+"). You " +
                "regain any expended uses when you finish a long rest.", biUses);

        inventory.addArmor(equippedArmor);
        stat.addSaveProficiency(proficientSave);
        fileOutput();
    }


    private String decideBackground()
    {
        String[] backgroundOptions = {"Acolyte", "Criminal", "Folk Hero", "Noble", "Sage", "Soldier"};
        int[] backgroundChance = {3, 5, 10, 4, 4, 3};

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
            Elf.applyAncestry("High Elf", stat, proficiency, features, bardSpells);
        } else if (ancestry.equalsIgnoreCase("Wood Elf")) {
            Elf.applyAncestry("Wood Elf", stat, proficiency, features);
        }




        return ancestry;


    }

    public void decideSkills(int numberOfSkills) {
        String[][] skillList = {
                {"Athletics"},
                {"Stealth", "Sleight of Hand", "Acrobatics"},
                {},
                {"Arcana", "History", "Investigation", "Nature", "Religion"},
                {"Perception", "Animal Handling", "Survival", "Insight", "Medicine"},
                {"Persuasion", "Deception", "Intimidation", "Performance", "Persuasion", "Deception", "Intimidation", "Performance"}
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
        String armorList[] = {"Leather", "Studded Leather", "Padded"};

        proficiency.addArmor(armorList);
        return armorList;
    }

    private String[] assignWeaponProf() {
        String weaponList[] = {"Club", "Dagger", "Greatclub", "Handaxe", "Javelin", "Light Hammer", "Mace",
                "Quarterstaff", "Sickle", "Spear", "Light Crossbow", "Dart", "Shortbow", "Sling", "Hand Crossbow",
                "Longsword", "Rapier", "Shortsword"};
        proficiency.addWeapon(weaponList);
        return weaponList;
    }
    private String[] assignToolProf(){
        String toolList[] = {"Bagpipes",  "Drum", "Dulcimer", "Flute", "Lute", "Lyre",  "Horn","Pan flute", "Shawm",
                "Viol"};
        for (int i = 0; i<3; i++)
        {
            instrument = toolList[Character.rand.nextInt(toolList.length)];
            System.out.println(instrument);
            proficiency.addTool(instrument);
        }
        return toolList;
    }

    public String[][] spellSelection(int spellNumber)
    {
        String[] cantrips = new String[3];
        String[] firstLevelSpell = new String[spellNumber];
        int firstLevelRedo = 0;

        String[] damageCantrip = {
                "Thunderclap",
                "Vicious Mockery"
        };
        String[] allCantrips = {
                "Blade Ward", "Dancing Lights", "Friends", "Light", "Mage Hand", "Mending", "Message",
                "Minor Illusion", "Prestidigitation", "Thunderclap", "True Strike", "Vicious Mockery"
        };

        bardSpells.addCantrip(damageCantrip[Character.rand.nextInt(damageCantrip.length)]);
        do {
        }while (bardSpells.addCantrip(allCantrips[Character.rand.nextInt(allCantrips.length)]) > 0);

        String[] heal1 = {
                "Cure Wounds",
                "Healing Word",
                "Heroism"
        };

        String[] utility1 = {
                "Charm Person",
                "Disguise Self",
                "Feather Fall",
                "Longstrider",
                "Silent Image"
        };

        String[] debuff1 = {
                "Bane",
                "Faerie Fire",
                "Hideous Laughter",
                "Sleep",
                "Thunderwave"
        };

        String[] ritual1 = {
                "Animal Friendship",
                "Comprehend Languages",
                "Detect Magic",
                "Identify",
                "Illusiory Script",
                "Speak with Animals",
                "Unseen Servant"
        };

        String[] all1 = {
                "Animal Friendship",
                "Bane",
                "Charm Person",
                "Comprehend Languages",
                "Cure Wounds",
                "Detect Magic",
                "Disguise Self",
                "Faerie Fire",
                "Feather Fall",
                "Healing Word",
                "Heroism",
                "Hideous Laughter",
                "Identify",
                "Illusiory Script",
                "Longstrider",
                "Silent Image",
                "Sleep",
                "Speak with Animals",
                "Thunderwave",
                "Unseen Servant"
        };

        firstLevelRedo += bardSpells.addFirstLevel(ritual1[Character.rand.nextInt(ritual1.length)]);
        firstLevelRedo += bardSpells.addFirstLevel(debuff1[Character.rand.nextInt(debuff1.length)]);
        firstLevelRedo += bardSpells.addFirstLevel(utility1[Character.rand.nextInt(utility1.length)]);
        firstLevelRedo += bardSpells.addFirstLevel(heal1[Character.rand.nextInt(heal1.length)]);


        System.out.println("SPELL RETRY: "+firstLevelRedo);

        while (firstLevelRedo != 0){
            int retry = firstLevelRedo;
            firstLevelRedo=0;
            for (int j = 0; j < retry;j++){
                firstLevelRedo+= bardSpells.addFirstLevel(all1[Character.rand.nextInt(all1.length)]);
            }
        }

        String[][] returnArray = {
                bardSpells.getCantrips(), bardSpells.getFirstLevel()
        };
        return returnArray;

    }
}
