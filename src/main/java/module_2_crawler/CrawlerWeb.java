package module_2_crawler;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class CrawlerWeb {
    private String srcUrl = PropertyConfigReader.getInstance().getSrcUrl();
    private String pathToSave = PropertyConfigReader.getInstance().getSrcDir() + FileName.HREF_INTERNAL.getName();

    public void search() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        Set<String> innerPages = searchInWeb();
        MyHelper.writeHrefToFile(innerPages, pathToSave);
        System.out.println(">>> page count = " + innerPages.size());

    }

    private Set<String> searchInWeb() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Set<String> resultList = new TreeSet<>();
        resultList.add(srcUrl);

        if (Boolean.parseBoolean(PropertyConfigReader.getInstance().getIsCheckAllPages())) {
            resultList.addAll(new CrawlerFile().search());
            resultList.addAll(new CrawlerSitemapXml().search());
            resultList.addAll(checkAllPages(resultList));
        }

        return resultList;
    }


    private Set<String> findHrefList(String pageUrl) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Set<String> resultList = new TreeSet<>();
        resultList.add(srcUrl);

        try {
            Document document = Jsoup.connect(pageUrl).maxBodySize(0).userAgent("Mozilla").ignoreHttpErrors(true).get();

            Elements links = document.getElementsByTag("a");
            for (Element link : links) {
                resultList.add(link.attr("abs:href"));
            }
        } catch (IOException e) {
            log.error(e);
        }

        return resultList;
    }

    private Set<String> filterInnerHref(Set<String> hrefList) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Set<String> resultList = new TreeSet<>();

        for (String href : hrefList) {
            if (MyHelper.isInnerHref(href)) {
                resultList.add(MyHelper.deleteEndAnchorFromHref(MyHelper.deleteEndSlashFromHref(href)));
            }
        }

        return resultList;
    }


    private Set<String> checkAllPages(Set<String> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Set<String> results = new TreeSet<>();

        Set<String> tempSet = new TreeSet<>(pages);

        while (tempSet.size() > 0) {
            System.out.println(tempSet.size());

            int size1 = results.size();
            results.add(tempSet.stream().findFirst().get());
            int size2 = results.size();

            if (size2 > size1) {
                tempSet.addAll(filterInnerHref(findHrefList(tempSet.stream().findFirst().get())));
            }
            tempSet.remove(tempSet.stream().findFirst().get());
        }

        return results;
    }

}
