import org.example.crawler.web.CrawlerController;

import java.io.File;

public class Application {
    public static void main(String[] args) throws Exception {
        File crawlStorage = new File("crawler4j");

        System.out.println(new CrawlerController(crawlStorage).top100words(args[0], Integer.parseInt(args[1])));
    }
}
