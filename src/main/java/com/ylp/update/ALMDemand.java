package com.ylp.update;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
/**
 * 利用head和body获取数据。
 */
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ALMDemand {

    private List<String> cookies;
    private String userName = "zkrkfwb";
    private String password  = "zkrkfwb";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0";
    private final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private final String CONT_TYPE = "application/json; charset=utf-8";
    private final String REFERER = "http://alm.ab.com/DevSuite/MultiTabHome.aspx?UserID=2916&SessionID=73612182&LanguageID=2&RuntimeKey=";
    private final String LANGUAGE = "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2";
    private final String Accept_Encoding = "gzip, deflate";
    private final String COOKIE = "DevPlanPreference=[expires=Tue, 31 Dec 2999 16:00:00 GMT|]; loginid=2916; langid=2; KendoTheme=bootstrap; DevSuiteWebUserData=jku1L6snwMSuvd2n4lMXbEY7OYUR8nQ9GMGkKeJcGD4gyt4wBPLA6P3MXGPinnVNjWq9/6aQgEw=; txdevtrackltmsid=60C70FF7E69B9C76DF6532CF7BA9C8AD; DSCSID=AD739B0E075A78EDF89DE93FE201D022; DevSpecSID=0s04re2yv3fxputhzxxhyutw; KWWeb=; ChangeSummaryID=1075735; txdtktmstring=F10B339EB7D4DBE70285E26274C6A461s";
    protected static Logger logger = LogManager.getLogger();
    public String getAllType(String url,String param) throws Exception {
        ALMDemand almDemand = new ALMDemand();
        HttpURLConnection conn = almDemand.getConn(url);
        conn.setRequestProperty("Content-Length", Integer.toString(param.length()));
        //conn.setRequestProperty("Cookie", COOKIE);
        AutoLoadDemand autoLoadDemand = new AutoLoadDemand();
        try {
            autoLoadDemand.login();
            List<String> cookies = autoLoadDemand.getCookies();
            for (int i = 0;i<cookies.size();i++){
                logger.info(cookies);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
        wr.write(param);
        wr.flush();
        wr.close();

        String result = "";
        int responseCode = conn.getResponseCode();
        result = ""+ responseCode + conn.getResponseMessage();
        if(responseCode == HttpURLConnection.HTTP_OK) {
            result = "";
            //定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        return  result;

    }
    // 构造header
    public HttpURLConnection getConn(String url) throws IOException {
        HttpURLConnection conn;
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "alm.ab.com");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                ACCEPT);
        conn.setRequestProperty("Accept-Language", LANGUAGE);
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", REFERER);
        conn.setRequestProperty("Content-Type", CONT_TYPE);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }
    public String getJsonParam(HashMap<String,String> map){
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }

}
