package module_4_report;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
@Data
public class PageSemanticReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_SEMANTIC.getName();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        for (Page page : pages) {
            try {
                FileWriter writer = new FileWriter(pathToReport, true);

                writer.write(page.getPageName() + "");
                writer.append(';');

                writer.write(page.getSemanticCounter().getHeaderCount() + "");
                writer.append(';');
                writer.write(page.getSemanticCounter().getFooterCount() + "");
                writer.append(';');
                writer.write(page.getSemanticCounter().getMainCount() + "");
                writer.append(';');
                writer.write(page.getSemanticCounter().getNavCount() + "");
                writer.append(';');

                writer.write(page.getSemanticCounter().getArticleCount() + "");
                writer.append(';');
                writer.write(page.getSemanticCounter().getSectionCount() + "");
                writer.append(';');
                writer.write(page.getSemanticCounter().getAsideCount() + "");

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

            writer.write("header");
            writer.append(';');
            writer.write("footer");
            writer.append(';');
            writer.write("main");
            writer.append(';');
            writer.write("nav");
            writer.append(';');

            writer.write("article");
            writer.append(';');
            writer.write("section");
            writer.append(';');
            writer.write("aside");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
