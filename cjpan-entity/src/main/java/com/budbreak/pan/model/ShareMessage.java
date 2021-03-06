package com.budbreak.pan.model;

import java.util.Date;
import java.util.List;

/**
 * Created by zc on 2018/10/31.
 */
public class ShareMessage {
    //    文件名
    private String fileName;

    //    下载次数
    private int downloadNum;

    //   过期时间
    private Date expireDate;

    //    分享时间
    private String time;

    //    下载次数
    private List<String> downloadName;

    public List<String> getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(List<String> downloadName) {
        this.downloadName = downloadName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
