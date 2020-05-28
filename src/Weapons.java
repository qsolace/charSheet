//The Weapons class is accessed by all the "class" classes (Fighter, Rogue, Barbarian) it is an important part of the
//program, storing all the important information about weapons, as well as important functions to access information
//about those weapons.

public class Weapons {
    private static String[][][] weaponList =//list of weapons, sectioned out as followed
            {//all weapons
                    {//simple melee weapons
                            {"Club", "1d4", "bludgeoning", "Light"},//name - damage - damage type - traits
                            {"Dagger", "1d4", "piercing", "Finesse", "Light", "Thrown", "(range 20/60)"},
                            {"Greatclub", "1d8", "bludgeoning", "Two-handed"},
                            {"Handaxe", "1d6", "slashing", "Light", "Thrown", "(range 20/60)"},
                            {"Javelin", "1d6", "piercing", "Thrown", "(range 30/120)"},
                            {"Light Hammer", "1d4", "bludgeoning", "Light", "Thrown", "(range 20/60)"},
                            {"Mace", "1d6", "bludgeoning"},
                            {"Quarterstaff", "1d6", "bludgeoning", "Versatile", "1d8"},
                            {"Sickle", "1d4", "slashing", "Light"},
                            {"Spear", "1d6", "piercing", "Thrown", "(range 20/60)", "Versatile", "1d8"}
                    },
                    {//simple ranged weapons
                            {"Light Crossbow", "1d8", "piercing", "Ammunition", "(range 80/320)", "Loading", "Two-handed"},
                            {"Dart", "1d4", "piercing", "Finesse", "Thrown", "(range 20/60)"},
                            {"Shortbow", "1d6", "piercing", "Ammunition", "(range 80/320)", "Two-handed"},
                            {"Sling", "1d4", "bludgeoning", "Ammunition", "(range 30/120)"}
                    },
                    {//martial melee weapons
                            {"Battleaxe", "1d8", "slashing", "Versatile", "1d10"},
                            {"Flail", "1d8", "bludgeoning"},
                            {"Glaive", "1d10", "slashing", "Heavy", "reach", "Two-handed"},
                            {"Greataxe", "1d12", "slashing", "Heavy", "Two-handed"},
                            {"Greatsword", "2d6", "slashing", "Heavy", "Two-handed"},
                            {"Halberd", "1d10", "slashing", "Heavy", "Reach", "Two-handed"},
                            {"Lance", "1d12", "piercing", "Reach", "Special-Lance"},
                            {"Longsword", "1d8", "slashing", "Versatile", "1d10"},
                            {"Maul", "2d6", "bludgeoning", "Heavy", "Two-handed"},
                            {"Morningstar", "1d8", "piercing"},
                            {"Pike", "1d10", "Heavy", "Reach", "Two-handed"},
                            {"Rapier", "1d8", "piercing", "Finesse"},
                            {"Scimitar", "1d6", "slashing", "Finesse", "Light"},
                            {"Shortsword", "1d6", "piercing", "Finesse", "Light"},
                            {"Trident", "1d6", "piercing", "Thrown", "(range 20/60)", "Versatile", "1d8"},
                            {"War pick", "1d8", "piercing"},
                            {"Warhammer", "1d8", "bludgeoning", "Versatile", "1d10"},
                            {"Whip", "1d4", "slashing", "Finesse", "Reach"}
                    },
                    {//martial ranged weapons
                            {"Blowgun", "1", "piercing", "Ammunition", "(range 25/100)", "loading"},
                            {"Hand Crossbow", "1d6", "piercing", "Ammunition", "(range 20/120)", "Light", "Loading"},
                            {"Heavy Crossbow", "1d10", "piercing", "Ammunition", "(range 100/400)", "Heavy", "Loading", "Two-handed"},
                            {"Longbow", "1d8", "piercing", "Ammunition", "(range 150/600)", "Heavy", "Two-handed"},
                            {"Net", "-", "-", "Thrown", "(range 5/15)", "Special-Net"}
                    }
            };

    public static String versatile (String weaponName)//this tests to see if the inputted string as the "variable tag" if it does, it returns the next tag, the modified damage
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Versatile"))
                        {
                            return weaponList[i][j][k+1];
                        }
                    }

                }
            }
        }
        return "Not Versatile";
    }
    public static String thrown (String weaponName)//checks to see if the string has the tag "thrown." It then returns "thrown" and the next trait, which is the thrown range
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Thrown"))
                        {
                            return "Thrown: " + weaponList[i][j][k+1];
                        }
                    }

                }
            }
        }
        return "";
    }

    public static String ranged (String weaponName)//This checks to see if the string is ranged, with the ammunition trait. It returns "ammunition" and the range
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Ammunition"))
                        {
                            return "Ammunition: " + weaponList[i][j][k+1];
                        }
                    }

                }
            }
        }
        return "";
    }

    public static String damage (String weaponName)//this outputs the normal damage of the string. This is right next to the name.
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    return weaponList[i][j][1];
                }
            }
        }
        return "Not a weapon";
    }
    public static String damageType (String weaponName)//this returns the damage type, which is right after the damage
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    return weaponList[i][j][2];
                }
            }
        }
        return "Not a weapon";
    }

    public static boolean isHeavy (String weaponName)//this finds if the weapon has the "heavy" tag
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Heavy"))
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static boolean isTwoHanded (String weaponName)//this checks if the weapon has the "two-handed" tag
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Two-handed"))
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static boolean isLight (String weaponName)//checks if the weapon has teh "light" tag
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Light"))
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static boolean isDex (String weaponName)//checks if the weapon can use dexterity. It checks if it is ranged weapon, or if it has the tag "finesse"
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    if (i == 1 || i == 3)
                    {
                        return true;
                    }
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Finesse"))
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }
    public static boolean isStr (String weaponName)//checks if the weapon uses strength. This happens if it is a melee weapon or is has the "finesse" tag
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weaponName.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    if (i == 0 || i == 2)
                    {
                        return true;
                    }
                    for (int k = 0; k < weaponList[i][j].length;k++)
                    {
                        if (weaponList[i][j][k].equalsIgnoreCase("Finesse"))
                        {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static String[] martialDex ()//returns all martial weapons that use dexterity
    {
        String[] output = new String[20];
        int outputLocation = 0;
        for (int i = 0; i < weaponList[3].length; i++)
        {
            output[i] = weaponList[3][i][0];
            outputLocation++;
        }

        for (int i = 0; i < weaponList[2].length; i ++)
        {
            if (isDex(weaponList[2][i][0]))
            {
                output[outputLocation] = weaponList[2][i][0];
                outputLocation++;
            }
        }
        String[] out = new String[outputLocation];
        ArrayFunctions.copyArray(out, output);
        return out;
    }

    public static String[] martialStr ()//returns all martial weapons that use strength
    {
        String[] output = new String[20];
        int outputLocation = 0;
        for (int i = 0; i < weaponList[2].length; i++)
        {
            output[i] = weaponList[2][i][0];
            outputLocation++;
        }

        for (int i = 0; i < weaponList[3].length; i ++)
        {
            if (isStr(weaponList[3][i][0]))
            {
                output[outputLocation] = weaponList[3][i][0];
                outputLocation++;
            }
        }
        String[] out = new String[outputLocation];
        ArrayFunctions.copyArray(out, output);
        return out;
    }

    public static boolean isSimpleMelee(String weaponName)
    {
        for (int i = 0; i < weaponList[0].length; i++)
        {
            if (weaponName.equalsIgnoreCase(weaponList[0][i][0]))
            {
                return true;
            }
        }
        return false;
    }

    public static String[] oneHandedWeapon (String... weapon)//checks to see if a weapon can be weilded in one hand (if it doesn't have the "two-handed" tag
    {
        String[] tempOutput = new String[weapon.length];
        int tempLocation = 0;

        for (int i = 0; i < weapon.length; i++)
        {
            if (!isTwoHanded(weapon[i]))
            {
                tempOutput[tempLocation] = weapon[i];
                tempLocation++;
            }
        }
        String[] output = new String[tempLocation];
        ArrayFunctions.copyArray(output, tempOutput);
        return output;

    }

    public static boolean isMelee(String weapon)//checks to see if a weapon is melee
    {
        for (int i = 0 ; i < weaponList.length; i++)
        {
            for (int j = 0; j <weaponList[i].length; j ++)
            {
                if (weapon.equalsIgnoreCase(weaponList[i][j][0]))
                {
                    if (i == 0||i == 2)
                    {
                        return true;
                    }

                }
            }
        }
        return false;
    }

}
