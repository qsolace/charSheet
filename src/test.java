public class test {
    public static void main (String args[])
    {

        for (int i = 0; i<10; i++)
        {
            Spells spells = new Spells();
            Stats stats = new Stats();
            int[] score = {10, 10, 10, 10, 10, 10};
            stats.setScores(score);
            Proficiency proficiency = new Proficiency();
            Features features = new Features();

            Elf.applyAncestry("High Elf", stats, proficiency, features, spells);
            spells.addCantrip("Mending");
            System.out.println(spells.getCantipsToString(", "));
        }

//        String testString = "Face—Intelligence";
//        int indexOfChar = testString.indexOf("—");
//        if (indexOfChar!=-1)
//        {
//            testString = testString.substring(0, indexOfChar);
//        }
//        System.out.println(testString);
//        System.out.print(testString.equalsIgnoreCase("Mending"));
    }
}
