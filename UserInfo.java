import java.io.IOException;

public class UserInfo {
    private String userId;
    private String pageLink;
    private String infoUrl;
    private String bpUrl_1;
    private String bpUrl_2;
    private String flagContry;
    private int performance;
    private long rankedScore;
    private long totalScore;
    private long TTH;
    private double ACC;
    private int PC;
    private int PT;
    private int lvl;
    private int Ranks;
    private int localRanks;
    private int maxCombo;
    private int kudosu;
    private int replays;
    private int UID;

    public void outInfo()
    {
        String info = userId + "  Rank: " + Ranks + "  LocalRank: " + Ranks + "  " + performance + "pp" +
                "  RankedScore: " + rankedScore + "  ACC: " + ACC + "  PC:" + PC + "  PT: " + PT + "  TotalScore:"
                + totalScore + "  LV:" + lvl + "  TTH: " + TTH; // + "     " +pageLink + "   " + infoUrl + "   " + bpUrl_1 + "   " + bpUrl_2;
        System.out.println(info);
    }

    public UserInfo(String ID, int UID_value)
    {
        userId = ID;
        UID = UID_value;
        setLink();
    }

    public long getTTH()
    {
        return TTH;
    }

    public int getPC()
    {
        return PC;
    }

    public int getPP()
    {
        return performance;
    }
    public void setID(String ID)
    {
        userId = ID;
    }

    public void setPP(int value)
    {
        performance = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setRS(long value)
    {
        rankedScore = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setTS(long value)
    {
        totalScore = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setACC(double value)
    {
        ACC = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setPC(int value)
    {
        PC = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setPT(int value)
    {
        PT = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setTTH(long value)
    {
        TTH = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setRank(int value)
    {
        Ranks = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setLRank(int value)
    {
        localRanks = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setLv(int value)
    {
        lvl = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setMaxCombo(int value)
    {
        maxCombo = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setKudosu(int value)
    {
        kudosu = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setReplays(int value)
    {
        replays = value;
        //System.out.println("value is " + value + "!!!");
    }

    public String getInfoUrl()
    {
        return infoUrl;
    }

    private void setLink()
    {
        pageLink = "https://osu.ppy.sh/u/" + UID;
        setGenerelInfoUrl();
    }

    private void setGenerelInfoUrl()
    {
        infoUrl = "https://osu.ppy.sh/pages/include/profile-general.php?u=" + UID + "&m=0";
        bpUrl_1 = "https://osu.ppy.sh/pages/include/profile-leader.php?u=" + UID + "&m=0";
        bpUrl_2 = "https://osu.ppy.sh/pages/include/profile-leader.php?u=" + UID + "&m=0&pp=1";
    }
}
