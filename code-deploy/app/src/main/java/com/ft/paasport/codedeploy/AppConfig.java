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

    @Valid
    @JsonProperty
    private String provisionerClusterDefEndpoint;

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

    public String getProvisionerClusterDefEndpoint() {
        return provisionerClusterDefEndpoint;
    }

    public void setProvisionerClusterDefEndpoint(String provisionerClusterDefEndpoint) {
        this.provisionerClusterDefEndpoint = provisionerClusterDefEndpoint;
    }
}
