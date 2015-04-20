package com.ft.paasport.codedeploy.acceptancetests.testssupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Custom config for Acceptance Tests. Note: This has got nothing to do with the DW Configuration and is based on a
 * separate config file loaded by a custom yaml config loader.
 *
 * @author anuragkapur
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TestConfig {

    private String secureConfigLocation;
    private String greeting;

    public String getSecureConfigLocation() {
        return secureConfigLocation;
    }

    public void setSecureConfigLocation(String secureConfigLocation) {
        this.secureConfigLocation = secureConfigLocation;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
