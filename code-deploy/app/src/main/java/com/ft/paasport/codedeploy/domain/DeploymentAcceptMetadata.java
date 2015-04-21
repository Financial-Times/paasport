package com.ft.paasport.codedeploy.domain;

/**
 * @author anuragkapur
 */
public class DeploymentAcceptMetadata {

    private String uuid;
    private String messageTemplate = "Deployment request accepted. Check status here: http://ec2-52-17-74-125.eu-west-1.compute.amazonaws.com:8080/paasport/code-deploy/%s";
    private String message;

    public DeploymentAcceptMetadata(String uuid) {
        message = String.format(messageTemplate, uuid);
    }

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
