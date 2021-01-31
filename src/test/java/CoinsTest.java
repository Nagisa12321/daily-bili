import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/1/28 22:36
 */
public class CoinsTest {
    public static void main(String[] args) {
        try {
            String bili_jct = System.getenv("BILI_JCT");
            String SESSDATA = System.getenv("SESSDATA");
            String DedeUserID = System.getenv("DedeUserID");


            URL u = new URL("https://account.bilibili.com/home/reward");
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            // 请求头
            huc.setRequestProperty("Referer", "https://www.bilibili.com/");
            huc.setRequestProperty("Connection", "keep-alive");
            huc.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.53");
            huc.setRequestProperty("Cookie", "DedeUserID=" + DedeUserID
                    + "; SESSDATA=" + SESSDATA
                    + "; bili_jct=" + bili_jct + ";");
            int responseCode = huc.getResponseCode();

            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = huc.getInputStream();
                //将响应流转换成字符串
                String result = new Scanner(inputStream).useDelimiter("\\Z").next();//将流转换为字符串。
                JSONObject jsonObject = JSONObject.parseObject(result);
                System.out.println(jsonObject);

                JSONObject object = jsonObject.getJSONObject("data");

                /* 获取经验信息, 进而获取投币数量 */
                int exp = (int) object.get("coins_av");
                System.out.println("今日投币经验: " + exp + " 因此还需投币的数量: " + (exp - 50) / 5);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void putCoinTest() {

    }
}
