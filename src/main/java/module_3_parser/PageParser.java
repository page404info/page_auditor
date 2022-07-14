package module_3_parser;

import com.google.gson.Gson;
import helper.FileName;
import helper.MyHelper;
import helper.PropertyReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Data
public class PageParser {
    private List<Page> pageObjList = new ArrayList<>();
    private String pathToInternalHref = PropertyReader.getInstance().getSrcDir() + FileName.HREF_INTERNAL.getName();
    private String pathToPageObject = PropertyReader.getInstance().getSrcDir() + FileName.PAGE_OBJECT.getName();

    public void parse() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> pages = MyHelper.readHrefFromFile(pathToInternalHref);
        int pagesSize = pages.size();
        for (String page : pages) {
            getPageMetadata(page);
            System.out.println(pagesSize);
            pagesSize--;
        }

        writePageObjListToJsonFile(pageObjList);
    }

    private void writePageObjListToJsonFile(List<Page> pages){
        Gson gson = new Gson();
        String jsonString = gson.toJson(pages);
        MyHelper.writeJsonToFile(jsonString, pathToPageObject);
    }

    private void getPageMetadata(String page) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            Page pageObj = new Page();
            pageObj.setPageName(page);
            Document document = Jsoup.connect(page).maxBodySize(0).userAgent("Mozilla").get();

            pageObj.setTitle(parseTitle(document));
            pageObj.setDescription(parseDescription(document));
            pageObj.setH1(parseH1(document));

            pageObj.setCharset(parseCharset(document));
            pageObj.setViewport(parseViewport(document));

            pageObj.setLinkList(parseLink(document));
            pageObj.setScriptList(parseScript(document));

            pageObj.setImgList(parseImg(document));
            pageObj.setHrefList(parseHref(document));

            pageObj.setElementCounter(parseElementCounter(document));

            pageObjList.add(pageObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseTitle(Document document) {
        Elements items = document.getElementsByTag("title");
        String result = "more1";
        if (items.size() == 1){
            result = items.get(0).text();
        }
        return result;
    }

    private String parseDescription(Document document) {
        Elements items = document.select("meta[name='description']");
        String result = "more1";
        if (items.size() == 1){
            result = items.get(0).attr("content");
        }
        return result;
    }

    private String parseH1(Document document) {
        Elements items = document.getElementsByTag("h1");
        String result = "more1";
        if (items.size() == 1){
            result = items.get(0).text();
        }
        return result;
    }

    private String parseCharset(Document document) {
        Elements items = document.select("meta[charset]");
        Elements items2 = document.select("meta[http-equiv='Content-Type']");
        String result = "more1";
        if (items.size() == 1){
            result = items.get(0).attr("charset");
        } else if(items.size() == 0 && items2.size() == 1){
            result = items2.get(0).attr("content").replace(";", ",");
        }
        return result;
    }

    private String parseViewport(Document document) {
        Elements items = document.select("meta[name='viewport']");
        String result = "more1";
        if (items.size() == 1){
            result = items.get(0).attr("content");
        }
        return result;
    }

    private List<String> parseLink(Document document) {
        Elements items = document.select("link[rel='stylesheet']");
        List<String> results = new ArrayList<>();
        for (Element item : items) {
                results.add(item.attr("abs:href"));
        }
        return results;
    }

    private List<String> parseScript(Document document) {
        Elements items = document.select("script[src]");
        List<String> results = new ArrayList<>();
        for (Element item : items) {
            results.add(item.attr("abs:src"));
        }
        return results;
    }

    private List<Img> parseImg(Document document) {
        Elements items = document.getElementsByTag("img");
        List<Img> results = new ArrayList<>();
        for (Element item : items) {
            Img img = new Img();
            img.setSrc(item.attr("abs:src"));
            img.setAlt(item.attr("alt"));
            img.setWidth(item.attr("width"));
            results.add(img);
        }
        return results;
    }

    private List<Href> parseHref(Document document) {
        Elements items = document.getElementsByTag("a");
        List<Href> results = new ArrayList<>();
        for (Element item : items) {
            Href href = new Href();
            href.setHref(item.attr("abs:href"));
            href.setTarget(item.attr("target"));
            href.setRel(item.attr("rel"));
            href.setText(item.text());
            results.add(href);
        }
        return results;
    }

    private ElementCounter parseElementCounter(Document document) {
        ElementCounter elementCounter = new ElementCounter();

        Elements titles = document.getElementsByTag("title");
        Elements descriptions = document.select("meta[name='description']");
        Elements favicons = document.select("link[rel='icon']");

        Elements styles = document.getElementsByTag("style");
        Elements links = document.select("link[rel='stylesheet']");
        Elements scripts = document.getElementsByTag("script");

        Elements h1s = document.getElementsByTag("h1");
        Elements h2s = document.getElementsByTag("h2");
        Elements h3s = document.getElementsByTag("h3");
        Elements h4s = document.getElementsByTag("h4");
        Elements h5s = document.getElementsByTag("h5");
        Elements h6s = document.getElementsByTag("h6");

        Elements hrefs = document.getElementsByTag("a");
        Elements imgs = document.getElementsByTag("img");
        Elements inputs = document.getElementsByTag("input");
        Elements buttons = document.getElementsByTag("button");
        Elements tables = document.getElementsByTag("table");


        elementCounter.setTitleCount(titles.size());
        elementCounter.setDescriptionCount(descriptions.size());
        elementCounter.setFaviconCount(favicons.size());

        elementCounter.setStyleCount(styles.size());
        elementCounter.setLinkCount(links.size());
        elementCounter.setScriptCount(scripts.size());

        elementCounter.setH1Count(h1s.size());
        elementCounter.setH2Count(h2s.size());
        elementCounter.setH3Count(h3s.size());
        elementCounter.setH4Count(h4s.size());
        elementCounter.setH5Count(h5s.size());
        elementCounter.setH6Count(h6s.size());

        elementCounter.setHrefCount(hrefs.size());
        elementCounter.setImgCount(imgs.size());
        elementCounter.setInputFieldCount(inputs.size());
        elementCounter.setButtonCount(buttons.size());
        elementCounter.setTableCount(tables.size());
        elementCounter.setCharCount(document.text().length());

        return elementCounter;
    }

}
