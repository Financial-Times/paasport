package com.ft.paasport.codedeploy.resources;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

@Path("/hello-world")
public class HelloWorldResource {

    private String greeting;

    public HelloWorldResource(String greeting) {
        this.greeting = greeting;
    }

    @GET
    public String getGreeting() {
        CommandLine cmdLine = CommandLine.parse("/Users/anuragkapur/tech-stuff/workspace/ft/paasport/code-deploy/scripts/java-deploy.sh");
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return greeting;
    }
}
