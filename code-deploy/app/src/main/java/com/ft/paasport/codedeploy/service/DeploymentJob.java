package com.ft.paasport.codedeploy.service;

import com.ft.paasport.codedeploy.dao.DeploymentsDao;
import com.ft.paasport.codedeploy.domain.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author anuragkapur
 */
public class DeploymentJob implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentJob.class);
    private Deployer deployer;
    private String hostname;
    private String url;
    private String uuid;
    private DeploymentsDao deploymentsDao;

    public DeploymentJob(Deployer deployer, String hostname, String url, String uuid, DeploymentsDao deploymentsDao) {
        this.deployer = deployer;
        this.hostname = hostname;
        this.url = url;
        this.uuid = uuid;
        this.deploymentsDao = deploymentsDao;
    }

    @Override
    public void run() {
        try {
            deployer.deploy(hostname, url);
            Deployment deployment = deploymentsDao.getDeployment(uuid);
            int completedCount = deployment.getCompletedCount();
            completedCount ++;
            deployment.setCompletedCount(completedCount);
            LOGGER.debug("Completed count update {}", deployment.getCompletedCount());
            if (completedCount == deployment.getHostCount()) {
                deployment.setStatus("completed");
            }
            deploymentsDao.updateDeployment(deployment);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
