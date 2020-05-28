public class Spells {
    private String[] cantrips = new String[5];
    private int cantripIndex = 0;

    public String[] getCantrips() {
        String[] cantripNull = new String[cantripIndex];
        ArrayFunctions.copyArray(cantripNull, cantrips);
        return cantripNull;
    }

    public String getCantipsToString(String breakString){
        String returnString = "";
        for (int i = 0; i < cantripIndex; i++)
        {
            returnString += cantrips[i] + breakString;
        }
        returnString = returnString.substring(0, returnString.length()-breakString.length());
        return returnString;
    }

    public int addCantrip (String... cantripName)
    {
        int repeat = 0;
        for (int i = 0; i < cantripName.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j < cantripIndex; j++)
            {
                String testString = cantripName[i];
                int indexOfChar = testString.indexOf("—");
                if (indexOfChar!=-1)
                {
                    testString = testString.substring(0, indexOfChar);
                }

                String testCantrip = cantrips[j];
                int cantipInt = testCantrip.indexOf("—");
                if (cantipInt!=-1)
                {
                    testCantrip = testCantrip.substring(0, cantipInt);
                }
//                System.out.println(testString);
//                System.out.print(testString.equalsIgnoreCase(testCantrip));
                isRepeat = testString.equalsIgnoreCase(testCantrip);

            }
            if (isRepeat)
            {
                repeat++;
            }
            else
            {
                cantrips[cantripIndex] = cantripName[i];
                cantripIndex++;
            }
        }
        return repeat;
    }

    private String[] firstLevel = new String[10];
    private int firstLevelIndex = 0;

    public String[] getFirstLevel() {
        String[] firstLevelNull = new String[firstLevelIndex];
        ArrayFunctions.copyArray(firstLevelNull, firstLevel);
        return firstLevelNull;
    }

    public String getFirstLevelToString(String breakString){
        String returnString = "";
        for (int i = 0; i < firstLevelIndex; i++)
        {
            returnString += firstLevel[i] + breakString;
        }
        returnString = returnString.substring(0, returnString.length()-breakString.length());
        return returnString;
    }

    public int addFirstLevel (String... firstLevelName)
    {
        int repeat = 0;
        for (int i = 0; i < firstLevelName.length; i++)
        {
            boolean isRepeat = false;
            for (int j = 0; j < firstLevelIndex; j++)
            {
                isRepeat = firstLevelName[i].equalsIgnoreCase(firstLevel[j]);
                if (isRepeat)
                {
                    repeat++;
                    break;
                }
            }
            if (!isRepeat)
            {
                firstLevel[firstLevelIndex] = firstLevelName[i];
                firstLevelIndex++;
            }
        }
        return repeat;
    }
}
