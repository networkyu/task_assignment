package com.ylp.update;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AutoLoadDemand {

    private List<String> cookies;
    private HttpURLConnection conn;
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0";
    private final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private final String CONT_TYPE = "application/x-www-form-urlencoded";
    private final String REFERER = "http://alm.ab.com/DevSuite/login.aspx?nodirect=1";
    private final String LANGUAGE = "";
    private String userName = "zkrkfwb";
    private String password  = "zkrkfwb";

    public static void main(String[] args) throws Exception {
    }
    public void login() throws Exception{
        String url = "http://alm.ab.com/DevSuite/Login.aspx";
        AutoLoadDemand http = new AutoLoadDemand();
        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());
        // 1. Send a "GET" request, so that you can extract the form's data.
        String page = http.GetPageContent(url);
        String postParams = http.getFormParams(page, http.userName, http.password);
        // 2. Construct above post's content and then send a POST request for
        // authentication
        http.sendPost(url, postParams);
    }
    private void sendPost(String url, String postParams) throws Exception {

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
        for (String cookie : this.cookies) {
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", REFERER);
        conn.setRequestProperty("Content-Type", CONT_TYPE);
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
        System.out.printf("content长度："+Integer.toString(postParams.length()));
        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
        wr.write(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.printf(conn.getResponseMessage());
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());

    }

    private String GetPageContent(String url) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // default is GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // act like a browser
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                ACCEPT);
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        if (cookies != null) {
            for (String cookie : this.cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Get the response cookies
        setCookies(conn.getHeaderFields().get("Set-Cookie"));

        return response.toString();

    }

    public String getFormParams(String html, String username, String password)
            throws UnsupportedEncodingException {

        System.out.println("Extracting form's data...");

        Document doc = Jsoup.parse(html);
//        18217231886
        // Google form id
        Element loginform = doc.getElementById("ctl01");
        Elements inputElements = loginform.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("tbUserName"))
                value = username;
            else if (key.equals("tbReserved"))
                value = password;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

    public List<String> getCookies() {
        if (cookies == null){
            // 登录
        }
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

}