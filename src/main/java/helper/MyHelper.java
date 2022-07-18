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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

    public static String deleteEndAnchorFromHref(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName() + ": " + href);
        while (href.trim().endsWith("#")) {
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

    public static boolean isInnerHref(String href) {
        boolean result = false;
        if (href.contains(PropertyConfigReader.getInstance().getSrcUrl()) && isHtmlPage(href)) {
            result = true;
        }

        return result;
    }

    public static boolean isHtmlPage(String href) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        boolean result = true;
        href = href.toLowerCase();
        if (href.equals(PropertyConfigReader.getInstance().getSrcUrl() + "/index.html")
                || href.contains("/download")
                || href.contains("/file")
                || href.contains("/javascript")
                || href.contains("/email-protection")
                || href.contains("/login")
                || href.contains(".key")
                || href.endsWith(".pdf")
                || href.endsWith(".ppt")
                || href.endsWith(".doc")
                || href.endsWith(".docx")
                || href.endsWith(".xls")
                || href.endsWith(".xlsx")
                || href.endsWith(".png")
                || href.endsWith(".jpg")
                || href.endsWith(".jpeg")
                || href.endsWith(".svg")
                || href.endsWith(".tif")
                || href.endsWith(".rtf")
                || href.endsWith(".rar")
                || href.endsWith(".zip")
                || href.endsWith(".7z")
                || href.endsWith(".page")
                || href.endsWith(".odt")
                || href.endsWith(".mp4")
                || href.endsWith(".webp")
        ) {
            result = false;
        }

        return result;
    }

    public static void writeHrefToFile(Set<String> urls, String pathToSave) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        try {
            FileWriter writer = new FileWriter(pathToSave, false);
            for (String s : urls) {
                writer.write(s.replace(" ", "%20"));
                writer.append('\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Href SAVED TO FILE = " + pathToSave);
    }

    public static void writeJsonToFile(String string, String pathToSave) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        try {
            FileWriter writer = new FileWriter(pathToSave, false);
            writer.write(string);
            writer.append('\n');
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Json SAVED TO FILE = " + pathToSave);
    }

    public static List<String> readHrefFromFile(String pathToFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        List<String> results = new ArrayList<>();

        if (!pathToFile.isEmpty()) {
            try {
                File file = new File(pathToFile);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String href = scanner.nextLine();
                    results.add(href);
                }
                log.debug(">> read from file = " + pathToFile);
                scanner.close();
            } catch (FileNotFoundException e) {
                log.error(e);
            }
        }

        return results;
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

    public static String getProtocol(String href) {
        return href.split(":")[0];
    }

    public static int isHrefContains_(String href) {
        int result = 0;
        if (href.contains("_")) {
            result = 1;
        }
        return result;
    }

    public static int isHrefContainsPercent(String href) {
        int result = 0;
        if (href.contains("%")) {
            result = 1;
        }
        return result;
    }

    public static int isHrefLengthMore120(String href) {
        int result = 0;
        if (href.length() > 120) {
            result = 1;
        }
        return result;
    }

    public static int isHrefContainsScript(String href) {
        int result = 0;
        if (href.contains("<script")
                || href.contains("<noscript")
                || href.contains("javascript:")
        ) {
            result = 1;
        }
        return result;
    }

    public static int isHrefContainsParams(String href) {
        int result = 0;
        if (href.contains("/?")) {
            result = 1;
        }
        return result;
    }

}
