package com.ft.paasport.codedeploy.domain;

/**
 * @author anuragkapur
 */
public class DeploymentAcceptMetadata {

    private String uuid;
    private String message = "Deployment request accepted. Check status here: TODO";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
