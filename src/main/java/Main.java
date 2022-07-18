import lombok.extern.log4j.Log4j2;
import module_1_init.Starter;
import module_2_crawler.CrawlerWeb;
import module_3_parser.PageParser;
import module_4_report.ReportCreator;

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        new Starter().start();
        new CrawlerWeb().search();
        new PageParser().parse();
        new ReportCreator().create();
    }
}