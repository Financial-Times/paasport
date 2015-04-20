package com.ft.paasport.codedeploy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author anuragkapur
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Machine {

    String hostname;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
