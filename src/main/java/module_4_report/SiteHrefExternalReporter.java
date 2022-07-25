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
public class SiteHrefExternalReporter {
    private String pathToSrcFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_PAGE_HREF.getName();
    private String pathToResultFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_HREF_EXTERNAL.getName();

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

                if (!strArr[11].contains(PropertyConfigReader.getInstance().getSrcUrl())
                        && !strArr[11].equals("src")
                        && !strArr[11].equals("")
                        && !strArr[11].equals(" ")
                        && !strArr[11].startsWith("m")
                        && !strArr[11].startsWith("t")) {
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
