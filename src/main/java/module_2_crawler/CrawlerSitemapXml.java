package module_2_crawler;

import helper.MyHelper;
import helper.PropertyReader;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class CrawlerSitemapXml {
    List<String> xmlList = new ArrayList<>();
    Set<String> hrefList = new TreeSet<>();

    public Set<String> search() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        findHref(PropertyReader.getInstance().getSrcUrl() + "/sitemap.xml");

        while (xmlList.size() > 0) {
            for (int i = 0; i < xmlList.size(); i++) {
                findHref(xmlList.get(i));
                xmlList.remove(xmlList.get(i));
            }
        }

        return hrefList;
    }

    private void findHref(String xmlUrl) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            Document document = Jsoup.connect(xmlUrl).get();
            Elements items = document.getElementsByTag("loc");
            String href = "";
            for (Element item : items) {
                href = item.text();
                if (href.endsWith(".xml")) {
                    xmlList.add(href);
                } else if (href.endsWith(".gz")) {
                    xmlList.add(href.split(".gz")[0]);
                } else {
                    if (MyHelper.isHtmlPage(href)) {
                        hrefList.add(href);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
