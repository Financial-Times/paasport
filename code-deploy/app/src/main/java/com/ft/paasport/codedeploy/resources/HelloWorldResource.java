package com.ft.paasport.codedeploy.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/membership/hello-world")
public class HelloWorldResource {

    private String greeting;

    public HelloWorldResource(String greeting) {
        this.greeting = greeting;
    }

    @GET
    public String getGreeting() {
        return greeting;
    }
}
