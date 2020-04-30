import java.util.Arrays;
public class Proficiency {


    private String weapons[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int weaponIndex =0;

    public String[] getWeapons() {
        return weapons;
    }
    public String getWeaponsToString()
    {

        String returnString = "";
        for (int i = 0; i < weapons.length; i++)
        {

            returnString = returnString + weapons[i] + " ";
        }
        return returnString;
    }
    public void  addWeapon(String... newWeapon)
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
    public boolean isProficientWeapon (String weaponName)
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
    private int armorIndex =0;

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

    private String language[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int languageIndex =0;

    public String[] getLanguage() {
        return language;
    }
    public String getLanguageToString()
    {
        String returnString = "";
        for (int i = 0; i < language.length; i++)
        {

            returnString = returnString + language[i] + " ";
        }
        return returnString;
    }
    public void addLanguage(String... newLanguage)
    {
        for (int i = 0; i<newLanguage.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j <language.length; j++ )
            {
                if (language[j].equalsIgnoreCase(newLanguage[i]))
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


    private String tools[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int toolIndex =0;

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
    private String[] expertise =
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

    public boolean isExpertise (String skillName)
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

    public void addExpertise(String skillName)
    {
        expertise[expertiseIndex] = skillName;
        expertiseIndex ++;
    }


    private String skills[][] = {
            {""},
            {"", "", ""},
            {},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", ""}
    };

    private int skillIndex[] = {0, 0, 0, 0, 0, 0};
    public void resetSkills()
    {
        Arrays.fill(skills, "");
    }
    public String[][] getSkills() {
        return skills;
    }
    public String[] skillsOneDArray()
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

    public String getSkillsToString()
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
    public int addSkill(int abilityScore, String... newSkill)
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


    public static int countMultArray(String[][] array)
    {
        int terms = 0;
        for (int i = 0; i<array.length; i++)
        {
            terms += array[i].length;
        }
        return terms;
    }
}
