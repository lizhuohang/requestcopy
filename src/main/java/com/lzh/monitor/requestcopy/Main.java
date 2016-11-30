package com.lzh.monitor.requestcopy;

import com.lzh.monitor.requestcopy.multiplay.LogReader;

/**
 * Created by lizhuohang on 2016/11/29.
 */
public class Main {
    public static void main(String[] args) {
        Thread wthread = new Thread(new LogReader());
        wthread.start();
    }
}
