package com.lzh.monitor.requestcopy.multiplay;

import com.lzh.monitor.requestcopy.module.RequestModule;
import com.lzh.monitor.requestcopy.schedule.CopyRequestSchedule;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class LogReader implements Runnable {
    private File logFile = null;
    //    private String filepath = "/usr/local/image-proxy-tomcat/logs/";
    private String filepath = "/Users/lizhuohang/Downloads/log/";
    private long lastTimeFileSize = 0; // file size ,last time
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat request_time_f = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

    private static final transient Logger logger = Logger.getLogger(LogReader.class);

    public void run() {
        while (true) {
            try {
                String file_name = dateFormat.format(new Date());
                if (logFile == null || !logFile.getName().equals(file_name)) {
                    logFile = new File(filepath + dateFormat.format(new Date()));
                }
                long len = logFile.length();
                if (len < lastTimeFileSize) {
                    logger.info("Use a new access log file! Name is " + file_name);
                    lastTimeFileSize = len;
                } else if (len > lastTimeFileSize) {
                    RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
                    randomFile.seek(lastTimeFileSize);
                    String tmp = null;
                    List<RequestModule> request_list = new ArrayList<RequestModule>();
                    while ((tmp = randomFile.readLine()) != null) {
                        logger.info("read line from log file : " + tmp);
                        String[] l_sts = tmp.split("] \"GET ");
                        if (l_sts.length <= 1) {
                            continue;
                        }
                        String info = l_sts[1];
                        RequestModule r = new RequestModule();
                        String[] infos = info.split(" ");
                        r.setUrl("http://img-src.transcoder.opera.com" + infos[0]);
                        r.setHttpcode(Integer.parseInt(infos[2]));
                        String time_str = l_sts[0].split("- - \\[")[1];
                        Date re_time = request_time_f.parse(time_str);
                        r.setRequesttime(re_time);
                        request_list.add(r);
                    }
                    lastTimeFileSize = randomFile.length();
                    randomFile.close();
                    //exc
                    new CopyRequestSchedule(request_list).init();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
