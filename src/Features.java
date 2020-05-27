//This stores all the features that a character has. They can be added in anywhere in the program, then they get printed
//out all together, super easily.
public class Features {
    private String[][] features = new String[10][2];
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

    public void addFeature(String title, String description)
    {
        features[featureIndex][0] = title;
        features[featureIndex][1] = description;
        featureIndex++;//adds a feature to teh feature array
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
            output += features[i][0] +"\n";
            output += features[i][1] + "\n-------------------\n";
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
