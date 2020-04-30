public class Features {
    private String[][] features = new String[10][2];
    private int featureIndex = 0;
    private int darkvisionRange = 0;
    private int speed = 0;
    private boolean isMedium = true;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setMedium(boolean medium) {
        isMedium = medium;
    }

    public void setDarkvisionRange(int darkvisionRange) {
        this.darkvisionRange = darkvisionRange;
    }

    public int getDarkvisionRange() {
        return darkvisionRange;
    }

    public void addFeature(String title, String description)
    {
        features[featureIndex][0] = title;
        features[featureIndex][1] = description;
        featureIndex++;
    }

    public String getFeatures()
    {
        String output = "";
        output += getDarkvision();
        for (int i = 0; i< features.length; i ++)
        {
            if (features[i][0]== null)
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
        if (darkvisionRange != 0)
        {
            String temp = "Darkvision\nYou can see in dim light within "+darkvisionRange+" feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray." +
                    "\n-------------------\n";
            return temp;
        }
        else
        {
            return "";
        }
    }


}
