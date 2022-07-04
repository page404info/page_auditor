package module_1_init;

import helper.Utils;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

@Log4j2
public class ProgramInit implements ProgramInitIF {
    private String srcUrl, pathToSrcHref="";
    private boolean isCheckAllPages;

    @Override
    public void inputParams() {
        do {
            srcUrl = inputSrcUrl();
        } while (!Utils.isHrefPing(srcUrl));

        isCheckAllPages = isCheckAllPages();

        if (isPageFile()) {
            do {
                pathToSrcHref = inputPathToSrcHref();
            } while (!Utils.isFilePing(pathToSrcHref));
        }

        log.info("< user params checked".toUpperCase());
    }

    @Override
    public void createConfigProperty() {
        PackageCreator packageCreator = new PackageCreator();

        packageCreator.create(srcUrl);
        new PropertyFileCreator().create(srcUrl, isCheckAllPages+"", packageCreator, pathToSrcHref);

        log.info("< config property created".toUpperCase());
    }


    private String inputSrcUrl() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Scanner sc = new Scanner(System.in);
        System.out.print(">> Input url: ");
        return Utils.deleteEndSlash(sc.nextLine().toLowerCase());
    }

    private boolean isCheckAllPages() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Scanner sc = new Scanner(System.in);
        System.out.print(">> Check all pages (y/n)?: ");
        String answer = sc.nextLine().toLowerCase();

        boolean result = false;
        if (answer.equals("y")) {
            result = true;
        }
        return result;
    }

    private boolean isPageFile() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Scanner sc = new Scanner(System.in);
        System.out.print(">> Have a file with links (y/n)?: ");
        String answer = sc.nextLine().toLowerCase();

        boolean result = false;
        if (answer.equals("y")) {
            result = true;
        }
        return result;
    }

    private String inputPathToSrcHref() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        Scanner sc = new Scanner(System.in);
        System.out.print(">> Input path to file: ");
        return sc.nextLine().toLowerCase();
    }

}
