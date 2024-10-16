package module_4_report;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Href;
import module_3_parser.objects.Img;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static io.restassured.RestAssured.given;

@Log4j2
@Data
public class PageHrefReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_HREF.getName();
    int fileCount = 1;
    private Map<String, Integer> uniqHrefMap = new TreeMap<>();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String srcUrl = PropertyConfigReader.getInstance().getSrcUrl();
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
                int srcCountReport = linkList.size() + scriptList.size() + hrefList.size() + imgList.size();


                for (String item : linkList) {
                    System.out.println(fileCount + " : " + pageCount + " : " + srcCount + " href report");
                    srcCount--;
                    String type = "link";
                    int statusCode = -1, isInner = 0;
                    if (item.contains(srcUrl)) {
                        isInner = 1;
                    }

                    try {
                        if (isHrefInMap(item)) {
                            statusCode = getStatusCodeFromMap(item);
                        } else {
                            response = given().when().get(item);
                            statusCode = response.getStatusCode();
                            uniqHrefMap.put(item, statusCode);
                        }
                    } catch (Exception e) {
                        uniqHrefMap.put(item, -1);
                        log.error(e + " " + item);
                    }


                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(srcCountReport + "");
                    writer.append(';');
                    writer.write(type + "");
                    writer.append(';');
                    writer.write(MyHelper.getProtocol(item) + "");
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContains_(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsPercent(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsScript(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsParams(item) + "");
                    writer.append(';');

                    writer.write(MyHelper.isHrefLengthMore120(item) + "");
                    writer.append(';');
                    writer.write(item.length() + "");
                    writer.append(';');
                    writer.write(item + "");
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                for (String item : scriptList) {
                    System.out.println(fileCount + " : " + pageCount + " : " + srcCount + " href report");
                    srcCount--;
                    String type = "script";
                    int statusCode = -1, isInner = 0;
                    if (item.contains(srcUrl)) {
                        isInner = 1;
                    }

                    try {
                        if (isHrefInMap(item)) {
                            statusCode = getStatusCodeFromMap(item);
                        } else {
                            response = given().when().get(item);
                            statusCode = response.getStatusCode();
                            uniqHrefMap.put(item, statusCode);
                        }
                    } catch (Exception e) {
                        uniqHrefMap.put(item, -1);
                        log.error(e + " " + item);
                    }


                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(srcCountReport + "");
                    writer.append(';');
                    writer.write(type + "");
                    writer.append(';');
                    writer.write(MyHelper.getProtocol(item) + "");
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContains_(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsPercent(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsScript(item) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsParams(item) + "");
                    writer.append(';');

                    writer.write(MyHelper.isHrefLengthMore120(item) + "");
                    writer.append(';');
                    writer.write(item.length() + "");
                    writer.append(';');
                    writer.write(item + "");
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                for (Href item : hrefList) {
                    System.out.println(fileCount + " : " + pageCount + " : " + srcCount + " href report");
                    srcCount--;
                    String type = "href";
                    String itemUrl = item.getHref();
                    int statusCode = -1, isInner = 0;
                    if (itemUrl.contains(srcUrl)) {
                        isInner = 1;
                    }

                    if (MyHelper.isDocumentPage(itemUrl)) {
                        statusCode = -2;
                    } else {
                        try {
                            if (isHrefInMap(itemUrl)) {
                                statusCode = getStatusCodeFromMap(itemUrl);
                            } else {
                                response = given().when().get(itemUrl);
                                statusCode = response.getStatusCode();
                                uniqHrefMap.put(itemUrl, statusCode);
                            }
                        } catch (Exception e) {
                            uniqHrefMap.put(itemUrl, -1);
                            log.error(e + " " + itemUrl);
                        }
                    }

                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(srcCountReport + "");
                    writer.append(';');
                    writer.write(type + "");
                    writer.append(';');
                    writer.write(MyHelper.getProtocol(item.getHref()) + "");
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContains_(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsPercent(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsScript(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsParams(itemUrl) + "");
                    writer.append(';');

                    writer.write(MyHelper.isHrefLengthMore120(itemUrl) + "");
                    writer.append(';');
                    writer.write(itemUrl.length() + "");
                    writer.append(';');
                    writer.write(itemUrl + "");
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write(item.getTarget() + "");
                    writer.append(';');
                    writer.write(item.getRel() + "");
                    writer.append(';');
                    writer.write(item.getText() + "");
                    writer.append(';');
                    writer.write(item.getAriaLabel() + "");
                    writer.append(';');
                    writer.write(item.getTabIndex() + "");
                    writer.append('\n');
                }

                for (Img item : imgList) {
                    System.out.println(fileCount + " : " + pageCount + " : " + srcCount + " href report");
                    srcCount--;
                    String type = "img";
                    String itemUrl = item.getSrc();
                    int statusCode = -1, isInner = 0;
                    if (itemUrl.contains(srcUrl)) {
                        isInner = 1;
                    }

                    try {
                        if (isHrefInMap(itemUrl)) {
                            statusCode = getStatusCodeFromMap(itemUrl);
                        } else {
                            response = given().when().get(itemUrl);
                            statusCode = response.getStatusCode();
                            uniqHrefMap.put(itemUrl, statusCode);
                        }
                    } catch (Exception e) {
                        uniqHrefMap.put(itemUrl, -1);
                        log.error(e + " " + itemUrl);
                    }


                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(srcCountReport + "");
                    writer.append(';');
                    writer.write(type + "");
                    writer.append(';');
                    writer.write(MyHelper.getProtocol(item.getSrc()) + "");
                    writer.append(';');

                    writer.write(isInner + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContains_(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsPercent(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsScript(itemUrl) + "");
                    writer.append(';');
                    writer.write(MyHelper.isHrefContainsParams(itemUrl) + "");
                    writer.append(';');

                    writer.write(MyHelper.isHrefLengthMore120(itemUrl) + "");
                    writer.append(';');
                    writer.write(itemUrl.length() + "");
                    writer.append(';');
                    writer.write(itemUrl + "");
                    writer.append(';');

                    writer.write(statusCode + "");
                    writer.append(';');

                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append(';');
                    writer.write("");
                    writer.append('\n');
                }

                writer.close();
                pageCount--;
            } catch (IOException e) {
                log.error(e);
            }
        }

        fileCount++;
    }

    public void createReportHeader() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            FileWriter writer = new FileWriter(pathToReport, false);
            writer.write("pageUrl");
            writer.append(';');
            writer.write("srcCount");
            writer.append(';');
            writer.write("type"); //link, script, href, img
            writer.append(';');
            writer.write("protocol"); //https, ftp
            writer.append(';');

            writer.write("isInner");
            writer.append(';');
            writer.write("is_");
            writer.append(';');
            writer.write("is%");
            writer.append(';');
            writer.write("isScript");
            writer.append(';');
            writer.write("isParams");
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
            writer.append(';');
            writer.write("ariaLabel");
            writer.append(';');
            writer.write("tabIndex");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

    private boolean isHrefInMap(String href) {
        boolean result = false;
        Set<String> keys = uniqHrefMap.keySet();

        for (String key : keys) {
            if (key.equals(href)) {
                result = true;
            }
        }

        return result;
    }

    private int getStatusCodeFromMap(String href) {
        int result = -1;
        Set<String> keys = uniqHrefMap.keySet();

        for (String key : keys) {
            if (key.equals(href)) {
                result = uniqHrefMap.get(key);
            }
        }

        return result;
    }


}
