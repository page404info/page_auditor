package helper;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class PropertyReader {
    private static PropertyReader instance = null;
    private String pathToFile = FileName.CONFIG.getName();
    private String srcUrl, isCheckAllPages, srcFile, srcDir;


    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private PropertyReader() {
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

