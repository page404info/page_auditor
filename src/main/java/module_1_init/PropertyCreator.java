package module_1_init;

import helper.FileName;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@Log4j2
class PropertyCreator {
    private String pathToFile = FileName.CONFIG.getName();

    public void create(String srcUrl, String isCheckAllPages, PackageCreator packageCreator, String srcFile) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        try (OutputStream fos = new FileOutputStream(pathToFile)) {
            Properties prop = new PropertyAlteredOrder();

            prop.setProperty("srcUrl", srcUrl);
            prop.setProperty("isCheckAllPages", isCheckAllPages);
            prop.setProperty("srcFile", srcFile);
            prop.setProperty("srcDir", packageCreator.getPathToSrcDir());

            prop.store(fos, null);
            log.debug("Config property SAVED TO FILE = resources/config.properties");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
