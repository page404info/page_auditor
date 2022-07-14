package module_4_report;

import helper.FileName;
import helper.MyHelper;
import helper.PropertyReader;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class RedirectChecker {
    private String srcUrl = PropertyReader.getInstance().getSrcUrl();
    private String pathToSave = PropertyReader.getInstance().getSrcDir() + FileName.REPORT_REDIRECT.getName();

    public void check() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        writeResult(getRedirectResults(), pathToSave);
    }

    private List<String> getRedirectResults() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> resultList = new ArrayList<>();
        List<String> prefixList = getPrefixListForRedirect();

        String propertyUrl = MyHelper.getHrefBody(srcUrl);
        if (propertyUrl.startsWith("www.")) {
            propertyUrl = propertyUrl.substring(4);
        }

        for (String s : prefixList) {
            String begin = s.split(";")[0];
            String end = s.split(";")[1];

            String verificationUrl = begin + propertyUrl + end;
            log.debug(">> " + verificationUrl);
            Response response = null;
            try {
                response = given().when().get(verificationUrl);
            } catch (Exception e) {
                log.error(e + ": " + verificationUrl);
            }

            int statusCode = 999;
            if (response != null) {
                statusCode = response.getStatusCode();
            }
            resultList.add(statusCode + ";" + verificationUrl);
        }
        return resultList;
    }

    private List<String> getPrefixListForRedirect() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> prefixList = new ArrayList<>();

        prefixList.add("http://; ");
        prefixList.add("http://;/");
        prefixList.add("http://www.; ");
        prefixList.add("http://www.;/");
        prefixList.add("http://;/index.html");
        prefixList.add("http://www.;/index.html");
        prefixList.add("http://;/index.php");
        prefixList.add("http://www.;/index.php");

        prefixList.add("https://; ");
        prefixList.add("https://;/");
        prefixList.add("https://www.; ");
        prefixList.add("https://www.;/");
        prefixList.add("https://;/index.html");
        prefixList.add("https://www.;/index.html");
        prefixList.add("https://;/index.php");
        prefixList.add("https://www.;/index.php");

        prefixList.add("http://;/robots.txt");
        prefixList.add("http://;/sitemap.xml");
        prefixList.add("https://;/robots.txt");
        prefixList.add("https://;/sitemap.xml");

        return prefixList;
    }

    private void writeResult(List<String> results, String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            FileWriter writer = new FileWriter(pathToFile, false);
            writer.write("statusCode");
            writer.append(';');
            writer.write("href");
            writer.append('\n');

            for (String s : results) {
                writer.write(s);
                writer.append('\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Redirect REPORT SAVED TO FILE = " + pathToFile);
    }


}
