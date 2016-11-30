package com.lzh.monitor.requestcopy.multiplay.threads;

import com.alibaba.fastjson.JSONObject;
import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.utils.TestHttpRequestUtil;
import org.apache.log4j.Logger;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class RequestThread extends Thread {
    private static final transient Logger logger = Logger.getLogger(RequestThread.class);
    private RequestModule r;

    public RequestThread(RequestModule r) {
        this.r = r;
    }

    @Override
    public void run() {
        if (this.r == null) {
            logger.info("start to request image , but the request entity is null!");
            return;
        }
        String url = r.getUrl();
        boolean success = TestHttpRequestUtil.testGet(url, null);
        logger.info("request info : " + JSONObject.toJSONString(r) + "        request result : " + success);

    }
}
