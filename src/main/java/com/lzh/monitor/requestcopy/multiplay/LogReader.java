package com.lzh.monitor.requestcopy.multiplay;

import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.schedule.CopyRequestSchedule;
import com.lzh.monitor.requestcopy.utils.CacheKeyUtil;
import com.lzh.monitor.requestcopy.utils.RedisUtil;
import com.lzh.monitor.requestcopy.utils.Setting;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class LogReader implements Runnable {
    private File logFile = null;
    private String filepath = Setting.getString("LOGPATH");
    private String logfile_b = Setting.getString("LOGB");
    private String logfile_a = Setting.getString("LOGA");
    private String server_domain = Setting.getString("serverdomain");
    private long lastTimeFileSize = -1; // file size ,last time
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat request_time_f = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

    private static final transient Logger logger = Logger.getLogger(LogReader.class);

    public void run() {
        while (true) {
            try {
                String file_name = logfile_b + dateFormat.format(new Date()) + logfile_a;
                if (logFile == null || !logFile.getName().equals(file_name)) {
                    logFile = new File(filepath + file_name);
                }
                long len = logFile.length();

                if (len < lastTimeFileSize || lastTimeFileSize == -1) {
                    logger.info("Use a new access log file! Name is " + file_name);
                    lastTimeFileSize = len;
                } else if (len > lastTimeFileSize) {
                    RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
                    randomFile.seek(lastTimeFileSize);
                    String tmp = null;
                    List<RequestModule> request_list = new ArrayList<RequestModule>();
                    while ((tmp = randomFile.readLine()) != null) {
                        RequestModule r = getModule(tmp);
                        if (r == null){
                            continue;
                        }
                        request_list.add(r);
                    }
                    lastTimeFileSize = randomFile.length();
                    randomFile.close();
                    //exc
                    new CopyRequestSchedule(request_list).init();

                    //set into redis
//                    RedisUtil.set(CacheKeyUtil.getLogFileNameKey() , file_name);
//                    RedisUtil.set(CacheKeyUtil.getReadLineNumKey() , lastTimeFileSize + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private RequestModule getModule(String txt){
        try {
            String[] l_sts = txt.split("] \"GET ");
            if (l_sts.length <= 1) {
                return null;
            }
            String info = l_sts[1];
            RequestModule r = new RequestModule();
            String[] infos = info.split(" ");
            r.setUrl(server_domain + infos[0]);
            r.setHttpcode(Integer.parseInt(infos[2]));
            String time_str = l_sts[0].split("- - \\[")[1];
            Date re_time = request_time_f.parse(time_str);
            r.setRequesttime(re_time);
            return r;
        }catch (Exception e){
            logger.error("Change text to Module error :" + e);
            return null;
        }
    }
}
