import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtchen.util.Request;
import com.jtchen.util.loadUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import static com.jtchen.util.CheckUtil.checkEnv;

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
    public void putCoinTest() throws Exception{
        var user = checkEnv();
        assert user != null;
        Request.setCookie(user.getCookie());

        var data = new JSONObject();
        data.put("aid", "586410702");
        data.put("multiply", "1");
        data.put("select_like", "0");
        data.put("cross_domain", "true");
        data.put("csrf", user.getCookie().getBili_jct());

        /*var res = Request.post("https://api.bilibili.com/x/web-interface/coin/add", data);*/

        var request = RequestBuilder.create(HttpPost.METHOD_NAME)
                .addHeader("connection", "keep-alive")
                .addHeader("referer", "https://www.bilibili.com/")
                .addHeader("User-Agent", loadUtil.getConnectProperties().getProperty("User-Agent"))
                .addHeader("Cookie", user.getCookie().toString())
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("charset", "UTF-8")
                .setUri("https://api.bilibili.com/x/web-interface/coin/add")
                .addParameters(getPairList(data))
                .build();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpResponse resp = client.execute(request);
            HttpEntity entity = resp.getEntity();
            String respContent = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            var j =  JSON.parseObject(respContent);

            System.out.println(j);
        } catch (Exception e) {

        }
    }

    public static NameValuePair[] getPairList(JSONObject pJson) {
        return pJson.entrySet().parallelStream().map(CoinsTest::getNameValuePair).toArray(NameValuePair[]::new);
    }

    private static NameValuePair getNameValuePair(Map.Entry<String, Object> entry) {
        return new BasicNameValuePair(entry.getKey(), (String) entry.getValue());
    }
}
