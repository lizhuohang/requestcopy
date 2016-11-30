package com.lzh.monitor.requestcopy.multiplay;

import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.multiplay.threads.RequestThread;
import com.lzh.monitor.requestcopy.utils.Setting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class MultiRequest {

    private static final int MAX_HANDLE_IMAGE_THREAD_COUNT = Setting.getInt("MAX_THREAD_COUNT" , 10);
    private static final ExecutorService putExecutors = Executors.newFixedThreadPool(MAX_HANDLE_IMAGE_THREAD_COUNT);


    public static void request(RequestModule r){
        putExecutors.execute(new RequestThread(r));
    }
}