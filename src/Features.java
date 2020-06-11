//This stores all the features that a character has. They can be added in anywhere in the program, then they get printed
//out all together, super easily.
public class Features {
    private String[][] features = new String[20][2];
    private int[][] featureUses = new int[20][2];
    private int featureIndex = 0;
    private int darkvisionRange = 0;
    private int speed = 0;
    private boolean isMedium = true;

    public void setSpeed(int speed) {
        this.speed = speed;
    }//set and get the variable speed

    public int getSpeed() {
        return speed;
    }

    public void setMedium(boolean medium) {
        isMedium = medium;//note if the character is medium or small
    }

    public void setDarkvisionRange(int darkvisionRange) {
        this.darkvisionRange = darkvisionRange;
    }//sets the range of darkvision.

    public int getDarkvisionRange() {
        return darkvisionRange;
    }

    public void addFeature(String title, String description, int... uses)
    {
        features[featureIndex][0] = title;
        features[featureIndex][1] = description;
        if (uses.length>=1)
        {
            featureUses[featureIndex][0]= uses[0];
        }
        if (uses.length == 2)
        {
            featureUses[featureIndex][1] = uses[1];
        }
        featureIndex++;//adds a feature to teh feature array
    }

    public boolean updateFeature (String title, String descriptionUpdate, int... usesUpdate)
    {
        for (int i = 0; i < featureIndex; i ++)
        {
            if (features[i][0].equalsIgnoreCase(title))
            {
                if (!descriptionUpdate.isEmpty())
                {
                    features[i][2] = descriptionUpdate;
                }
                if (usesUpdate.length>=1)
                {
                    featureUses[i][0] = usesUpdate[0];
                }
                return true;
            }
        }
        return false;
    }

    public String getFeatures()
    {
        String output = "";
        output += getDarkvision();
        for (int i = 0; i< features.length; i ++)
        {
            if (features[i][0]== null)//prints the features with a line break and horizontal line between them
            {
                break;
            }
            output += features[i][0];
            if (featureUses[i][0]!=0)
            {
                output += " (";
                if (featureUses[i][1]!=1) {
                    output+="  ";
                    for (int k = 1; k < featureUses[i][0]; k++) {
                        output += "|  ";
                    }
                }
                else
                {
                    output+= "__/"+featureUses[i][0];
                }
                output += ")";
            }
            output += "\n"+features[i][1] + "\n-------------------\n";
        }
        return output;
    }

    private String getDarkvision()
    {
        if (darkvisionRange != 0)//darkvision is only printed if it has a range
        {
            String temp = "Darkvision\nYou can see in dim light within "+darkvisionRange+" feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray." +
                    "\n-------------------\n";//has the description for Darkvision
            return temp;
        }
        else
        {
            return "";
        }
    }


}
