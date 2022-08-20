package module_3_parser;

import com.google.gson.Gson;
import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Log4j2
@Data
public class PageParser {
    private int pageDelimiter = 500;
    private String pathToInternalHref = PropertyConfigReader.getInstance().getSrcDir() + FileName.HREF_INTERNAL.getName();
    private String pathToPageObject;

    public void parse() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> pages = MyHelper.readHrefFromFile(pathToInternalHref);
        List<Page> pageObjList = new ArrayList<>();
        int fileCount = 1, pagesSize = pages.size(), countFiles = countFiles(pages.size(), pageDelimiter);

        writeObjectFileCountToProperty(countFiles);

        for (int i = 0; i < pages.size(); i++) {
            System.out.println(pagesSize);
            pagesSize--;

            if (i == 0 || i % pageDelimiter == 0) {
                pathToPageObject =
                        PropertyConfigReader.getInstance().getSrcDir()
                                + "/_" + fileCount
                                + FileName.PAGE_OBJECT.getName();
                fileCount++;
            }
            pageObjList.add(getPageMetadata(pages.get(i)));

            if (pageObjList.size() == pageDelimiter) {
                writePageObjListToJsonFile(pageObjList);
                pageObjList.clear();
            }
        }

        writePageObjListToJsonFile(pageObjList);
    }

    private void writePageObjListToJsonFile(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Gson gson = new Gson();
        String jsonString = gson.toJson(pages);
        MyHelper.writeJsonToFile(jsonString, pathToPageObject);
    }

    private void writeObjectFileCountToProperty(int count) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        try (OutputStream fos = new FileOutputStream(FileName.OBJECT_FILE_COUNT.getName())) {
            Properties prop = new Properties();

            prop.setProperty("objectFileCount", String.valueOf(count));
            prop.store(fos, null);
            log.info("Object file count property SAVED TO FILE = resources/object_file_count.properties");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countFiles(int listLength, int delimiter) {
        int result = 0;
        if (listLength % delimiter == 0) {
            result = listLength / delimiter;
        } else {
            result = (listLength / delimiter) + 1;
        }
        return result;
    }

    private Page getPageMetadata(String page) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Page pageObj = new Page();

        try {
            pageObj.setPageName(page);
            Document document = Jsoup.connect(page).maxBodySize(0).userAgent("Mozilla").ignoreHttpErrors(true).get();
            log.debug(">>> " + document.toString().length());

            pageObj.setTitle(parseTitle(document));
            pageObj.setDescription(parseDescription(document));
            pageObj.setH1(parseH1(document));

            pageObj.setCharset(parseCharset(document));
            pageObj.setViewport(parseViewport(document));
            pageObj.setFaviconAttr(parseFaviconAttr(document));

            pageObj.setLinkList(parseLink(document));
            pageObj.setScriptList(parseScript(document));

            pageObj.setImgList(parseImg(document));
            pageObj.setVideoList(parseVideo(document));
            pageObj.setHrefList(parseHref(document));

            pageObj.setElementCounter(parseElementCounter(document));
        } catch (IOException e) {
            pageObj.setTitle("-1");
            pageObj.setDescription("-1");
            pageObj.setH1("-1");

            pageObj.setCharset("-1");
            pageObj.setViewport("-1");
            pageObj.setFaviconAttr("-1");

            pageObj.setLinkList(parseLink(null));
            pageObj.setScriptList(parseScript(null));

            pageObj.setImgList(parseImg(null));
            pageObj.setVideoList(parseVideo(null));
            pageObj.setHrefList(parseHref(null));

            pageObj.setElementCounter(parseElementCounter(null));
            log.error(e);
        }
        return pageObj;
    }

    private String parseTitle(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Elements items = document.getElementsByTag("title");
        String result = "_not1";
        if (items.size() == 1) {
            result = items.get(0).text().replace(";", ",");
        }
        return result;
    }

    private String parseDescription(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Elements items = document.select("meta[name='description']");
        String result = "_not1";
        if (items.size() == 1) {
            result = items.get(0).attr("content").replace(";", ",");
        }
        return result;
    }

    private String parseH1(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Elements items = document.getElementsByTag("h1");
        String result = "_not1";
        if (items.size() == 1) {
            result = items.get(0).text().replace(";", ",");
        }
        return result;
    }

    private String parseCharset(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String result = "_not1";
        try {
            Elements items = document.select("meta[charset]");
            Elements items2 = document.select("meta[http-equiv='Content-Type']");

            if (items.size() == 1) {
                result = items.get(0).attr("charset").replace(";", ",");
            } else if (items2.size() == 1) {
                result = items2.get(0).attr("content").replace(";", ",");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    private String parseViewport(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Elements items = document.select("meta[name='viewport']");
        String result = "_not1";
        if (items.size() == 1) {
            result = items.get(0).attr("content").replace(";", ",");
        }
        return result;
    }

    private String parseFaviconAttr(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Elements items = document.select("link[rel='icon']");
        StringBuffer result = new StringBuffer();
        for (Element item : items) {
            result.append(item.attr("abs:href"));
            result.append(" type=");
            result.append(item.attr("type"));
            result.append(" --- ");
        }
        return String.valueOf(result).replace(";", ",");
    }

    private List<String> parseLink(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> results = new ArrayList<>();

        try {
            Elements items = document.select("link[rel='stylesheet']");
            for (Element item : items) {
                results.add(item.attr("abs:href"));
            }
        } catch (Exception e) {
            results.add("-1");
            log.error(e);
        }
        return results;
    }

    private List<String> parseScript(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> results = new ArrayList<>();

        try {
            Elements items = document.select("script[src]");
            for (Element item : items) {
                results.add(item.attr("abs:src"));
            }
        } catch (Exception e) {
            results.add("-1");
            log.error(e);
        }
        return results;
    }

    private List<Img> parseImg(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<Img> results = new ArrayList<>();

        try {
            Elements items = document.getElementsByTag("img");
            for (Element item : items) {
                Img img = new Img();
                img.setSrc(item.attr("abs:src"));
                img.setAlt(item.attr("alt").replace(";", ","));
                img.setWidth(item.attr("width").replace(";", ","));
                results.add(img);
            }
        } catch (Exception e) {
            Img img = new Img();
            img.setSrc("-1");
            img.setAlt("-1");
            img.setWidth("-1");
            results.add(img);
            log.error(e);
        }
        return results;
    }

    private List<Video> parseVideo(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<Video> results = new ArrayList<>();

        try {
            Elements items = document.getElementsByTag("video");
            for (Element item : items) {
                Video video = new Video();
                video.setSrc(item.getElementsByTag("source").get(0).attr("abs:src"));
                video.setWidth(item.attr("width").replace(";", ","));
                video.setHeight(item.attr("height").replace(";", ","));
                results.add(video);
            }
        } catch (Exception e) {
            Video video = new Video();
            video.setSrc("-1");
            video.setWidth("-1");
            video.setHeight("-1");
            results.add(video);
            log.error(e);
        }
        return results;
    }

    private List<Href> parseHref(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<Href> results = new ArrayList<>();

        try {
            Elements items = document.getElementsByTag("a");
            for (Element item : items) {
                Href href = new Href();
                href.setHref(item.attr("abs:href"));
                href.setTarget(item.attr("target").replace(";", ","));
                href.setRel(item.attr("rel").replace(";", ","));
                href.setText(item.text());
                results.add(href);
            }
        } catch (Exception e) {
            Href href = new Href();
            href.setHref("-1");
            href.setTarget("-1");
            href.setRel("-1");
            href.setText("-1");
            results.add(href);
            log.error(e);
        }
        return results;
    }

    private ElementCounter parseElementCounter(Document document) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        ElementCounter elementCounter = new ElementCounter();

        try {
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
            Elements videos = document.getElementsByTag("video");


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
            elementCounter.setVideoCount(videos.size());

            elementCounter.setCharDocumentCount(document.toString().length());
            elementCounter.setCharTextCount(document.text().length());
            elementCounter.setComment(document.toString().contains("<!--")
                    || document.toString().contains("*/"));
        } catch (Exception e) {
            elementCounter.setTitleCount(-1);
            elementCounter.setDescriptionCount(-1);
            elementCounter.setFaviconCount(-1);

            elementCounter.setStyleCount(-1);
            elementCounter.setLinkCount(-1);
            elementCounter.setScriptCount(-1);

            elementCounter.setH1Count(-1);
            elementCounter.setH2Count(-1);
            elementCounter.setH3Count(-1);
            elementCounter.setH4Count(-1);
            elementCounter.setH5Count(-1);
            elementCounter.setH6Count(-1);

            elementCounter.setHrefCount(-1);
            elementCounter.setImgCount(-1);
            elementCounter.setInputFieldCount(-1);
            elementCounter.setButtonCount(-1);
            elementCounter.setTableCount(-1);
            elementCounter.setVideoCount(-1);

            elementCounter.setCharDocumentCount(-1);
            elementCounter.setCharTextCount(-1);
            elementCounter.setComment(true);
            log.error(e);
        }

        return elementCounter;
    }

}
