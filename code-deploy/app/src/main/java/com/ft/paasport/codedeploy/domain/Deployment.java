package com.ft.paasport.codedeploy.domain;

import java.util.Date;

/**
 * @author anuragkapur
 */
public class Deployment {

    private String uuid;
    private TarFile sourceTar;
    private String status = "initiated";
    private String message = null;
    private Date timestamp;
    private int hostCount;
    private int completedCount = 0;

    public TarFile getSourceTar() {
        return sourceTar;
    }

    public void setSourceTar(TarFile sourceTar) {
        this.sourceTar = sourceTar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHostCount() {
        return hostCount;
    }

    public void setHostCount(int hostCount) {
        this.hostCount = hostCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
