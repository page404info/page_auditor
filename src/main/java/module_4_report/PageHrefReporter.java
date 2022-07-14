package module_4_report;

import helper.FileName;
import helper.PropertyReader;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Href;
import module_3_parser.objects.Img;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
@Data
public class PageHrefReporter {
    private String pathToReport = PropertyReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_HREF.getName();

    public void create(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        createReportHeader();
        createReportBody(pages);
        log.info("Page href REPORT SAVED TO FILE = " + pathToReport);
    }

    private void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String srcUrl = PropertyReader.getInstance().getSrcUrl();
        Response response;
        int pageCount = pages.size();

        for (Page page : pages) {
            try {
                FileWriter writer = new FileWriter(pathToReport, true);
                List<String> linkList = page.getLinkList();
                List<String> scriptList = page.getScriptList();
                List<Href> hrefList = page.getHrefList();
                List<Img> imgList = page.getImgList();
                int srcCount = linkList.size() + scriptList.size() + hrefList.size() + imgList.size();

                System.out.println(pageCount + " : " + srcCount + " href report");
                pageCount--;

                for (String item : linkList) {
                    String type = "link";
                    int statusCode = -1, isInner = 0, is_ = 0, isPercent = 0, isMore120Char = 0;
                    if (item.contains(srcUrl)) {
                        isInner = 1;
                    }
                    if (item.contains("_")) {
                        is_ = 1;
                    }
                    if (item.contains("%")) {
                        isPercent = 1;
                    }
                    if (item.length() > 120) {
                        isMore120Char = 1;
                    }

                    try {
                        response = given().when().get(item);
                        statusCode = response.getStatusCode();
                    } catch (Exception e) {
                        log.error(e + " " + item);
                    }

                    writer.write(page.getPageName());
                    writer.append(';');
                    writer.write(srcCount + "");
                    writer.append(';');
                    writer.write(type);
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(is_ + "");
                    writer.append(';');
                    writer.write(isPercent + "");
                    writer.append(';');
                    writer.write(isMore120Char + "");
                    writer.append(';');

                    writer.write(item.length() + "");
                    writer.append(';');
                    writer.write(item);
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                for (String item : scriptList) {
                    String type = "script";
                    int statusCode = -1, isInner = 0, is_ = 0, isPercent = 0, isMore120Char = 0;
                    if (item.contains(srcUrl)) {
                        isInner = 1;
                    }
                    if (item.contains("_")) {
                        is_ = 1;
                    }
                    if (item.contains("%")) {
                        isPercent = 1;
                    }
                    if (item.length() > 120) {
                        isMore120Char = 1;
                    }

                    try {
                        response = given().when().get(item);
                        statusCode = response.getStatusCode();
                    } catch (Exception e) {
                        log.error(e + " " + item);
                    }

                    writer.write(page.getPageName());
                    writer.append(';');
                    writer.write(srcCount + "");
                    writer.append(';');
                    writer.write(type);
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(is_ + "");
                    writer.append(';');
                    writer.write(isPercent + "");
                    writer.append(';');
                    writer.write(isMore120Char + "");
                    writer.append(';');

                    writer.write(item.length() + "");
                    writer.append(';');
                    writer.write(item);
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                for (Href item : hrefList) {
                    String type = "href";
                    int statusCode = -1, isInner = 0, is_ = 0, isPercent = 0, isMore120Char = 0;
                    String itemUrl = item.getHref();
                    if (itemUrl.contains(srcUrl)) {
                        isInner = 1;
                    }
                    if (itemUrl.contains("_")) {
                        is_ = 1;
                    }
                    if (itemUrl.contains("%")) {
                        isPercent = 1;
                    }
                    if (itemUrl.length() > 120) {
                        isMore120Char = 1;
                    }

                    try {
                        response = given().when().get(itemUrl);
                        statusCode = response.getStatusCode();
                    } catch (Exception e) {
                        log.error(e + " " + itemUrl);
                    }

                    writer.write(page.getPageName());
                    writer.append(';');
                    writer.write(srcCount + "");
                    writer.append(';');
                    writer.write(type);
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(is_ + "");
                    writer.append(';');
                    writer.write(isPercent + "");
                    writer.append(';');
                    writer.write(isMore120Char + "");
                    writer.append(';');

                    writer.write(itemUrl.length() + "");
                    writer.append(';');
                    writer.write(itemUrl);
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write(item.getTarget());
                    writer.append(';');
                    writer.write(item.getRel());
                    writer.append(';');
                    writer.write(item.getText());
                    writer.append('\n');
                }

                for (Img item : imgList) {
                    String type = "img";
                    int statusCode = -1, isInner = 0, is_ = 0, isPercent = 0, isMore120Char = 0;
                    String itemUrl = item.getSrc();
                    if (itemUrl.contains(srcUrl)) {
                        isInner = 1;
                    }
                    if (itemUrl.contains("_")) {
                        is_ = 1;
                    }
                    if (itemUrl.contains("%")) {
                        isPercent = 1;
                    }
                    if (itemUrl.length() > 120) {
                        isMore120Char = 1;
                    }

                    try {
                        response = given().when().get(itemUrl);
                        statusCode = response.getStatusCode();
                    } catch (Exception e) {
                        log.error(e + " " + itemUrl);
                    }

                    writer.write(page.getPageName());
                    writer.append(';');
                    writer.write(srcCount + "");
                    writer.append(';');
                    writer.write(type);
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(is_ + "");
                    writer.append(';');
                    writer.write(isPercent + "");
                    writer.append(';');
                    writer.write(isMore120Char + "");
                    writer.append(';');

                    writer.write(itemUrl.length() + "");
                    writer.append(';');
                    writer.write(itemUrl);
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                writer.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    private void createReportHeader() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            FileWriter writer = new FileWriter(pathToReport, false);
            writer.write("pageUrl");
            writer.append(';');
            writer.write("srcCount");
            writer.append(';');
            writer.write("type"); //link, script, href, img
            writer.append(';');

            writer.write("isInner");
            writer.append(';');
            writer.write("is_");
            writer.append(';');
            writer.write("is%");
            writer.append(';');
            writer.write("is>120Char");
            writer.append(';');

            writer.write("srcLength");
            writer.append(';');
            writer.write("src");
            writer.append(';');
            writer.write("statusCode");
            writer.append(';');

            writer.write("target");
            writer.append(';');
            writer.write("rel");
            writer.append(';');
            writer.write("text");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
