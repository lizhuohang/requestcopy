package com.lzh.monitor.requestcopy.timertask;

import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.multiplay.MultiRequest;
import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * Created by lizhuohang on 2016/11/29.
 */
public class CopyRequestTask extends TimerTask {

    private RequestModule r;
    private static final transient Logger logger = Logger.getLogger(RequestModule.class);

    public CopyRequestTask(RequestModule r){
        this.r = r;
    }

    public void run() {
        MultiRequest.request(this.r);
    }
}
