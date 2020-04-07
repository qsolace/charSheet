import java.util.Random;

public class Character {
//    public int[] scoreArray = {0,0,0,0,0,0};
    private int strength_score;
    private int dexterity_score;
    private int constitution_score;
    private int intelligence_score;
    private int wisdom_score;
    private int charisma_score;

    private int strength_mod;
    private int dexterity_mod;
    private int constitution_mod;
    private int intelligence_mod;
    private int wisdom_mod;
    private int charisma_mod;



    public static void main(String[] args){
        Stats blah = new Stats();

        int[] scoreArray = {0,0,0,0,0,0};
        do {
            for (int i = 0; i < 6; i++) {
                scoreArray[i] = blah.generate();
//            System.out.println(scoreArray[i]);
            }
        } while (!(classAssess(scoreArray).equalsIgnoreCase("fighter")));

        Fighter fight = new Fighter();

        fight.addStatNumbers(scoreArray);
        System.out.println(fight.orderedReturnStats());
        System.out.println(fight.assignedReturnStats());
        fight.createModifiers();
        System.out.println(fight.modifierReturn());
        fight.createFirstLevel();
        System.out.println(fight.assignedReturnStats());
        System.out.println("HP: " + fight.getHitPoints());
        System.out.println("AC: " + fight.getArmorClass());
        System.out.println("Saves: " + fight.saveModReturn());
        System.out.println("Languages: "+ fight.getLanguages()[0] + fight.getLanguages()[1] + fight.getLanguages()[2]);


    }

    private static String classAssess (int[] statArray)
    {
        Random rand = new Random();

        int goodAmount = 0;
        int highAmount = 0;
        int lowAmount = 0;

        for (int i = 0; i <6; i++)
        {
            if (statArray[i] >=15)
            {
                highAmount+=1;
            }
            else if (statArray[i] >= 13)
            {
                goodAmount += 1;
            }
            else if (statArray[i]<10)
            {
                lowAmount +=1;
            }
        }//Bard - 0, Barbarian- 1, Cleric -2 , Druid 3, Fighter 4, Monk 5, Paladin 6, Ranger 7, Rogue 8, Warlock 9, Sorcerer 10, Wizard 11
        int[] classWeight = {0,1,1,0,1,1,2,0,1,0,0,1};
        String[] className = {"Bard", "Barbarian", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Warlock", "Sorcerer", "Wizard"};

        if (lowAmount<=1)
        {
            classWeight[0] += 3;//increase Bard chances based on versatility.
        }
        if (highAmount == 0)
        {
            classWeight[3] += 8;
            if (goodAmount == 0)
            {
                classWeight[3] += 100;
            }
        }
        if (highAmount >= 1)
        {


            classWeight[0] += 2;
            classWeight[1] +=1;
            classWeight[2] +=2;
            classWeight[3] += 2;
            classWeight[4] += 1;
            classWeight[5] += 1;
            classWeight[6] += -1;
            classWeight[7] += 1;
            classWeight[8] += 4;
            classWeight[9] += 4;
            classWeight[10] += 3;
            classWeight[11]+= 2;
            if (goodAmount >= 2)
            {

                classWeight[1] += 4;
                classWeight[2] += 2;
                classWeight[3] += 1;
                classWeight[5] += 4;
                classWeight[6] += 4;
                classWeight[7] += 3;
                classWeight[9] += 1;
                classWeight[10] += 1;
                classWeight[11] += 1;
            }
            else if (goodAmount >= 1)
            {
                classWeight[0] += 2;
                classWeight[1] += 2;
                classWeight[2] += 3;
                classWeight[4] += 4;
                classWeight[5] += 2;
                classWeight[6] += 2;
                classWeight[7] += 1;
                classWeight[9] += 3;
                classWeight[10] += 3;
                classWeight[11] += 3;

            }
        }
        if (highAmount>=2)
        {
            classWeight[1]+=3;
            classWeight[2] += 5;
            classWeight[4] += 4;
            classWeight[5] += 5;
            classWeight[6]+=2;
            classWeight[7]+= 2;
            classWeight[8]+=3;
            classWeight[9]+=3;
            classWeight[11] += 4;
            classWeight[10]+=4;
        }
        if (highAmount >= 3)
        {
            classWeight[1]+= 10;
            classWeight[6] += 7;
            classWeight[7] += 5;
            classWeight[5]+= 9;
        }


        int totalWeight = 0;
        for (int i = 0; i < classWeight.length; i ++)
        {
            totalWeight+=classWeight[i];
        }

        int decidedNumber = rand.nextInt(totalWeight)+1;

        int classnumber = -1;
        while (decidedNumber > 0)
        {
            classnumber++;
            decidedNumber -= classWeight[classnumber];
        }


//        System.out.println("\nHigh Stats: " + highAmount);
//        System.out.println("Good Stats: " + goodAmount);
//        System.out.println("Low Stats: " + lowAmount);

        return className[classnumber];
    }


}
