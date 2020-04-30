import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class Character {


    public static String seedString = "Hello!";
    public static long seed = 0;
    public static Random rand = new Random();
    public static Scanner scan = new Scanner(System.in);
    private static String userAncestry = "";
    private static String userClass = "";
    private static Stats stats = new Stats();
    private static boolean needStats = true;
    private static String[] nameList =
    {
        "Lord Percival Fredrickstein von Musel Klossowski de Rolo III of Whitestone",
        "Vax'ildan Vessar",
        "Lady Vex'ahlia, Baroness of the Third House of Whitestone and Grand Mistress of the Grey Hunt",
        "Shaun Gilmore",
        "Shakäste",
        "Scanlan Shorthalt",
        "Pike Trickfoot",
        "Grog Strongjaw",
        "Grog Stonejaw",
        "Grog Strongjaw, Grand Poobah de Doink of All of This and That",
        "Keyleth of the Air Ashari",
        "Sir Taryon Gary Darrington",
        "Jester Lavorre",
        "Caleb Widogast",
        "Beauregard Lionette",
        "Fjord Stone",
        "Fjord Tough",
        "Yasha Nydoorin",
        "Mollymauk Tealeaf",
        "Caduceus Clay",
        "Nott the Brave",
        "Veth the Brave",
        "Veth Brenatto",
        "Bren Aldric Ermendrud",
        "Frumpkin",
        "The Traveler",
        "The Gentleman",
        "The Ruby of the Sea",
        "Clayton Sharpe",
        "Arcanist Allura Vysoren",
        "Tiberius Stormwind from Draconia, moreover of Vox Machina at Greyskull Keep"
    };

    public static void main(String[] args) throws IOException {
        JOptionPane jOptionPane = new JOptionPane();




        JTextField characterName = new JTextField();
        JCheckBox randomizeAll = new JCheckBox();
        Object[] message = {
                "Character Name: ", characterName,
                "Randomize Ancestry, Class, and Stats: ", randomizeAll
        };






        JOptionPane.showMessageDialog(null, message, "Welcome to DnD5e Character Creator!", 1);

        System.out.println(characterName.getText().length());
        seedString = characterName.getText();
        if (characterName.getText().isEmpty())
        {
            seedString = nameList[rand.nextInt(nameList.length)];
        }

        System.out.println(seedString);
        System.out.println(randomizeAll.isSelected());
        int[] statTemp = new int[6];

        if (!randomizeAll.isSelected()) {

            String[] ancestryChoices = {"", "Human", "High Elf", "Wood Elf", "Hill Dwarf", "Mountain Dwarf", "Lightfoot Halfling", "Stout Halfling"};
            userAncestry = (String) JOptionPane.showInputDialog(null, "Choose your ancestry:",
                    seedString+"'s Ancestry Selection", JOptionPane.QUESTION_MESSAGE, null, ancestryChoices, ancestryChoices[0]);

            String[] classChoices = {"", "Barbarian", "Fighter", "Rogue"};
            userClass = (String) JOptionPane.showInputDialog(null, "Choose your class:",
                    seedString+"'s Class Selection", JOptionPane.QUESTION_MESSAGE, null, classChoices, classChoices[0]);


            JTextField[] statInput = new JTextField[6];
            for (int i = 0; i <statInput.length; i++)
            {
                statInput[i] = new JTextField();
            }
            Object[] inputStats = {
                    "Stat 1: ", statInput[0],
                    "Stat 2: ", statInput[1],
                    "Stat 3: ", statInput[2],
                    "Stat 4: ", statInput[3],
                    "Stat 5: ", statInput[4],
                    "Stat 6: ", statInput[5]
            };

            JOptionPane.showMessageDialog(null, inputStats, "Stat Selection", 1);
            needStats = false;

            for (int i = 0; i < statInput.length; i++)
            {
                try
                {
                    statTemp[i]=Integer.parseInt(statInput[i].getText());
                }
                catch (NumberFormatException e)
                {
                    System.out.println("FAIL!");
                    needStats = true;
                    break;
                }
            }



        }

        for (int i = 0; i < seedString.length(); i++)
        {
            seed += (int)seedString.charAt(i);
        }
        rand.setSeed(seed);
        if (needStats) {
            stats.generate();
        }
        else
        {
            stats.setScores(statTemp);
        }


//        JOptionPane.showMessageDialog(null, "java is fun", "Title", 1);
        String classAssessment = userClass;
        while (!(classAssessment.equalsIgnoreCase("fighter")||classAssessment.equalsIgnoreCase("rogue")||classAssessment.equalsIgnoreCase("barbarian")))
        {
            classAssessment = classAssess(stats.scores);
        }

        switch (classAssessment.toUpperCase())
        {
            case "FIGHTER":
                Fighter fighter = new Fighter(stats, userAncestry);
                fighter.createFirstLevel();
                break;
            case "ROGUE":
                System.out.println("ROGUE");
                Rogue rogue = new Rogue(stats, userAncestry);
                rogue.createFirstLevel();
                break;
            case "BARBARIAN":
                Barbarian barbarian = new Barbarian(stats, userAncestry);
                barbarian.createFirstLevel();
                break;
        }





    }

    private static String classAssess (int[] statArray)
    {
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

        int decidedNumber = Character.rand.nextInt(totalWeight)+1;

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
