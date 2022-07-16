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
public class PageImgReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_IMG.getName();

    public void create(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        createReportHeader();
        createReportBody(pages);
        log.info("Page image REPORT SAVED TO FILE = " + pathToReport);
    }

    private void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Response response;
        int pageCount = pages.size();

        for (Page page : pages) {
            try {
                FileWriter writer= new FileWriter(pathToReport, true);
                List<Img> items = page.getImgList();

                System.out.println(pageCount + " : " + items.size() + " image report");
                pageCount--;

                for (Img item : items) {
                    int imgByte = -1, isMore200KB = 0;
                    String type = "unknown";
                    try {
                        response = given().when().get(item.getSrc());
                        imgByte = response.getBody().asByteArray().length;
                        if(imgByte/1024 > 200){
                            isMore200KB = 1;
                        }
                    } catch (Exception e) {
                        log.error(e + " " + item.getSrc());
                    }

                    String[] words = item.getSrc().split("\\.");
                    int wLength = words.length;
                    type = words[wLength - 1];

                    writer.write(page.getPageName());
                    writer.append(';');
                    writer.write(items.size() + "");
                    writer.append(';');
                    writer.write(type);
                    writer.append(';');

                    writer.write(isMore200KB + "");
                    writer.append(';');
                    writer.write(imgByte + "");
                    writer.append(';');

                    writer.write(item.getWidth() + "");
                    writer.append(';');
                    writer.write(item.getAlt());
                    writer.append(';');
                    writer.write(item.getSrc());
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
            writer.write("imgCount");
            writer.append(';');
            writer.write("type");
            writer.append(';');

            writer.write("is>200KB");
            writer.append(';');
            writer.write("Byte");
            writer.append(';');

            writer.write("width");
            writer.append(';');
            writer.write("alt");
            writer.append(';');
            writer.write("src");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
