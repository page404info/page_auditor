package module_4_report;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import helper.FileName;
import helper.PropertyConfigReader;
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
    private String pathToJsonFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.PAGE_OBJECT.getName();
    private List<Page> pages = getPageListFromJsonFile(pathToJsonFile);

    public void create(){
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        new PageStructureReporter().create(pages);
        new PageImgReporter().create(pages);
        new PageHrefReporter().create(pages);
        new PageSeoReporter().create(pages);
        new PageLoadReporter().create(pages);
        new RedirectChecker().check();
        new SitemapCreator().create();
    }

    private List<Page> getPageListFromJsonFile(String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(pathToFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<Page>>(){}.getType();
        List<Page> pages = gson.fromJson(br, type);
        return pages;
    }

}
