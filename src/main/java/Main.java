import lombok.extern.log4j.Log4j2;
import module_1_init.Starter;
import module_2_redirect.RedirectChecker;

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        new Starter().start();
        new RedirectChecker().check();

    }
}

/*
https://edata.gov.ua/
e:\@support\111.txt
*/