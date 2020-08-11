import com.github.tomakehurst.wiremock.WireMockServer;
import org.example.crawler.web.CrawlerController;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CrawlerTest {
    File crawlStorage = new File("crawler4j");

    @Test
    public void testCustomPageFetcher() throws Exception {

        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/crawler")).willReturn(aResponse().withBody("<html><body><h1>this this is html, html html</h1></body></html>")));

        List<String> topWords = new CrawlerController(crawlStorage).top100words("http://localhost:" + "8080/crawler", 0);
        Assert.assertEquals("html", topWords.get(0));
        Assert.assertEquals("this", topWords.get(1));
        Assert.assertEquals("is", topWords.get(2));
        wireMockServer.stop();
    }
}
