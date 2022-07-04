package helper;

import lombok.extern.log4j.Log4j2;

import java.io.File;

import static io.restassured.RestAssured.given;

@Log4j2
public class Utils {

    public static boolean isHrefPing(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + href);
        boolean isPing = false;
        int statusCode;

        try {
            statusCode = given().when().get(href).getStatusCode();
            if (statusCode == 200) {
                isPing = true;
            } else {
                log.error("status code is: " + statusCode + " " + href);
            }
        } catch (Exception e) {
            log.error(e + " " + href);
        }
        return isPing;
    }

    public static boolean isFilePing(String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + pathToFile);
        File file = new File(pathToFile);
        return file.exists();
    }

    public static String deleteEndSlash(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + href);
        while (href.trim().endsWith("/")) {
            href = href.substring(0, href.length() - 1).trim();
        }
        return href;
    }
}
