import java.util.Random;

public class Fighter {
    private int[] stats = {0, 0, 0, 0, 0, 0};
    public int[] assignedStats = {0, 0, 0, 0, 0, 0};//{Str, Dex, Con, Int, Wis, Cha}
    private int[] statModifiers = {0, 0, 0, 0, 0, 0};
    private boolean[] proficientSave = {true, false, true, false, false, false};
    private int[] saveBonus = {0, 0, 0, 0, 0, 0};
    private int hitPoints = 0;
    private int level = 1;
    private int profBonus = 2;
    private int hitDie = 10;
    private int skillNumber = 2;

    private String equippedArmor = "";
    private int armorClass = 10;

    private String languages[] = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int languageIndex = 0;

    private String skills[] = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int skillIndex = 0;

    private String tools[] = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int toolIndex = 0;

    private String weapons[] = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int weaponIndex = 0;

    private String armor[] = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int armorIndex = 0;

    private int speed;
    private int darkvisionRange;

    private boolean strFighter;
    private boolean isEKnight = false;

//----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INPUT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    public void addStatNumbers (int[] inputStats)
    {
         for (int i = 0; i < stats.length; ++i)
         {
             stats[i] = inputStats[i];//copies stats from input to class list
         }

         int length = stats.length;//sorts the array into order
         for (int i =0; i < length; i++)
         {
             int currentStat = stats[i];
             int previous = i -1;

             while (previous >= 0 && stats[previous]>=currentStat)
             {
                 stats[previous+1]=stats[previous];
                 previous--;
             }
             stats[previous+1] = currentStat;
         }
         assignStats();
     }

    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
    }

    public void addLanguages(String[] languages) {

        System.out.println(languageIndex);
        for (int i = 0; i < languages.length; i++)
        {
            System.out.println(languages.length);
            this.languages[languageIndex] = languages[i];
            languageIndex ++;
        }
    }
    public void addSkills(String[] skills) {

        System.out.println(skillIndex);
        for (int i = 0; i < languages.length; i++)
        {
            System.out.println(skills.length);
            this.skills[skillIndex] = skills[i];
            skillIndex ++;
        }
    }

    public void addTools(String[] tools) {

        System.out.println(toolIndex);
        for (int i = 0; i < tools.length; i++)
        {
            System.out.println(tools.length);
            this.tools[toolIndex] = tools[i];
            toolIndex ++;
        }
    }

    public void addWeapons(String[] weapons) {

        System.out.println(weaponIndex);
        for (int i = 0; i < weapons.length; i++)
        {
            System.out.println(weapons.length);
            this.weapons[weaponIndex] = weapons[i];
            weaponIndex ++;
        }
    }




    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDarkvisionRange(int darkvisionRange) {
        this.darkvisionRange = darkvisionRange;
    }
    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUTPUT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------

    public String orderedReturnStats ()//return plain stat list
    {
         String statReturn = "";

         for (int i = 0; i < stats.length; i++)
         {
             statReturn += stats[i] + " ";
         }
         return statReturn;
     }

    public int getHitDie() {
        return hitDie;
    }

    public String assignedReturnStats ()//returns assigned stat list
    {
        String statReturn = "";

        for (int i = 0; i < assignedStats.length; i++)
        {
            statReturn += assignedStats[i] + " ";
        }
        return statReturn;
    }

    public String modifierReturn ()
    {
        String statReturn = "";

        for (int i = 0; i < statModifiers.length; i++)
        {
            statReturn += statModifiers[i] + " ";
        }
        return statReturn;
    }

    public String saveModReturn ()
    {
        String statReturn = "";

        for (int i = 0; i < saveBonus.length; i++)
        {
            statReturn += saveBonus[i] + " ";
        }
        return statReturn;
    }

    public int getHitPoints ()
    {
        return hitPoints;
    }
    public int getArmorClass()
    {
        return armorClass;
    }

    public String[] getLanguages() {
        return languages;
    }

    //----------------------------------------------------------------------------------------------------------------------
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MANIPULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//----------------------------------------------------------------------------------------------------------------------
    private void assignStats ()
    {
         Random random = new Random();

         strFighter = random.nextBoolean();//randomly determines if Str should be highest.

         if (strFighter)//if Str should be highest, does that and makes dex 3rd lowest
         {
             assignedStats[0] = stats[5];
             assignedStats[1] = stats[2];
         }
         else//otherwise, flip them
         {
             assignedStats[1] = stats[5];
             assignedStats[0] = stats[2];
         }

         assignedStats[2] = stats[4];//Assign Con as second highest stat.

         if (isEKnight)//if EK, Int is highest mental, then W/C randomly
         {
             assignedStats[3] = stats[3];

             int randomNum = random.nextInt(2);

             assignedStats[4] = stats[randomNum];
             assignedStats[5] = stats[Math.abs(randomNum-1)];
         }
         else
         {//assigning mental stats. Random which is which.

             switch (random.nextInt(6)){//the worst thing I've ever done. This will look better later
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
     }

     public void createModifiers ()
     {
         for (int i = 0; i < statModifiers.length; i ++)
         {
             statModifiers[i] = (assignedStats[i]-10)/2;
         }
     }
    public void createFirstLevel ()
    {
        decideAncestry();
        createModifiers();
        createSavingThrow();
        System.out.println(isEKnight);


        hitPoints = hitDie + statModifiers[2];
        if (strFighter)
        {
            equippedArmor = "chain mail";
            armorClass = 16;
        }
        else
        {
            equippedArmor = "leather armor";
            armorClass = 11 + statModifiers[1];
        }
    }
    private void createSavingThrow()
    {
        for (int i = 0; i < saveBonus.length; i ++)
        {
            saveBonus[i] = statModifiers[i];
            if (proficientSave[i])
                saveBonus[i] += profBonus;
        }
    }

    private String decideAncestry()
    {
        int ancestryChance[] = {2, 2, 1, 1, 1, 1, 1};
        String ancestryName[] = {"mountain dwarf", "hill dwarf", "human", "high elf", "wood elf", "lightfoot halfling", "stout halfling"};
        //mountain dwarf, hill dwarf, human, high elf, wood elf, lightfoot halfling, stout halfling
        if (strFighter)
        {
            ancestryChance[0] += 4;
            ancestryChance[1] += 2;

            if (stats[0]%2 == 1)
            {
                ancestryChance[2]+=4;
            }
            if (stats[2]%2 == 1)
            {
                ancestryChance[2]+= 2;
            }
            if(isEKnight && stats[3]%2 == 1)
            {
                ancestryChance[2]+=2;
                ancestryChance[3]+=2;
            }
        }
        else
        {
            ancestryChance[3]+=2;
            ancestryChance[4]+=2;
            ancestryChance[5]+=2;

            if (stats[2]%2==1)
            {
                ancestryChance[6]+=4;
            }
            else
            {
                ancestryChance[6]+=2;
            }

            if (stats[1]%2 == 1)
            {
                ancestryChance[2]+=4;
            }
            if (stats[2]%2 == 1)
            {
                ancestryChance[2]+= 2;
            }
            if(isEKnight && stats[3]%2 == 1)
            {
                ancestryChance[2]+=2;
                ancestryChance[3]+=4;
            }
        }
        Random rand = new Random();


        int totalWeight = 0;
        for (int i = 0; i < ancestryChance.length; i ++)
        {
            totalWeight+=ancestryChance[i];
        }

        int decidedNumber = rand.nextInt(totalWeight)+1;

        int ancestryNumber = -1;
        while (decidedNumber > 0)
        {
            ancestryNumber++;
            decidedNumber -= ancestryChance[ancestryNumber];
        }

        String ancestry = ancestryName[ancestryNumber];
        ancestry = "human";
        System.out.println(ancestry);
        if (ancestry.compareToIgnoreCase("human")==0)
        {
            Human human = new Human();
            human.applyAncestry(this);
        }


        return ancestryName[ancestryNumber];


    }

    public String[] decideSkills(int numberOfSkills)
    {
        Random rand = new Random();
        String [] skillSelection = new String [numberOfSkills];
        String skillList[] = {"Acrobatics", "Animal Handling", "Athletics", "History", "Insight", "Intimidation", "Perception", "Survival"};
        for (int i = 0; i < numberOfSkills; i++)
        {
            if (skillList.length == 0)
            {
                skillSelection[i] = "";
                return skillSelection;
            }
            int skillLocation = rand.nextInt(skillList.length);
            skillSelection[i] = skillList[skillLocation];
            skillList = removeItem(skillList, skillLocation);
            for (int j = 0; j < skills.length; j++)
            {
                if (skills[j].equalsIgnoreCase(skillSelection[i]))
                {

                    i--;
                    break;
                }
            }

        }
        return skillSelection;
    }

    private String[] removeItem(String[] array, int index)
    {
        String[] newArray = new String[array.length-1];
        for (int i=0; i<index;i++)
        {
            newArray[i] = array[i];
        }
        for (int i = index+1; i < array.length; i++)
        {
            newArray[i-1] = array[i];
        }
        return newArray;
    }
}
