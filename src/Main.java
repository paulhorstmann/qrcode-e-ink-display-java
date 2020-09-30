import content.Crawler;
import content.HerderArticle;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)  {
        ArrayList<DisplayPage> displayPages = new ArrayList<>();
        Crawler crawler = new Crawler();
        HerderArticle localAticle;
        for (int i = 0; i < 6; i++) {
            localAticle = crawler.getContent().get(i);
            displayPages.add(new DisplayPage("Herder News", localAticle.getTitle(), localAticle.getDescription(), localAticle.getLink(), 6, i+1));
        }
        for (DisplayPage page: displayPages) {
            System.out.println(page.toString());
        }
    }
}
