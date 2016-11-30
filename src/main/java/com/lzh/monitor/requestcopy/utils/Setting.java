package com.lzh.monitor.requestcopy.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by lizhuohang on 30/11/2016.
 */
public class Setting {
    private static final transient Logger logger = Logger.getLogger(Setting.class);
    public static Properties SETTINGS;

    static {
        init();
    }

    private static void init() {
        String cachefile = "/opt/requestcopy/setting.properties";
        SETTINGS = new Properties();
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(cachefile));
            logger.info(stream.toString());
            SETTINGS.load(stream);
        } catch (IOException e) {
            logger.error("初始化配置失败！", e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public static String getString(String key , String defaultstr) {
        String resultStr = "";
        try {
            resultStr = SETTINGS.getProperty(key);
            if (StringUtils.isBlank(resultStr)){
                return defaultstr;
            }
            return new String(resultStr.getBytes("ISO-8859-1"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return defaultstr;
        }
    }

    public static String getString(String key) {
        String resultStr = "";
        try {
            resultStr = SETTINGS.getProperty(key);
            resultStr = new String(resultStr.getBytes("ISO-8859-1"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static int getInt(String key , int defaultint){
        String resultStr = "";
        try {
            resultStr = SETTINGS.getProperty(key);
            if (StringUtils.isBlank(resultStr)){
                return defaultint;
            }
            resultStr = new String(resultStr.getBytes("ISO-8859-1"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(resultStr);
    }

}
