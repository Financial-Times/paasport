package com.ft.paasport.codedeploy.resources;

import com.ft.paasport.codedeploy.dao.DeploymentsDao;
import com.ft.paasport.codedeploy.domain.Deployment;
import com.ft.paasport.codedeploy.domain.DeploymentAcceptMetadata;
import com.ft.paasport.codedeploy.domain.Machine;
import com.ft.paasport.codedeploy.service.Deployer;
import com.ft.paasport.codedeploy.service.DeploymentJob;
import com.ft.paasport.codedeploy.service.ProvisionerClient;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Path("/")
public class DeploymentsResource {

    private Deployer deployer;
    private ProvisionerClient provisioner;
    private DeploymentsDao deploymentsDao;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public DeploymentsResource(Deployer deployer, ProvisionerClient provisioner, DeploymentsDao deploymentsDao) {
        this.deployer = deployer;
        this.provisioner = provisioner;
        this.deploymentsDao = deploymentsDao;
    }

    @POST
    @Path("/{clusterId}/deployments")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deploy(@Valid Deployment deployment, @PathParam("clusterId") final String clusterId) {

        // record deployment
        deployment.setTimestamp(new Date());
        UUID uuid = UUID.randomUUID();
        List<String> hostnames = provisioner.getMachinesInCluster(clusterId);
        deployment.setHostCount(hostnames.size());
        deploymentsDao.insertDeployment(deployment, clusterId, uuid.toString());
        String url = deployment.getSourceTar().getUrl();

        // execute jobs on hosts
        for (String hostname : hostnames) {
            DeploymentJob deploymentJob = new DeploymentJob(deployer, hostname, url, uuid.toString(), deploymentsDao);
            executorService.execute(deploymentJob);
        }

        final DeploymentAcceptMetadata acceptMetadata = new DeploymentAcceptMetadata();
        acceptMetadata.setUuid(uuid.toString());

        return Response.accepted(acceptMetadata).build();
    }
}
