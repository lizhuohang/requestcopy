package com.lzh.monitor.requestcopy.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class TestHttpRequestUtil {
    private static final transient Logger logger = Logger.getLogger(TestHttpRequestUtil.class);


    public static final int POST_TYPE = 1;
    public static final int GET_TYPE = 2;

    public static boolean testHttpRequest(String url, Map<String, String> params, final int httptype) {
        URL u = null;
        HttpURLConnection con = null;
        StringBuffer sb = new StringBuffer();
        String paramstr = "";
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
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
                logger.info("Request failed , the http code is not 200 : " + con.getResponseCode());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Request failed , exception occurred");
            return false;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        logger.info("Request SUCCESS , httpurl : " + url + " , httpparams : " + paramstr + " , httpmethod : " + httptype + ".....success!");

        return true;
    }


    public static boolean testGet(String url, String param) {
        try {
            HttpClient client = new HttpClient();
            String request_str = url;
            if (StringUtils.isNotBlank(param)){
                request_str += "?" + param;
            }
            GetMethod get = new GetMethod(request_str);
            get.releaseConnection();
            client.executeMethod(get);
            if (get.getStatusCode() != HttpURLConnection.HTTP_OK){
                logger.info("GET FAILED , respond code is not 200 , geturl : " + url + " , getparams : " + param + "respond code : " + get.getStatusCode() );
                return false;
            }
        } catch (Exception e) {
            logger.info("GET FAILED , exception occurred , get url error ,geturl : " + url + " , getparams : " + param);
            return false;
        }
        logger.info("GET SUCCESS , geturl : " + url + " , getparams : " + param );
        return true;
    }
}
