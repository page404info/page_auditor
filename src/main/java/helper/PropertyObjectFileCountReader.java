package helper;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class PropertyObjectFileCountReader {
    private static PropertyObjectFileCountReader instance = null;
    private String pathToFile = FileName.OBJECT_FILE_COUNT.getName();
    private String objectFileCount;


    public static PropertyObjectFileCountReader getInstance() {
        if (instance == null) {
            instance = new PropertyObjectFileCountReader();
        }
        return instance;
    }

    private PropertyObjectFileCountReader() {
        readProperty();
    }

    private void readProperty() {
        try (InputStream fis = new FileInputStream(pathToFile)) {
            Properties prop = new Properties();
            prop.load(fis);

            objectFileCount = prop.getProperty("objectFileCount");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

