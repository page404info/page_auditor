package module_4_report;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import helper.FileName;
import helper.PropertyConfigReader;
import helper.PropertyObjectFileCountReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import module_3_parser.objects.Page;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@Log4j2
@Data
public class ReportCreator {

    public void create() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        int fileCount = Integer.parseInt(PropertyObjectFileCountReader.getInstance().getObjectFileCount());

        new RedirectChecker().check();
        new SitemapCreator().create();

        PageStructureReporter pageStructureReporter = new PageStructureReporter();
        PageImgReporter pageImgReporter = new PageImgReporter();
        PageHrefReporter pageHrefReporter = new PageHrefReporter();
        PageSeoReporter pageSeoReporter = new PageSeoReporter();
        PageLoadReporter pageLoadReporter = new PageLoadReporter();

        pageStructureReporter.createReportHeader();
        pageImgReporter.createReportHeader();
        pageHrefReporter.createReportHeader();
        pageSeoReporter.createReportHeader();
        pageLoadReporter.createReportHeader();

        for (int i = 1; i < fileCount + 1; i++) {
            String pathToJsonFile = PropertyConfigReader.getInstance().getSrcDir()
                    + "/_" + i + FileName.PAGE_OBJECT.getName();
            List<Page> pages = getPageListFromJsonFile(pathToJsonFile);

            pageStructureReporter.createReportBody(pages);
            pageSeoReporter.createReportBody(pages);
            pageImgReporter.createReportBody(pages);
            pageLoadReporter.createReportBody(pages);
            pageHrefReporter.createReportBody(pages);

            pages.clear();
        }

        new SiteContactReporter().create();
        new SiteHrefExternalReporter().create();
        log.info("CREATED ALL REPORTS: " + PropertyConfigReader.getInstance().getSrcDir());
    }

    private List<Page> getPageListFromJsonFile(String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        Type type = new TypeToken<List<Page>>() {
        }.getType();
        List<Page> pages = gson.fromJson(br, type);
        return pages;
    }

}
