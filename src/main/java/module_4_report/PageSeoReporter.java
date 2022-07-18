package module_4_report;

import helper.FileName;
import helper.PropertyConfigReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
@Data
public class PageSeoReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_SEO.getName();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        for (Page page : pages) {
            try {
                int isNotUnique = 0, isTitleMore60Char = 0, isDescriptiomMore160Char = 0;
                if (page.getTitle().equals(page.getDescription())
                        || page.getTitle().equals(page.getH1())
                        || page.getDescription().equals(page.getH1())
                ) {
                    isNotUnique = 1;
                }
                if (page.getTitle().length() > 60) {
                    isTitleMore60Char = 1;
                }
                if (page.getDescription().length() > 160) {
                    isDescriptiomMore160Char = 1;
                }

                FileWriter writer = new FileWriter(pathToReport, true);

                writer.write(page.getPageName() + "");
                writer.append(';');

                writer.write(isNotUnique + "");
                writer.append(';');
                writer.write(isTitleMore60Char + "");
                writer.append(';');
                writer.write(isDescriptiomMore160Char + "");
                writer.append(';');

                writer.write(page.getTitle().length() + "");
                writer.append(';');
                writer.write(page.getDescription().length() + "");
                writer.append(';');
                writer.write(page.getH1().length() + "");
                writer.append(';');

                writer.write(page.getTitle() + "");
                writer.append(';');
                writer.write(page.getDescription() + "");
                writer.append(';');
                writer.write(page.getH1() + "");

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

            writer.write("isNotUnique");
            writer.append(';');

            writer.write("isTitle>60Char");
            writer.append(';');
            writer.write("isDescription>160Char");
            writer.append(';');

            writer.write("titleLength");
            writer.append(';');
            writer.write("descriptionLength");
            writer.append(';');
            writer.write("h1Length");
            writer.append(';');

            writer.write("title");
            writer.append(';');
            writer.write("description");
            writer.append(';');
            writer.write("h1");
            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
