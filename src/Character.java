//This is the Main Class (TM). It has all the fun things. The user prompts at the begining? That's here.
//Tonnes of fun names? That's here. Class selection? Still here. It then splits off into the Fighter, Barbarian, and
//Rogue classes.


import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
//this is the main running class for this program. It calls all the other classes and prompts the user for inputs.
public class Character {


    public static String seedString = "Hello!";
    public static long seed = 0;
    public static Random rand = new Random();
    public static Scanner scan = new Scanner(System.in);
    private static String userAncestry = "";
    private static String userClass = "";
    private static Stats stats = new Stats();
    private static boolean needStats = true;
    private static String[] nameList =//this is a list of fun names if the user does not enter one. All of them are stolen from the fantastic show
            //Critical Role, which you should definitely watch. youtube.com/criticalrole  It's really nice. Especially now a days... with a global pandemic...
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
        "Fjord Stone",//It's a bunch of car puns...
        "Fjord Tough",
        "Fjord Flex",
        "Fjord Mustang",
        "Fjord Fusion",
        "Fjord Explorer",
        "Yasha Nydoorin",
        "Mollymauk Tealeaf",
        "M.T.",
        "Kiri",
        "Sprinkle",
        "Professor Thaddeus",
        "Lady Kima of Vord",
        "Lord Briarwood",
        "Lady Briarwood",
        "Fogg",
        "Matthew Christopher Mercer",
        "Bryce",
        "Caduceus Clay",
        "Nott the Brave",
        "Veth the Brave",
        "Veth Brenatto",
        "Bren Aldric Ermendrud",
        "Frumpkin",
        "The Fey King",
        "The Traveler",
        "The Gentleman",
        "The Ruby of the Sea",
        "Clayton Sharpe",
        "Arcanist Allura Vysoren",
        "Tiberius Stormwind from Draconia, moreover of Vox Machina at Greyskull Keep"
    };

    public static void main(String[] args) throws IOException {

        JTextField characterName = new JTextField();
        JCheckBox randomizeAll = new JCheckBox();
        Object[] message = {
                "Character Name: ", characterName,//this determines what the user needs to input
                "Randomize Ancestry, Class, and Stats: ", randomizeAll
        };

        JOptionPane.showMessageDialog(null, message, "Welcome to DnD5e Character Creator!", 1);//this shows the first screen that the user sees

        seedString = characterName.getText();
        if (characterName.getText().isEmpty())
        {
            seedString = nameList[rand.nextInt(nameList.length)];//if you don't enter a name, this chooses one for you
        }


        int[] statTemp = new int[6];
        //do you really have to read through all this? It seems rather time consuming...


        if (!randomizeAll.isSelected()) {//there was a tick box in the first window that allowed the user to choose if they wanted to select stuff. If they wanted to, this happens:

            String[] ancestryChoices = {"", "Human", "High Elf", "Wood Elf", "Hill Dwarf", "Mountain Dwarf", "Lightfoot Halfling", "Stout Halfling"};
            userAncestry = (String) JOptionPane.showInputDialog(null, "Choose your ancestry:",//the user gets a popup asking them to choose their ancestry
                    seedString+"'s Ancestry Selection", JOptionPane.QUESTION_MESSAGE, null, ancestryChoices, ancestryChoices[0]);

            String[] classChoices = {"", "Barbarian", "Fighter", "Rogue"};//another popup to choose their class
            userClass = (String) JOptionPane.showInputDialog(null, "Choose your class:",
                    seedString+"'s Class Selection", JOptionPane.QUESTION_MESSAGE, null, classChoices, classChoices[0]);


            JTextField[] statInput = new JTextField[6];//a final popup to choose their stats
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

            for (int i = 0; i < statInput.length; i++)//if they don't put in integers, this detects that and generates them for the user.
            {
                try
                {
                    statTemp[i]=Integer.parseInt(statInput[i].getText());
                }
                catch (NumberFormatException e)
                {
                    needStats = true;
                    break;
                }
            }



        }

        for (int i = 0; i < seedString.length(); i++)
        {
            seed += (int)seedString.charAt(i);
        }
        rand.setSeed(seed);//this turns the name into a seed, meaning that if you put the same name in multiple times, you get the same character

        if (needStats) {//if stats need to be generated (whether because they weren't generated or were incorrectly inputted) this generates the stats via the "stats" class
            stats.generate();
        }
        else
        {
            stats.setScores(statTemp);
        }


        String classAssessment = userClass;//assigns to the user's input
        while (!(classAssessment.equalsIgnoreCase("fighter")||classAssessment.equalsIgnoreCase("rogue")||classAssessment.equalsIgnoreCase("barbarian")))
        {//if the user didn't input anything, it comes here, where the program decides.
            classAssessment = classAssess(stats.scores);
        }

        switch (classAssessment.toUpperCase())//if it fits one of these, it creates that class and creates the first level.
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

    private static String classAssess (int[] statArray)//this is what determines a class if the user doesn't decide
    {
        int goodAmount = 0;
        int highAmount = 0;
        int lowAmount = 0;

        for (int i = 0; i <6; i++)//this determines the frequency for different tiers of stats.
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

        //you'll notice that I have more classes here than are able to be generated; when I update it, these will be included
        int[] classWeight = {0,1,1,0,1,1,2,0,1,0,0,1};
        String[] className = {"Bard", "Barbarian", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Warlock", "Sorcerer", "Wizard"};

        if (lowAmount<=1)//assigns a weight based on the frequency of stat tiers.
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
        for (int i = 0; i < classWeight.length; i ++)//determines the total weight of all the classes
        {
            totalWeight+=classWeight[i];
        }

        int decidedNumber = Character.rand.nextInt(totalWeight)+1;//randomizes which class it will be

        int classnumber = -1;
        while (decidedNumber > 0)
        {
            classnumber++;
            decidedNumber -= classWeight[classnumber];//finds that class
        }



        return className[classnumber];//outputs the name of that class
    }


}
