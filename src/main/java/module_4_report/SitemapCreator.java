package module_4_report;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
public class SitemapCreator {
    private String pathToInnerHrefFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.HREF_INTERNAL.getName();
    private String pathToSitemapFile = PropertyConfigReader.getInstance().getSrcDir() + FileName.REPORT_SITEMAP.getName();

    public void create() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> urlList = MyHelper.readHrefFromFile(pathToInnerHrefFile);
        try {
            FileWriter writer = new FileWriter(pathToSitemapFile, false);

            for (int i = 1; i < urlList.size(); i++) {
                int length = urlList.get(i).split("/").length - 3;
                if (!urlList.get(i).contains("/?") && urlList.get(i).contains("?")) {
                    length++;
                }

                if (urlList.get(i).length() != PropertyConfigReader.getInstance().getSrcUrl().length()) {
                    for (int j = 1; j < length; j++) {
                        writer.write("\t");
                    }
                    writer.write(urlList.get(i));
                    writer.append('\n');
                }
            }

            writer.close();
        } catch (IOException e) {
            log.error(e);
        }

        log.info("Sitemap SAVED TO FILE = " + pathToSitemapFile);
    }
}
