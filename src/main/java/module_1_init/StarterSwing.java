package module_1_init;

import helper.MyHelper;
import helper.PropertyConfigReader;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.util.Scanner;

@Log4j2
public class StarterSwing {
    private String srcUrl, pathToSrcHref = "";
    private boolean isCheckAllPages;

    public void start() {
        inputParams();
        createConfigProperty();
        JOptionPane.showMessageDialog(null, "Start " + PropertyConfigReader.getInstance().getSrcUrl());
    }

    private void inputParams() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        do {
            srcUrl = inputSrcUrl();
        } while (!MyHelper.isHrefPing(srcUrl));

        log.info(">>> START checking = " + srcUrl);

        isCheckAllPages = isCheckAllPages();

        if (isCheckAllPages && isPageFile()) {
            do {
                pathToSrcHref = inputPathToSrcFile();
            } while (!MyHelper.isFilePing(pathToSrcHref));
            log.info("path to src file = " + pathToSrcHref);
        }
    }

    private void createConfigProperty() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        PackageCreator packageCreator = new PackageCreator();

        packageCreator.create(srcUrl);
        new PropertyConfigCreator().create(srcUrl, isCheckAllPages + "", packageCreator, pathToSrcHref);

        log.info("Config property CREATED");
    }


    private String inputSrcUrl() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String userInput = JOptionPane.showInputDialog("Enter url");
        return MyHelper.deleteEndSlashFromHref(userInput.toLowerCase());
    }

    private boolean isCheckAllPages() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String userInput = JOptionPane.showInputDialog("Check all pages (y/n)");
        String answer = userInput.toLowerCase();

        boolean result = false;
        if (answer.equals("y")) {
            result = true;
        }
        return result;
    }

    private boolean isPageFile() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String userInput = JOptionPane.showInputDialog("Have a file with links (y/n)");
        String answer = userInput.toLowerCase();

        boolean result = false;
        if (answer.equals("y")) {
            result = true;
        }
        return result;
    }

    private String inputPathToSrcFile() {
        log.debug(new Exception().getStackTrace()[0].getMethodName());
        String userInput = JOptionPane.showInputDialog("Enter path to file");
        return userInput.toLowerCase();
    }

}
