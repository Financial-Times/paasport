package com.ft.paasport.codedeploy.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * @author anuragkapur
 */
public class Deployer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Deployer.class);
    private static final String DEPLOY_SCRIPT_NAME = "remoteDeploy.sh";
    private static URL fileUrl;

    public Deployer() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        fileUrl = classLoader.getResource(DEPLOY_SCRIPT_NAME);
    }

    public void deploy(String hostname, String sourceTarUrl) {
        LOGGER.info("hostname :: {}, sourceTarUrl :: {}", hostname, sourceTarUrl);
        CommandLine cmdLine = CommandLine.parse(fileUrl.getPath());
        cmdLine.addArgument(hostname);
        cmdLine.addArgument(sourceTarUrl);
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
