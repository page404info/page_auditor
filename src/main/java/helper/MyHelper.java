package helper;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

@Log4j2
public class MyHelper {

    public static boolean isFilePing(String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + pathToFile);
        File file = new File(pathToFile);
        return file.exists();
    }

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

    public static String deleteEndSlashFromHref(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + href);
        while (href.trim().endsWith("/")) {
            href = href.substring(0, href.length() - 1).trim();
        }
        return href;
    }

    public static String getHrefBody(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + href);
        String body = null;
        if (href.contains("://")) {
            try {
                body = href.split("://")[1].trim();
            } catch (Exception e) {
                log.error(e + ": " + href);
            }
        }
        return body;
    }

    public static void takeScreenshot(String url, String fileNamePng) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
//                "headless",
                "start-maximized",
                "--disable-gpu", "--disable-popup-blocking", "--disable-default-apps",
                "--test-type=browser",
                "--ignore-certificate-errors");

        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(fileNamePng));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
    }
}
