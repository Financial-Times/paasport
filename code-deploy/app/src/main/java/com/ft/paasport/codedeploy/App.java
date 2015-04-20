package com.ft.paasport.codedeploy;

import com.ft.paasport.codedeploy.health.HelloWorldHealthCheck;
import com.ft.paasport.codedeploy.resources.HelloWorldResource;
import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author anuragkapur
 */
public class App extends Application<AppConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {

        // Init and register hello-world resource
        final HelloWorldResource helloWorldResource = new HelloWorldResource(appConfig.getGreeting());
        environment.jersey().register(helloWorldResource);

        final HelloWorldHealthCheck helloWorldHealthCheck = new HelloWorldHealthCheck();
        environment.healthChecks().register("helloWorldHealthCheck", helloWorldHealthCheck);
    }

    @Override
    public String getName() {
        return "appName";
    }

    public static void main(String[] args) throws Exception {
        new App().run(getDefaultedArgs(args));
    }

    private static String[] getDefaultedArgs(String[] args) {
        final String[] actualArgs;
        if (args.length == 0) {
            String yamlPath = null;
            try {
                yamlPath = new File(Resources.getResource("com/ft/paasport/codedeploy/local-dev.yaml").toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                LOGGER.error("error reading config", e);
            }
            actualArgs = new String[]{"server", yamlPath};
        } else {
            actualArgs = args;
        }
        return actualArgs;
    }
}
