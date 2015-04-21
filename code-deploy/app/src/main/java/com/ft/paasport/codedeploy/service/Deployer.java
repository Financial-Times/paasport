package com.ft.paasport.codedeploy.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author anuragkapur
 */
public class Deployer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Deployer.class);
    private static String filePath;

    public Deployer(String filePath) throws MalformedURLException {
        Deployer.filePath = filePath;
    }

    public void deploy(String hostname, String sourceTarUrl) throws IOException {
        LOGGER.info("hostname :: {}, sourceTarUrl :: {}", hostname, sourceTarUrl);
        CommandLine cmdLine = CommandLine.parse(filePath);
        cmdLine.addArgument(hostname);
        cmdLine.addArgument(sourceTarUrl);
        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(cmdLine);
    }
}
