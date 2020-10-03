import content.Crawler;
import content.HerderArticle;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)  {
        ArrayList<DisplayPage> displayPages = new ArrayList<>();
        Crawler crawler = new Crawler();
        HerderArticle localArticle;
        for (int i = 0; i < 6; i++) {
            localArticle = crawler.getContent().get(i);
            displayPages.add(new DisplayPage("Herder News", localArticle.getTitle(), localArticle.getDescription(), localArticle.getLink(), i+1, 6));
        }
        displayPages.forEach(System.out::println);
    }
}
