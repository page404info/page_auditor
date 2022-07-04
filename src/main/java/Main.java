import lombok.extern.log4j.Log4j2;
import module_1_init.ProgramInit;
import module_1_init.ProgramInitIF;

@Log4j2
public class Main {
    public static void main(String[] args) {
        log.debug(new Exception().getStackTrace()[0].getMethodName());

        ProgramInitIF init = new ProgramInit();
        init.inputParams();
        init.createConfigProperty();

    }
}

/*
https://edata.gov.ua
e:\@support\111.txt
*/