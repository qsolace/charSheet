//This keeps track of all the skills and proficiencies. It stores them in an array, and has a bunch of functions that
//help me access them.

import java.util.Arrays;
public class Proficiency {


    private String weapons[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int weaponIndex =0;

    public String[] getWeapons() {
        return weapons;
    }
    public String getWeaponsToString()//export all weapons as a string, removing blank values
    {

        String returnString = "";
        for (int i = 0; i < weapons.length; i++)
        {

            returnString = returnString + weapons[i] + " ";
        }
        return returnString;
    }
    public void  addWeapon(String... newWeapon)//add a number of weapon proficiencies to the list, as long as they are unique
    {

        for (int i = 0; i<newWeapon.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <weapons.length; j++ )
            {
                if (weapons[j].equalsIgnoreCase(newWeapon[i]))
                {
                    isRepeat = true;
                }
            }
            if (!isRepeat) {
                weapons[weaponIndex] = newWeapon[i];
                weaponIndex++;
            }
        }
    }
    public boolean isProficientWeapon (String weaponName)//returns whether or not the inputted string is in the proficiency list
    {
        for (int i = 0; i < weapons.length; i++)
        {
            if (weaponName.equalsIgnoreCase(weapons[i]))
            {
                return true;
            }
        }
        return false;
    }

    public int getWeaponIndex() {
        return weaponIndex;
    }

    private String armor[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int armorIndex =0;//same as weapons

    public String[] getArmor() {
        return armor;
    }
    public String getArmorToString()
    {
        String returnString = "";
        for (int i = 0; i < armor.length; i++)
        {
            returnString = returnString + armor[i] + " ";
        }
        return returnString;
    }
    public void  addArmor(String... newArmor)
    {
        for (int i = 0; i<newArmor.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <armor.length; j++ )
            {
                if (armor[j].equalsIgnoreCase(newArmor[i]))
                {
                    isRepeat = true;
                }
            }
            if (!isRepeat) {
                armor[armorIndex] = newArmor[i];
                armorIndex++;
            }
        }
    }

    public boolean isProficientArmor (String armorName)//returns whether or not the inputted string is in the proficiency list
    {
        for (int i = 0; i < armor.length; i++)
        {
            if (armorName.equalsIgnoreCase(armor[i]))
            {
                return true;
            }
        }
        return false;
    }

    private String language[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private String[] normalLanguages = {"Dwarvish", "Elvish", "Giant", "Gnomish", "Goblin", "Halfling", "Orc"};
    private int languageIndex =0;//same as weapons

    public String[] getLanguage() {
        return language;
    }
    public String getLanguageToString(String breakString)
    {
        String returnString = "";
        for (int i = 0; i < languageIndex; i++)
        {
            if (language[i].equalsIgnoreCase("choice"))
            {
                boolean newLanguage = false;
                do {
                    newLanguage = specificAddLanguage(normalLanguages[Character.rand.nextInt(normalLanguages.length)], i);

                }while(!newLanguage);
            }
            returnString += language[i] + breakString;
        }
        return returnString.substring(0, returnString.length()-breakString.length());
    }
    public void addLanguage(String... newLanguage)
    {
        for (int i = 0; i<newLanguage.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <language.length; j++ )
            {
                if (language[j].equalsIgnoreCase(newLanguage[i])&&!language[j].equalsIgnoreCase("choice"))
                {
                    isRepeat = true;
                }
            }
            if (!isRepeat) {
                language[languageIndex] = newLanguage[i];
                languageIndex++;
            }
        }
    }

    public boolean specificAddLanguage (String languageAddition, int location)
    {
        for (int i = 0; i < language.length; i++)
        {
            if (languageAddition.equalsIgnoreCase(language[i]))
            {
                return false;
            }
        }
        language[location] = languageAddition;
        return true;
    }


    private String tools[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int toolIndex =0;//same as weapons

    public String[] getTools() {
        return tools;
    }
    public String getToolsToString()
    {
        String returnString = "";
        for (int i = 0; i < tools.length; i++)
        {

            returnString = returnString + tools[i] + " ";
        }
        return returnString;
    }
    public void  addTool(String... newTool)
    {
        for (int i = 0; i<newTool.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <tools.length; j++ )
            {
                if (tools[j].equalsIgnoreCase(newTool[i]))
                {
                    isRepeat = true;
                }
            }
            tools[toolIndex] = newTool[i];
            toolIndex ++;
        }
    }



    private String skills[][] = {//skills are tied to ability scores. The array is organized based on what ability score it is tied to.
            {""},//Strength
            {"", "", ""},//Dexterity
            {},//Constitution
            {"", "", "", "", ""},//Intelligence
            {"", "", "", "", ""},//Wisdom
            {"", "", "", ""}//Charisma
    };

    private int skillIndex[] = {0, 0, 0, 0, 0, 0};//what point in each ability score you are
    public void resetSkills()
    {
        Arrays.fill(skills, "");//fills everything with blank
    }
    public String[][] getSkills() {
        return skills;//returns the 2D array
    }
    public String[] skillsOneDArray()//exports as a 1 D array, removing blanks
    {
        String[] skillReturn = new String[18];
        int skillReturnIndex = 0;
        for (int j = 0; j < skills.length; j++) {
            for (int i = 0; i < skills[j].length; i++) {
                if (!skills[j][i].isEmpty()) {
                    skillReturn[skillReturnIndex] = skills[j][i];
                    System.out.println(skillReturn[skillReturnIndex]);
                    skillReturnIndex++;
                }
            }
        }

        String[] realReturn = new String [skillReturnIndex];
        ArrayFunctions.copyArray(realReturn, skillReturn);
        return realReturn;

    }

    public String getSkillsToString()//puts it out as a string, removing duplicates
    {
        String returnString = "";
        for (int j = 0; j < skills.length; j++) {
            for (int i = 0; i < skills[j].length; i++) {
                if (!skills[j][i].isEmpty()) {
                    returnString = returnString + skills[j][i] + " ";
                }
            }
        }
        return returnString;
    }
    public int addSkill(int abilityScore, String... newSkill)//adds a skill, using the ability score to decide where to assign it.
    {
        int repeat = 0;
        for (int i = 0; i<newSkill.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <skills[abilityScore].length; j++ )
            {
                if (skills[abilityScore][j].equalsIgnoreCase(newSkill[i]))
                {
                    isRepeat = true;
                    repeat++;
                }
            }
            if (!isRepeat) {
                skills[abilityScore][skillIndex[abilityScore]] = newSkill[i];
                skillIndex[abilityScore]++;
            }
        }
        return repeat;
    }

    public boolean isSkill (String skillName)
    {
        String[] skillList = skillsOneDArray();
        for (int i = 0; i < skillsOneDArray().length; i++)
        {
            if (skillName.equalsIgnoreCase(skillList[i]))
            {
                return true;
            }
        }
        return false;
    }

    private String[] expertise =//Expertise is related to skills, but since they will be tied, we don't need to section it by the ability sccores
            {
                    "", "", "", "", "", "", ""
            };
    private int expertiseIndex = 0;

    public String[] getExpertise() {
        return expertise;
    }

    public String getExpertiseToString()
    {
        String output = "";
        for (int i = 0; i <expertiseIndex; i++)
        {
            if (!expertise[i].isEmpty())
            {
                output+= expertise[i] + " ";
            }
        }
        return output;
    }

    public boolean isExpertise (String skillName)//returns true if the string is found in the expertise list
    {
        for (int i = 0; i < expertiseIndex; i ++)
        {
            if (skillName.equalsIgnoreCase(expertise[i]))
            {
                return true;
            }
        }
        return false;
    }

    public void addExpertise(String skillName)//adds a string to the list
    {
        expertise[expertiseIndex] = skillName;
        expertiseIndex ++;
    }


    public static int countMultArray(String[][] array)//returns the number of terms in a 2d array
    {
        int terms = 0;
        for (int i = 0; i<array.length; i++)
        {
            terms += array[i].length;
        }
        return terms;
    }
}
