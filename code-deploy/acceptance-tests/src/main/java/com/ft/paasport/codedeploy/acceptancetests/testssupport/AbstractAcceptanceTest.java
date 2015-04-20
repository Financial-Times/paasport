package com.ft.paasport.codedeploy.acceptancetests.testssupport;

import me.atam.atam4j.configuration.ConfigLoader;

/**
 * @author anuragkapur
 */
public abstract class AbstractAcceptanceTest {

    public TestConfig getConfig() {

        String configFileNameTemplate = "acceptance-tests-%s.yml";
        String configFileName = String.format(configFileNameTemplate, "local");

        if(System.getProperty("APP.ENV") != null) {
            configFileName = String.format(configFileNameTemplate, System.getProperty("APP.ENV"));
        }

        return new ConfigLoader<>(TestConfig.class, configFileName).getTestConfig();
    }
}
