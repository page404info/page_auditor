package module_4_report;

import helper.FileName;
import helper.PropertyConfigReader;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Img;
import module_3_parser.objects.Page;
import module_3_parser.objects.Video;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
@Data
public class PageVideoReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_VIDEO.getName();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Response response;
        int pageCount = pages.size();

        for (Page page : pages) {
            try {
                FileWriter writer= new FileWriter(pathToReport, true);
                List<Video> items = page.getVideoList();

                System.out.println(pageCount + " : " + items.size() + " video report");
                pageCount--;

                for (Video item : items) {
                    int videoByte = -1;
                    String type = "unknown";
                    try {
                        response = given().when().get(item.getSrc());
                        videoByte = response.getBody().asByteArray().length;
                    } catch (Exception e) {
                        log.error(e + " " + item.getSrc());
                    }

                    String[] words = item.getSrc().split("\\.");
                    int wLength = words.length;
                    type = words[wLength - 1];

                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(items.size() + "");
                    writer.append(';');
                    writer.write(type + "");
                    writer.append(';');

                    writer.write(videoByte + "");
                    writer.append(';');
                    writer.write(videoByte/1024/1024 + "");
                    writer.append(';');

                    writer.write(item.getWidth() + "");
                    writer.append(';');
                    writer.write(item.getHeight() + "");
                    writer.append(';');
                    writer.write(item.getSrc() + "");
                    writer.append('\n');
                }
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
            writer.write("videoCount");
            writer.append(';');
            writer.write("type");
            writer.append(';');

            writer.write("Byte");
            writer.append(';');
            writer.write("MByte");
            writer.append(';');

            writer.write("width");
            writer.append(';');
            writer.write("height");
            writer.append(';');
            writer.write("src");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
