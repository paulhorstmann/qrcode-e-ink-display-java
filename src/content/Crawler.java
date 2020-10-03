package content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Crawler {
    ArrayList<HerderArticle> content = new ArrayList<>();

    public Crawler() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.herder-gymnasium-minden.de/wbs/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder description = new StringBuilder();
        String url;
        if(doc != null) {
            Elements article = doc.select("div[itemprop=\"blogPost\"]");
            for (Element articleElement: article) {
                description.setLength(0);
                for (Element inner: articleElement.getElementsByTag("p")) {
                    description.append(inner.html().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll(" {2}Weiterlesen ...", ""));
                }
                url =articleElement.select("h2").select("a").attr("href").split("&")[2];
                url = "https://herder-gymnasium-minden.de/wbs/?view=article&" + url.substring(0,url.indexOf(':'));
                content.add(new HerderArticle( (articleElement.select("h2").select("a").html()), (description.toString()), url));
            }
        }
    }

    public ArrayList<HerderArticle> getContent() {
        return content;
    }
}
