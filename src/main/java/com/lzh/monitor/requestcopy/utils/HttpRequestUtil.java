package com.lzh.monitor.requestcopy.utils;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class HttpRequestUtil {
    private static final transient Logger logger = Logger.getLogger(HttpRequestUtil.class);


    public static final int POST_TYPE = 1;
    public static final int GET_TYPE = 2;

    public static String httpRequest(String url, Map<String, String> params, final int httptype) {
        URL u = null;
        HttpURLConnection con = null;
        StringBuffer sb = new StringBuffer();
        String paramstr = "";
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            paramstr = sb.substring(0, sb.length() - 1);
        }

        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(httptype == POST_TYPE ? "POST" : "GET");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(3500);
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(paramstr);
            osw.flush();
            osw.close();

            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request failed , the http code is not 200 : " + con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                con.disconnect();
            }
            throw new RuntimeException("Request failed , exception occurred", e);
        }


        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        logger.info("httpurl : " + url + " , httpparams : " + paramstr + " , httpmethod : " + httptype + " , httpresult : " + buffer.toString());

        return buffer.toString();
    }


    public static void executeGet(String url, String param) {
        try {
            HttpClient client = new HttpClient();
            GetMethod get = new GetMethod(url + "?" + param);
            get.releaseConnection();
            client.executeMethod(get);
            String response = get.getResponseBodyAsString();
            //String response = new String(method.getResponseBodyAsString().getBytes("ISO-8859-1"));
            logger.info("geturl : " + url + " , getparams : " + param + " , getresult : " + get.getResponseBodyAsString());
        } catch (Exception e) {
            logger.info("get url error ,geturl : " + url + " , getparams : " + param);
        }
    }
}
