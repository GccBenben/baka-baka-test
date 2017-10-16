import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.lang.Thread;

public class spider {
    private String targetUrl;
    //private static String rankingPageBase = "https://osu.ppy.sh/p/pp/?m=0&s=3&o=1&f=0&page=";
    private static String rankingPageBase = "https://osu.ppy.sh/p/pp/?c=CN&m=0&s=3&o=1&f=&page=";
    private List<String> rankingUrls;
    private HashMap<UserInfo, Integer> userList;
    private int totalPlayer = 0;
    private long totalHit = 0;
    private int totalPC = 0;
    private int totalPP = 0;

    public spider(String url)
    {
        targetUrl = url;
        createUserDatebase();
        enumRankingPageUrl();
        getUsersLink();

        //Document contents = Get_Url(targetUrl);
        //getGenerelInfo(contents, user);
    }
    private Document Get_Url(String url)
    {
        try
        {
            //System.out.println(url);
            Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(8000)
                    //.post()
                    .get();

            return doc;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void createUserDatebase()
    {
        userList = new HashMap<UserInfo,Integer>();
    }

    private void getUsersLink()
    {
        for(int i = 0; i < rankingUrls.size(); i++)
        {
            Document rankingPage = Get_Url(rankingUrls.get(i));
            try
            {
            getRankingInfo(rankingPage,i);
            }
            catch(Exception e){e.printStackTrace();}
            //System.out.println(rankingPage.outerHtml());
            System.out.println("i is:" + i + "    player number: "+ totalPlayer + "    total Hit: " + totalHit + "    total PC: " + totalPC + "   total PP:" + totalPP);
        }
    }

    private void enumRankingPageUrl()
    {
        rankingUrls = new LinkedList<String>();

        for(int i = 1; i < 201; i ++)
        {
            rankingUrls.add(rankingPageBase + i);
            //System.out.println(rankingUrls.get(i-1));
        }
    }

    private void getRankingInfo(Document doc, int page){
        UserInfo user;
        Elements a = doc.select("a[href~=/u/[1-9]\\d*]");
        //Element user_ID = doc.getElementsByAttributeValue("href","/u/124493").first();
        //String test = user_ID.outerHtml();
        int rank = page * 50 + 1;
        for(Element as : a) {
            String linkUrl = as.attr("href");
            String userId = as.text();
            String reg = "[^0-9]";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(linkUrl);
            int uid = Integer.parseInt(m.replaceAll("").trim());
            user = new UserInfo(userId, uid);
            getGenerelInfo(user);
            //totalHit += user.getTTH();
            //totalPC  += user.getPC();
            //getBPinfo(user);
            if (user.getPC() > 38000 && user.getPC() < 42000) {
                totalHit += user.getTTH();
                totalPC += user.getPC();
                totalPP += user.getPP();
                totalPlayer++;
                user.outInfo();
                //System.out.println(totalPlayer);
            }
            userList.put(user, rank);
            //user.outInfo();
            rank++;
        }
    }

    private void getBPinfo(UserInfo user)
    {

    }

    private String getNumbers(String input)
    {
        String reg = "[^0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(input);
        return m.replaceAll("").trim();
    }

    private void getGenerelInfo(UserInfo user)
    {
        String userGenerelUrl = user.getInfoUrl();
        try
        {
            Document doc = Jsoup.connect(userGenerelUrl)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements generel_Info = doc.select("div.profileStatLine");
            for (Element info : generel_Info)
            {
                String linkText = info.text();
                //System.out.println(linkText);
                String regGen = "[^#.0-9]";
                Pattern p = Pattern.compile(regGen);
                Matcher m = p.matcher(linkText);
                String infor = m.replaceAll("").trim();
                if(linkText.contains("Performance:"))
                {
                    //System.out.println(infor.split("#")[0]);
                    user.setPP(Integer.parseInt(infor.split("#")[0]));
                    user.setRank(Integer.parseInt(infor.split("#")[1]));
                    user.setLRank(Integer.parseInt(infor.split("#")[2]));
                }
                else if(linkText.contains("Ranked Score"))
                {
                    //System.out.println(infor);
                    user.setRS(Long.parseLong(infor));
                }
                else if(linkText.contains("Accuracy"))
                {
                    //System.out.println(infor);
                    user.setACC(Double.parseDouble(infor));
                }
                else if(linkText.contains("Count"))
                {
                    //System.out.println(infor);
                    user.setPC(Integer.parseInt(infor));
                }
                else if(linkText.contains("Time"))
                {
                    //System.out.println(infor);
                    user.setPT(Integer.parseInt(infor));
                }
                else if(linkText.contains("Total Score"))
                {
                    //System.out.println(infor);
                    user.setTS(Long.parseLong(infor));
                }
                else if(linkText.contains("Level"))
                {
                    //System.out.println(infor);
                    user.setLv(Integer.parseInt(infor));
                }
                else if(linkText.contains("Hits"))
                {
                    //System.out.println(infor);
                    user.setTTH(Long.parseLong(infor));
                }
                else if(linkText.contains("Combo"))
                {
                    //System.out.println(infor);
                    user.setMaxCombo(Integer.parseInt(infor));
                }
                else if(linkText.contains("Kudosu"))
                {
                    //System.out.println(infor);
                    user.setKudosu(Integer.parseInt(infor));
                }
                else if(linkText.contains("Replays"))
                {
                    //System.out.println(infor);
                    user.setReplays(Integer.parseInt(infor));
                }
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String url = "https://osu.ppy.sh/pages/include/profile-general.php?u=229400&m=0";
        spider spider = new spider(url);
    }
}
