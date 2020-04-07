public class Human {
    private int statIncrease[] = {1, 1, 1, 1, 1, 1};
    private int speed = 30;
    private int darkvisionRange = 0;

    private String languages[] = {"Common", "choice"};
    private String skills[] = {};
    private String tools[] = {};
    private String weapons[] = {};

    public void applyAncestry(Fighter className)
    {
        for (int i = 0; i < className.assignedStats.length; i++)
        {
            className.assignedStats[i] += statIncrease[i];
        }
        className.addLanguages(languages);
        className.setSpeed(speed);
        className.setDarkvisionRange(darkvisionRange);
    }
}
