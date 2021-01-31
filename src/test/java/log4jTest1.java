import org.apache.log4j.Logger;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:52
 */
public class log4jTest1 {

    public static Logger logger = Logger.getLogger(log4jTest1.class);
    public static void main(String[] args) {
        logger.info("123");
    }
}
