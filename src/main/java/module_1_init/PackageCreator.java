package module_1_init;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Data
class PackageCreator {
    private String pathToSeoDir = "C://page404info";
    private String pathToSrcDir, pathToSrcHtmlPagesDir, pathToRedirectDir, pathToHrefDir, pathToReportDir;


    public void create(String url) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        createMainDir();
        createSrcDir(url);
        createRedirectDir();
        createHrefDir();
        createSrcHtmlPagesDir();
        createReportDir();
    }


    private void createMainDir() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            if (Files.notExists(Paths.get(pathToSeoDir))) {
                Files.createDirectory(Paths.get(pathToSeoDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }

    private void createSrcDir(String url) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            pathToSrcDir = pathToSeoDir + "/" + getSrcPackageName(url);
            if (Files.notExists(Paths.get(pathToSrcDir))) {
                Files.createDirectory(Paths.get(pathToSrcDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }

    private void createRedirectDir() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            pathToRedirectDir = pathToSrcDir + "/redirect";
            if (Files.notExists(Paths.get(pathToRedirectDir))) {
                Files.createDirectory(Paths.get(pathToRedirectDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }

    private void createSrcHtmlPagesDir() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            pathToSrcHtmlPagesDir = pathToSrcDir + "/srcHtmlPages";
            if (Files.notExists(Paths.get(pathToSrcHtmlPagesDir))) {
                Files.createDirectory(Paths.get(pathToSrcHtmlPagesDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }

    private void createHrefDir() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            pathToHrefDir = pathToSrcDir + "/href";
            if (Files.notExists(Paths.get(pathToHrefDir))) {
                Files.createDirectory(Paths.get(pathToHrefDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }

    private void createReportDir() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        try {
            pathToReportDir = pathToSrcDir + "/_reports";
            if (Files.notExists(Paths.get(pathToReportDir))) {
                Files.createDirectory(Paths.get(pathToReportDir));
            }
        } catch (IOException e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        } catch (Exception e) {
            log.error(new Exception().getStackTrace()[0].getMethodName() + " " + e);
        }
    }


    private String getSrcPackageName(String url) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String body = "";
        try {
            body = url.split("://")[1].trim();
        } catch (Exception e) {
            log.error(e + ": " + url);
        }

        String urlPart = body.split("/")[0]
                .replace('.', '_')
                .replace('/', '_')
                .toLowerCase();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuuMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String datePart = dtf.format(now);
        return urlPart + "_" + datePart;
    }
}
