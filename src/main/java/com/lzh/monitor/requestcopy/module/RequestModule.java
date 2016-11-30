package com.lzh.monitor.requestcopy.module;

import java.util.Date;

/**
 * Created by lizhuohang on 2016/11/30.
 */
public class RequestModule {
    private String url;
    private int httpcode;
    private Date requesttime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(int httpcode) {
        this.httpcode = httpcode;
    }

    public Date getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(Date requesttime) {
        this.requesttime = requesttime;
    }
}
