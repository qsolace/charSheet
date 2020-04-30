public class ArrayFunctions {

    public static void copyArray (int[] target, int[] copied)
    {
        for (int i = 0; i < target.length; i++)
        {
            target[i] = copied[i];
        }
    }

    public static void copyArray (String[] target, String[] copied)
    {
        for (int i = 0; i < target.length; i++)
        {
            target[i] = copied[i];
        }
    }
}
