package com.jtchen;

import com.jtchen.domain.Config;
import com.jtchen.domain.User;
import com.jtchen.task.CoinTask;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static com.jtchen.util.CheckUtil.checkEnv;
import static com.jtchen.util.CheckUtil.checkUser;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/30 22:50
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);
        User user;
        if ((user = checkEnv()) == null) {
            logger.error("BILI_JCT, SESSDATA, DedeUserID没有配置");
            return;
        }
        logger.info("✔ 相关信息配置完善, 开始检查用户信息");
        if (!checkUser(user)) {
            logger.error("用户信息检查失败");
            return;
        }
        var config = Config.getInstance();
        logger.info("✔ 解析用户信息成功o(*￣▽￣*)ブ");
        logger.info("✔ 亲爱的 " + user.getUname() + "欢迎使用 daily-bili");
        logger.info("✔ 『当前硬币』 " + user.getMoney());
        logger.info("✔ 『当前等级』 " + user.getCurrentLevel());
        logger.info("✔ 『升级所需的经验』 " + user.getRemainingExp());

        logger.info("✔ 进入投币环节");
        var coinTask = new CoinTask(user, config);
        coinTask.run();
    }


}
