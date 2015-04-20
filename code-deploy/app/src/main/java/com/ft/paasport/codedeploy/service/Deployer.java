package com.ft.paasport.codedeploy.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.IOException;
import java.net.URL;

/**
 * @author anuragkapur
 */
public class Deployer {

    private static final String DEPLOY_SCRIPT_NAME = "java-deploy.sh";
    private static URL fileUrl;

    public Deployer() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        fileUrl = classLoader.getResource(DEPLOY_SCRIPT_NAME);
    }

    public void deploy(String clusterId, String sourceTarUrl) {
        CommandLine cmdLine = CommandLine.parse(fileUrl.getPath());
        cmdLine.addArgument(clusterId);
        cmdLine.addArgument(sourceTarUrl);
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
