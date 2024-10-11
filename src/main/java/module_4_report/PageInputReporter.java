package module_4_report;

import helper.FileName;
import helper.PropertyConfigReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Button;
import module_3_parser.objects.Input;
import module_3_parser.objects.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
@Data
public class PageInputReporter {
    private String pathToReport = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_INPUT.getName();

    public void createReportBody(List<Page> pages) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        //Response response;
        //int pageCount = pages.size();

        for (Page page : pages) {
            try {
                FileWriter writer = new FileWriter(pathToReport, true);
                List<Input> items = page.getInputList();

                //System.out.println(pageCount + " : " + items.size() + " button report");
                //pageCount--;

                for (Input item : items) {
                    writer.write(page.getPageName() + "");
                    writer.append(';');
                    writer.write(item.getType() + "");
                    writer.append(';');
                    writer.write(item.getName() + "");
                    writer.append(';');
                    writer.write(item.getAriaLabel() + "");
                    writer.append(';');
                    writer.write(item.getTabIndex() + "");
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
            writer.write("type");
            writer.append(';');
            writer.write("name");
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

}
