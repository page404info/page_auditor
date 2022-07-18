package module_4_report;

import helper.FileName;
import helper.PropertyConfigReader;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Img;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
@Data
public class PageLoadReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_LOAD.getName();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Response response;
        int pageCount = pages.size();

        for (Page page : pages) {
            try {
                FileWriter writer = new FileWriter(pathToReport, true);
                List<String> linkList = page.getLinkList();
                List<String> scriptList = page.getScriptList();
                List<Img> imgList = page.getImgList();

                System.out.println(pageCount + " load report");
                pageCount--;

                long startHtml = System.currentTimeMillis();
                response = given().when().get(page.getPageName());
                long htmlByte = response.getBody().asByteArray().length;
                long finishHtml = System.currentTimeMillis();
                long htmlMs = finishHtml - startHtml;


                long linkByte = 0;
                long startLink = System.currentTimeMillis();
                for (String item : linkList) {
                    try {
                        response = given().when().get(item);
                        linkByte += response.getBody().asByteArray().length;
                    } catch (Exception e) {
                        log.error(e + " " + item);
                    }
                }
                long finishLink = System.currentTimeMillis();
                long linkMs = finishLink - startLink;

                long scriptByte = 0;
                long startScript = System.currentTimeMillis();
                for (String item : scriptList) {
                    try {
                        response = given().when().get(item);
                        scriptByte += response.getBody().asByteArray().length;
                    } catch (Exception e) {
                        log.error(e + " " + item);
                    }
                }
                long finishScript = System.currentTimeMillis();
                long scriptMs = finishScript - startScript;


                long imgByte = 0;
                long startImg = System.currentTimeMillis();
                for (Img item : imgList) {
                    try {
                        response = given().when().get(item.getSrc());
                        imgByte += response.getBody().asByteArray().length;
                    } catch (Exception e) {
                        log.error(e + " " + item);
                    }
                }
                long finishImg = System.currentTimeMillis();
                long imgMs = finishImg - startImg;

                long totalKByte = (htmlByte + linkByte + scriptByte + imgByte) / 1024;
                long totalMs = htmlMs + linkMs + scriptMs + imgMs;
                int isMore3sec = 0;
                if (totalMs / 1000 > 3) {
                    isMore3sec = 1;
                }

                writer.write(page.getPageName() + "");
                writer.append(';');
                writer.write(htmlByte + "");
                writer.append(';');
                writer.write(htmlMs + "");
                writer.append(';');

                writer.write(linkList.size() + "");
                writer.append(';');
                writer.write(linkByte + "");
                writer.append(';');
                writer.write(linkMs + "");
                writer.append(';');

                writer.write(scriptList.size() + "");
                writer.append(';');
                writer.write(scriptByte + "");
                writer.append(';');
                writer.write(scriptMs + "");
                writer.append(';');

                writer.write(imgList.size() + "");
                writer.append(';');
                writer.write(imgByte + "");
                writer.append(';');
                writer.write(imgMs + "");
                writer.append(';');

                writer.write(totalKByte + "");
                writer.append(';');
                writer.write(totalMs + "");
                writer.append(';');
                writer.write(isMore3sec + "");

                writer.append('\n');
                writer.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
    }

    public void createReportHeader() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            FileWriter writer = new FileWriter(pathToReport, false);
            writer.write("pageUrl");
            writer.append(';');

            writer.write("htmlByte");
            writer.append(';');
            writer.write("htmlMs");
            writer.append(';');

            writer.write("cssCount");
            writer.append(';');
            writer.write("cssByte");
            writer.append(';');
            writer.write("cssMs");
            writer.append(';');

            writer.write("scriptCount");
            writer.append(';');
            writer.write("scriptByte");
            writer.append(';');
            writer.write("scriptMs");
            writer.append(';');

            writer.write("imgCount");
            writer.append(';');
            writer.write("imgByte");
            writer.append(';');
            writer.write("imgMs");
            writer.append(';');

            writer.write("totalKByte");
            writer.append(';');
            writer.write("totalMs");
            writer.append(';');
            writer.write("is>3sec");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
