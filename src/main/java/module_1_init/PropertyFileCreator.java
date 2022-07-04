package module_1_init;

import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@Log4j2
class PropertyFileCreator {

    public void create(String srcUrl, String isCheckAllPages, PackageCreator packageCreator, String pathToUrls) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        try (OutputStream fos = new FileOutputStream("src/main/resources/config.properties")) {
            Properties prop = new PropertyAlteredOrder();

            prop.setProperty("srcUrl", srcUrl);
            prop.setProperty("isCheckAllPages", isCheckAllPages);
            prop.setProperty("srcPathToUrls", pathToUrls);

            prop.setProperty("srcPackageName", packageCreator.getPathToSrcDir());
            prop.setProperty("redirectPackageName", packageCreator.getPathToRedirectDir());
            prop.setProperty("hrefPackageName", packageCreator.getPathToHrefDir());
            prop.setProperty("pagePackageName", packageCreator.getPathToSrcHtmlPagesDir());
            prop.setProperty("reportPackageName", packageCreator.getPathToReportDir());

            prop.setProperty("robotsTxtFileName", "/srcRobotsTxt.txt");
            prop.setProperty("siteMapXmlFileName", "/srcSitemapXml");
            prop.setProperty("imgPage404FileName", "/srcImgPage404.png");
            prop.setProperty("reportRedirectFileName", "/report_redirect.csv");

            prop.setProperty("hrefFromSitemapXmlFileName", "/href_fromSitemapXml.txt");
            prop.setProperty("hrefFromSrcFileName", "/href_fromSrcFile.txt");
            prop.setProperty("hrefInternalFileName", "/href_internal.txt");
            prop.setProperty("reportSiteMapFileName", "/href_siteMap.txt");

            prop.setProperty("reportSubdomainFileName", "/report_subDomain.txt");
            prop.setProperty("reportGeneralFileName", "/reportGeneral.csv");
            prop.setProperty("reportHeadFileName", "/reportHead.csv");
            prop.setProperty("reportBalanceFileName", "/balance.csv");

            prop.store(fos, null);
            log.debug("< CREATED FILE = resources/config.properties");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
