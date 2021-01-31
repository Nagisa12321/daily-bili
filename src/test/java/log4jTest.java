import org.apache.log4j.Logger;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 23:52
 */
public class log4jTest {
    public static Logger logger = Logger.getLogger(log4jTest.class);

    public static void main(String[] args) {
        // 很低的日志级别一般不会使用
        logger.trace("logger的trace级别");
        // 细粒度信息对调试应用非常有帮助，主要打印一些运行信息
        logger.debug("logger的debug级别");
        // 粗粒度突出强调应用程序的运行过程，打印感兴趣或者重要的信息
        logger.info("logger的info级别");
        // 表明会出现潜在错误的情形，有些信息不是错误信息，但也要给程序员一点提示
        logger.warn("logger的warn级别");
        // 指出虽然发生错误，但不影响运行，打印错误和异常信息，如果不想输出太多日志可以使用该级别
        logger.error("logger的error级别");
        // 每个严重的错误事件将会导致应用程序的退出，出现错误可以停止程序运行进行调试
        logger.fatal("logger的fatal级别");

    }
}
