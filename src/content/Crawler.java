package content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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
        if(doc != null) {
            Elements article = doc.select("div[itemprop=\"blogPost\"]");
            for (Element articleElement: article) {
                description.setLength(0);
                for (Element inner: articleElement.getElementsByTag("p")) {
                    description.append(inner.html().replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll("  Weiterlesen ...", ""));
                }
                content.add(new HerderArticle( (articleElement.select("h2").select("a").html()), (description.toString()), ("https://herder-gymnasium-minden.de" + articleElement.select("h2").select("a").attr("href"))));
            }
        }
    }

    public ArrayList<HerderArticle> getContent() {
        return content;
    }
}
