public class Backgrounds {
    public static void addBackground (String background, Proficiency proficiency, Inventory inventory, Features features)
    {
        switch (background.toUpperCase())
        {
            case "ACOLYTE":
                proficiency.addSkill(3, "Religion");
                proficiency.addSkill(4, "Insight");
                proficiency.addLanguage("choice", "choice");

                features.addFeature("Shelter of the Faithful", "As an acolyte, you command the respect of " +
                        "those who share your faith, and you can perform the religious ceremonies of your deity. You and " +
                        "your adventuring companions can expect to receive free healing and care at a temple, shrine, or " +
                        "other established presence of your faith, though you must provide any material components " +
                        "needed for spells. Those who share your religion will support you (but only you) at a modest " +
                        "lifestyle.\n" +
                        "\n" +
                        "You might also have ties to a specific temple dedicated to your chosen deity or pantheon, and " +
                        "you have a residence there. This could be the temple where you used to serve, if you remain on " +
                        "good terms with it, or a temple where you have found a new home. While near your temple, you " +
                        "can call upon the priests for assistance, provided the assistance you ask for is not hazardous " +
                        "and you remain in good standing with your temple.");

                inventory.addMisc("A Holy Symbol","5 Sticks of Incense", "Vestments", "Set of Common Clothes");
                if (Character.rand.nextBoolean())
                {
                    inventory.addMisc("Prayer Book");
                }
                else
                {
                    inventory.addMisc("Prayer Wheel");
                }
                inventory.addMoney(15);
                break;
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------

            case "CRIMINAL":
                proficiency.addSkill(1, "Stealth");
                proficiency.addSkill(5, "Deception");
                proficiency.addTool("Thieves' Tools");
                if (Character.rand.nextBoolean())
                {
                    proficiency.addTool("Playing Card Set");
                }
                else
                {
                    proficiency.addTool("Dice Set");
                }

                features.addFeature("Criminal Contact", "You have a reliable and trustworthy contact who " +
                        "acts as your liaison to a network of other criminals. You know how to get messages to and from " +
                        "your contact, even over great distances; specifically, you know the local messengers, " +
                        "corrupt caravan masters, and seedy sailors who can deliver messages for you.");

                inventory.addMisc("Crowbar", "Set of Dark Common Clothes including a Hood");
                inventory.addMoney(15);
                break;
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------

            case "FOLK HERO":
                proficiency.addSkill(4, "Animal Handling", "Survival");
                proficiency.addTool("Vehicles (land)");

                String[] toolPossibilities = {"Alchemist’s Supplies", "Brewer’s Supplies", "Calligrapher's Supplies",
                        "Carpenter’s Tools	", "Cartographer’s Tools", "Cobbler’s Tools", "Cook’s Utensils	",
                        "Glassblower’s Tools", "Jeweler’s Tools", "Leatherworker’s Tools", "Mason’s Tools	",
                        "Painter’s Supplies", "Potter’s Tools", "Smith’s Tools", "Tinker’s Tools", "Weaver’s Tools",
                        "Woodcarver’s Tools"};

                String toolProf = toolPossibilities[Character.rand.nextInt(toolPossibilities.length)];
                proficiency.addTool(toolProf);

                features.addFeature("Rustic Hospitality", "Since you come from the ranks of the common " +
                        "folk, you fit in among them with ease. You can find a place to hide, rest, or recuperate among " +
                        "other commoners, unless you have shown yourself to be a danger to them. They will shield you " +
                        "from the law or anyone else searching for you, though they will not risk their lives for you.");

                inventory.addTool(toolProf);
                inventory.addMisc("Shovel", "Iron Pot", "Set of Common Clothes");
                inventory.addMoney(10);
                break;
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------

            case "NOBLE":
                proficiency.addSkill(3, "History");
                proficiency.addSkill(5, "Persuasion");
                if (Character.rand.nextBoolean())
                {
                    proficiency.addTool("Playing Card Set");
                }
                else
                {
                    proficiency.addTool("Dice Set");
                }
                proficiency.addLanguage("choice");

                features.addFeature("Position of Privilege", "Thanks to your noble birth, people are " +
                        "inclined to think the best of you. You are welcome in high society, and people assume you " +
                        "have the right to be wherever you are. The common folk make every effort to accommodate you " +
                        "and avoid your displeasure, and other people of high birth treat you as a member of the same " +
                        "social sphere. You can secure an audience with a local noble if you need to.");

                inventory.addMisc("Set of Fine Clothes", "Signet Ring", "Scroll of Pedigree");
                inventory.addMoney(25);
                break;
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
            case "SAGE":
                proficiency.addSkill(3, "Arcana", "History");
                proficiency.addLanguage("choice", "choice");

                features.addFeature("Researcher", "When you attempt to learn or recall a piece of lore, " +
                        "if you do not know that information, you often know where and from whom you can obtain it. " +
                        "Usually, this information comes from a library, scriptorium, university, or a sage or other " +
                        "learned person or creature. Your DM might rule that the knowledge you seek is secreted away " +
                        "in an almost inaccessible place, or that it simply cannot be found. Unearthing the deepest " +
                        "secrets of the multiverse can require an adventure or even a whole campaign.");

                inventory.addMisc("Bottle of Black Ink", "Quill", "Small Knife", "A Letter from a dead colleague " +
                        "posing a question you have not yet been able to answer", "Set of Common Clothes");

                inventory.addMoney(10);
                break;
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
            case "SOLDIER":
                proficiency.addSkill(0, "Athletics");
                proficiency.addSkill(5, "Intimidation");
                proficiency.addTool("Vehicles (land)");

                if (Character.rand.nextBoolean())
                {
                    toolProf="Playing Card Set";
                }
                else
                {
                    toolProf="Dice Set";
                }
                proficiency.addTool(toolProf);

                features.addFeature("Military Rank", "You have a military rank from your career as a " +
                        "soldier. Soldiers loyal to your former military organization still recognize your authority " +
                        "and influence, and they defer to you if they are of a lower rank. You can invoke your rank to " +
                        "exert influence over other soldiers and requisition simple equipment or horses for temporary " +
                        "use. You can also usually gain access to friendly military encampments and fortresses where " +
                        "your rank is recognized.");

                inventory.addMisc("Insignia of Rank","A trophy taken from a fallen enemy", "Set of Common Clothes");
                inventory.addMisc(toolProf);
                inventory.addMoney(10);
                break;



        }
    }
}
