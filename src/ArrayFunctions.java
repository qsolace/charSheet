public class ArrayFunctions {//just some useful array things. Like you do

    public static void copyArray (int[] target, int[] copied)//copies one array into another
    {
        for (int i = 0; i < target.length; i++)
        {
            target[i] = copied[i];
        }
    }

    public static void copyArray (String[] target, String[] copied)//copies one array into another
    {
        for (int i = 0; i < target.length; i++)
        {
            target[i] = copied[i];
        }
    }
}
