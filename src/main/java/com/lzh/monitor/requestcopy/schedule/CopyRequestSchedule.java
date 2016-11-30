package com.lzh.monitor.requestcopy.schedule;

import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.timertask.CopyRequestTask;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by lizhuohang on 2016/11/29.
 */
public class CopyRequestSchedule extends BaseSchedule {

    private List<RequestModule> list;

    public CopyRequestSchedule(List<RequestModule> list){
        this.list = list;
    }

    public void init() {
        Timer timer = new Timer("copy_request");
        Date now = new Date();
        Calendar firstcal = Calendar.getInstance();
        if (this.list == null || this.list.size() == 0){
            return;
        }
        for (RequestModule r : this.list){
            firstcal.setTime(r.getRequesttime());
            firstcal.add(Calendar.MINUTE, 1);
            timer.schedule(new CopyRequestTask(r), firstcal.getTime());
        }
    }
}
