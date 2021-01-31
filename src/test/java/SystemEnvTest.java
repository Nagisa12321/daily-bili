/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/26 22:00
 */
public class SystemEnvTest {
    public static void main(String[] args) {
        String java_home = System.getenv("JAVA_HOME");
        System.out.println(java_home);

        for (var entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
