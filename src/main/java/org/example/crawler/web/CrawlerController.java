package org.example.crawler.web;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CrawlerController {

    private final File crawlStorage;
    private CrawlConfig config;
    private final RobotstxtConfig robotstxtConfig;

    public CrawlerController(File crawlStorage) {
        this.crawlStorage = crawlStorage;
        this.robotstxtConfig = new RobotstxtConfig();
        this.config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        config.setMaxPagesToFetch(-1);
        config.setPolitenessDelay(1000);
    }

    public List<String> top100words(String baseUrl, int depth) throws Exception {

        int numCrawlers = 12;

        config.setMaxDepthOfCrawling(depth);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtServer robotstxtServer= new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(baseUrl);

        CrawlController.WebCrawlerFactory<HtmlCrawler> factory = () -> new HtmlCrawler(baseUrl);

        controller.start(factory, numCrawlers);
        return Counter.getInstance.getWordsCount().entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .map(Map.Entry::getKey)
                .limit(100)
                .collect(Collectors.toList());

    }
}
