package com.ft.paasport.codedeploy;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;

/**
 * @author anuragkapur
 */
public class AppConfig extends Configuration {

    @Valid
    @JsonProperty
    private String secureConfigLocation;

    @Valid
    @JsonProperty
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
