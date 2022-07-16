package module_2_crawler;

import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class CrawlerFile {
    private String srcUrl = PropertyConfigReader.getInstance().getSrcUrl();
    private String pathToFile = PropertyConfigReader.getInstance().getSrcFile();

    public Set<String> search() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> items = MyHelper.readHrefFromFile(pathToFile);
        Set<String> results = new TreeSet<>();

        for (String item : items){
            if (isHrefValid(item)){
                results.add(srcUrl + item);
            }
        }

        return results;
    }

    private boolean isHrefValid(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        boolean result = false;

        if (href.startsWith("/")) {
            result = true;
        } else {
            log.error("href from srcFile does not start with '/' = " + href);
        }

        return result;
    }
}
