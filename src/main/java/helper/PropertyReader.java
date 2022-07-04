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
    private String propertyFilePath = "./src/main/resources/config.properties";

    private String srcUrl, isCheclAllPages, srcPathToUrls;
    private String srcPackageName, redirectPackageName, hrefPackageName, pagePackageName, reportPackageName;

    private String robotsTxtFileName, siteMapXmlFileName,
            imgPage404FileName, imgRobotsTxtFileName, imgSiteMapXmlFileName,
            reportRedirectFileName;

    private String hrefFromSitemapXmlFileName, hrefFromSrcFileName, hrefInternalFileName,
            reportSiteMapFileName, reportSubdomainFileName,
            reportGeneralFileName, reportHeadFileName, reportBalanceFileName;


    private PropertyReader() {
        readProperty();
    }


    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private void readProperty() {
        try (InputStream fis = new FileInputStream(propertyFilePath)) {
            Properties prop = new Properties();
            prop.load(fis);

            srcUrl = prop.getProperty("srcUrl");
            isCheclAllPages = prop.getProperty("isCheckAllPages");
            srcPathToUrls = prop.getProperty("srcPathToUrls");

            srcPackageName = prop.getProperty("srcPackageName");
            redirectPackageName = prop.getProperty("redirectPackageName");
            hrefPackageName = prop.getProperty("hrefPackageName");
            pagePackageName = prop.getProperty("pagePackageName");
            reportPackageName = prop.getProperty("reportPackageName");

            robotsTxtFileName = prop.getProperty("robotsTxtFileName");
            siteMapXmlFileName = prop.getProperty("siteMapXmlFileName");
            imgPage404FileName = prop.getProperty("imgPage404FileName");
            imgRobotsTxtFileName = prop.getProperty("imgRobotsTxtFileName");
            imgSiteMapXmlFileName = prop.getProperty("imgSiteMapXmlFileName");
            reportRedirectFileName = prop.getProperty("reportRedirectFileName");

            hrefInternalFileName = prop.getProperty("hrefInternalFileName");
            hrefFromSitemapXmlFileName = prop.getProperty("hrefFromSitemapXmlFileName");
            hrefFromSrcFileName = prop.getProperty("hrefFromSrcFileName");
            reportSiteMapFileName = prop.getProperty("reportSiteMapFileName");
            reportSubdomainFileName = prop.getProperty("reportSubdomainFileName");
            reportGeneralFileName = prop.getProperty("reportGeneralFileName");
            reportHeadFileName = prop.getProperty("reportHeadFileName");
            reportBalanceFileName = prop.getProperty("reportBalanceFileName");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

