package helper;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class PropertyConfigReader {
    private static PropertyConfigReader instance = null;
    private String pathToFile = FileName.CONFIG.getName();
    private String srcUrl, isCheckAllPages, srcFile, srcDir;


    public static PropertyConfigReader getInstance() {
        if (instance == null) {
            instance = new PropertyConfigReader();
        }
        return instance;
    }

    private PropertyConfigReader() {
        readProperty();
    }

    private void readProperty() {
        try (InputStream fis = new FileInputStream(pathToFile)) {
            Properties prop = new Properties();
            prop.load(fis);

            srcUrl = prop.getProperty("srcUrl");
            isCheckAllPages = prop.getProperty("isCheckAllPages");
            srcFile = prop.getProperty("srcFile");
            srcDir = prop.getProperty("srcDir");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

