package com.ylp.update;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 模拟浏览器向url发送请求并接收返回的html数据。
 */
public class ReadResponseData {
    // 使用Jsoup从url中获取字符串(/*超时出错，未使用，可能原因是服务器*/)。
    public String getResponseDataBy(String url) throws IOException {
        try{
            Connection.Response response = Jsoup.connect(url).execute();
            response.charset("gb2312");
            String str = response.body();
            return str;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    // 通过Java原生HttpURLConnection从url中读取数据。
    public String getResponseDataByWithHttpClient(String url){
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setReadTimeout(80000);
            conn.setConnectTimeout(80000);
            conn.setInstanceFollowRedirects(false);
            // 设置代理，不设置好像会超时。
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "gb2312"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null){
                    buffer.append(line);
                }
                String result = buffer.toString();
                return result;
            }else {
                System.out.println("链接："+url+"访问失败");
                return "";
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("在页面："+url+"当中读取数据错误");
            return "";
        }
    }

}
