package module_4_report;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
@Data
public class SiteCssInnerReporter {
    private String pathToSrcFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_HREF.getName();
    private String pathToResultFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_CSS_INNER.getName();

    public void create() {
        Set<String> resultList = filterHref();
        if(resultList.size() > 0) {
            MyHelper.writeHrefToFile(resultList, pathToResultFile);
        }
    }

    private Set<String> filterHref() {
        Set<String> resultList = new TreeSet<>();

        try {
            File file = new File(pathToSrcFile);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] strArr = str.split(";");

                if (strArr[4].equals("1") &&
                        (strArr[2].equals("link") || strArr[2].equals("script"))) {
                    resultList.add(strArr[11]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            log.error(e);
        }

        return resultList;
    }
}
