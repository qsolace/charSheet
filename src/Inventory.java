//This stores all the items that a character has. It's nothing super fancy. Just more arrays and functions.

public class Inventory {
    private String dungeonerPackContents[] = {"Backpack", "Crowbar", "Hammer", "10 Pitons", "10 Torches", "Tinderbox", "10 days of rations", "Waterskin", "50 feet of hempen rope"};
    private String explorersPackContents[] = {"Backpack", "Bedroll", "Mess kit", "Tinderbox", "10 torches", "10 days of rations", "Waterskin", "50 feet of hempen rope"};
    private String burglarsPackContents[] = {"Backpack", "Bag of 1000 ball bearings", "10 feet of string", "Bell", "5 candles", "Crowbar", "hammer", "10 pitons", "Hooded Lantern",
    "2 flasks of oil", "5 days of rations", "Tinderbox", "Waterskin", "50 feet of hempen rope"};//pack contents






    private String weapons[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int weaponIndex =0;//weapon storage

    public String[] getWeapons() {
        return weapons;//get the array back
    }
    public String getWeaponsToString()//get the weapons to a string, with a ", " in between
    {
        String returnString = "";
        for (int i = 0; i < weapons.length-1; i++)
        {
            returnString = returnString + weapons[i];
            if (weapons[i+1].isEmpty())
            {
                break;
            }
            else
            {
                returnString+= ", ";
            }

        }
        return returnString;
    }
    public void  addWeapon(String... newWeapon)
    {
        for (int i = 0; i<newWeapon.length; i++)//adds a weapon. This is done by marking the location of an open space, then placing the new weapon there.
            //the marker is then moved over by one.
        {
            weapons[weaponIndex] = newWeapon[i];
            weaponIndex ++;
        }
    }



    private String armor[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int armorIndex =0;//same as weapons

    public String[] getArmor() {
        return armor;
    }
    public String getArmorToString()
    {
        String returnString = "";
        for (int i = 0; i < armor.length-1; i++)
        {
            returnString = returnString + armor[i];
            if (armor[i+1].isEmpty())
            {
                break;
            }
            else
            {
                returnString+= ", ";
            }
        }
        return returnString;
    }
    public void  addArmor(String... newArmor)
    {
        for (int i = 0; i<newArmor.length; i++)
        {
            armor[armorIndex] = newArmor[i];
            armorIndex ++;
        }
    }



    private String tools[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int toolIndex =0;//same as weapons

    public String[] getTools() {
        return tools;
    }
    public String getToolsToString()
    {
        String returnString = "";
        for (int i = 0; i < tools.length-1; i++)
        {

            returnString = returnString + tools[i];
            if (tools[i+1].isEmpty())
            {
                break;
            }
            else
            {
                returnString+= ", ";
            }
        }
        return returnString;
    }
    public void  addTool(String... newTool)
    {
        for (int i = 0; i<newTool.length; i++)
        {
            tools[toolIndex] = newTool[i];
            toolIndex ++;
        }
    }


    private String misc[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private int miscIndex =0;//really. It's the exact same as weapons

    public String[] getMisc() {
        return misc;
    }
    public String getMiscToString()
    {
        String returnString = "";
        for (int i = 0; i < misc.length-1; i++)
        {

            returnString = returnString + misc[i];
            if (misc[i+1].isEmpty())
            {
                break;
            }
            else
            {
                returnString+= ", ";
            }
        }
        return returnString;
    }
    public void  addMisc(String... newMisc)
    {
        for (int i = 0; i<newMisc.length; i++)
        {
            misc[miscIndex] = newMisc[i];
            miscIndex ++;
        }
    }


    public void addDungeonerPack()//just adds the Packs from the top to misc
    {
        addMisc(dungeonerPackContents);
    }
    public void addExplorersPack(){addMisc(explorersPackContents);}

    public void addBurglarsPack(){addMisc(burglarsPackContents);}
}
