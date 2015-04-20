package com.ft.paasport.codedeploy.testssupport;

/**
 * @author anuragkapur
 */
public abstract class AbstractIntegrationTest {

    public IntegrationTestConfiguration getConfig() {

        String configFileNameTemplate = "integrationTests-%s.yml";
        String configFileName = String.format(configFileNameTemplate, "LOCAL");

        if(System.getProperty("APP.ENV") != null) {
            configFileName = String.format(configFileNameTemplate, System.getProperty("APP.ENV"));
        }

        return new ConfigLoader<>(IntegrationTestConfiguration.class, configFileName).getConfig();
    }
}
