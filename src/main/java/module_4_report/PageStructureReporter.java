package module_4_report;

import helper.FileName;
import helper.PropertyReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
@Data
public class PageStructureReporter {
    private String pathToReport = PropertyReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_STRUCTURE.getName();

    public void create(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        createReportHeader();
        createReportBody(pages);

        log.info("Page structure REPORT SAVED TO FILE = " + pathToReport);
    }

    private void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        for (Page page : pages) {
            try {
                FileWriter writer = new FileWriter(pathToReport, true);

                writer.write(page.getPageName());
                writer.append(';');

                writer.write(page.getCharset());
                writer.append(';');
                writer.write(page.getViewport());
                writer.append(';');

                writer.write(page.getElementCounter().getTitleCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getDescriptionCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getFaviconCount() + "");
                writer.append(';');

                writer.write(page.getElementCounter().getStyleCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getLinkCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getScriptCount() + "");
                writer.append(';');

                writer.write(page.getElementCounter().getH1Count() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getH2Count() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getH3Count() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getH4Count() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getH5Count() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getH6Count() + "");
                writer.append(';');

                writer.write(page.getElementCounter().getHrefCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getImgCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getInputFieldCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getButtonCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getTableCount() + "");
                writer.append(';');
                writer.write(page.getElementCounter().getCharCount() + "");

                writer.append('\n');
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

            writer.write("charset");
            writer.append(';');
            writer.write("viewport");
            writer.append(';');

            writer.write("title");
            writer.append(';');
            writer.write("description");
            writer.append(';');
            writer.write("favicon");
            writer.append(';');

            writer.write("style");
            writer.append(';');
            writer.write("link");
            writer.append(';');
            writer.write("script");
            writer.append(';');


            writer.write("h1");
            writer.append(';');
            writer.write("h2");
            writer.append(';');
            writer.write("h3");
            writer.append(';');
            writer.write("h4");
            writer.append(';');
            writer.write("h5");
            writer.append(';');
            writer.write("h6");
            writer.append(';');


            writer.write("href");
            writer.append(';');
            writer.write("img");
            writer.append(';');
            writer.write("input");
            writer.append(';');
            writer.write("button");
            writer.append(';');
            writer.write("table");
            writer.append(';');
            writer.write("char");

            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

}
