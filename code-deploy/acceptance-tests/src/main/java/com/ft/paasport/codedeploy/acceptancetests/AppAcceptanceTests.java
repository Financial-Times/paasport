package com.ft.paasport.codedeploy.acceptancetests;

import com.ft.paasport.codedeploy.acceptancetests.tests.DummyAcceptanceTest;
import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.AcceptanceTestHealthCheckManager;

import java.io.File;

/**
 * @author anuragkapur
 */
public class AppAcceptanceTests extends Application<Configuration> {

    public static final int TEN_MINUTES_IN_SECONDS = 60 * 10;

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {

        // Enable starting the sw app without any resources defined. Remove this if you want to register resources.
        environment.jersey().disable();

        // TODO: Pass top level package instead of individual class names
        new AcceptanceTestHealthCheckManager(environment, TEN_MINUTES_IN_SECONDS, DummyAcceptanceTest.class).initialise();
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            args = new String[]{"server", new File(Resources.getResource("dw-config.yml").toURI()).getAbsolutePath()};
        }

        new AppAcceptanceTests().run(args);
    }
}
