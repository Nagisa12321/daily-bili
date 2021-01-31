import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 0:23
 */
public class GetTest {
    public static void main(String[] args) {
        try {

            String bili_jct = System.getenv("BILI_JCT");
            String SESSDATA = System.getenv("SESSDATA");
            String DedeUserID = System.getenv("DedeUserID");

            URL u = new URL("https://api.bilibili.com/x/web-interface/nav");
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            // 请求头
            huc.setRequestProperty("Referer", "https://www.bilibili.com/");
            huc.setRequestProperty("Connection", "keep-alive");
            huc.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.53");
            huc.setRequestProperty("Cookie", " DedeUserID=162028484; SESSDATA=8d9463b1%2C1619348642%2C00a0d*a1; bili_jct=54bb0ce136c960aab187d6f8ab204f58; DedeUserID__ckMd5=349c47508db7c173; ");
            /*huc.setRequestProperty("Cookie", "DedeUserID=" + DedeUserID
                    + "; SESSDATA=" + SESSDATA
                    + "; bili_jct=" + bili_jct + ";");*/

            int responseCode = huc.getResponseCode();

            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = huc.getInputStream();
                //将响应流转换成字符串
                String result = new Scanner(inputStream).useDelimiter("\\Z").next();//将流转换为字符串。
                JSONObject jsonObject = JSONObject.parseObject(result);

                JSONObject data = jsonObject.getJSONObject("data");

                System.out.println(data);

                System.out.println();

                /* 用户名 */
                System.out.println("用户名:" + data.get("uname"));
                /* 账户的uid */
                System.out.println("uid: " + data.get("mid"));
                /* vip类型 */
                System.out.println("VIP类型: " + data.get("vipType"));
                /* 硬币数 */
                System.out.println("硬币数量" + data.get("money"));
                /* 经验 */
                JSONObject level_info = data.getJSONObject("level_info");
                int current_exp = (int) level_info.get("current_exp");
                int next_exp = (int) level_info.get("next_exp");
                System.out.println("经验: " + current_exp);
                /* 大会员状态 */
                JSONObject vip_label = data.getJSONObject("vip_label");
                System.out.println("大会员类型: " + vip_label.get("text"));
                /* 钱包B币卷余额 */
                JSONObject wallet = data.getJSONObject("wallet");
                System.out.println("钱包余额: " + wallet.get("bcoin_balance"));
                /* 升级到下一级所需要的经验 */
                System.out.println("升级到下一级所需要的经验: " + (next_exp - current_exp));
                /* 获取当前的等级 */
                System.out.println("当前等级: " + level_info.get("current_level"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
