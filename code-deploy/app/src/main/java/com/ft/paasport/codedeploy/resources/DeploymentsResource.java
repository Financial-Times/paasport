package com.ft.paasport.codedeploy.resources;

import com.ft.paasport.codedeploy.domain.Deployment;
import com.ft.paasport.codedeploy.domain.DeploymentAcceptMetadata;
import com.ft.paasport.codedeploy.domain.Machine;
import com.ft.paasport.codedeploy.service.Deployer;
import com.ft.paasport.codedeploy.service.ProvisionerClient;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class DeploymentsResource {

    private Deployer deployer;
    private ProvisionerClient provisioner;

    public DeploymentsResource(Deployer deployer, ProvisionerClient provisioner) {
        this.deployer = deployer;
        this.provisioner = provisioner;
    }

    @POST
    @Path("/{clusterId}/deployments")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deploy(@Valid Deployment deployment, @PathParam("clusterId") final String clusterId) {

        List<String> hostnames = provisioner.getMachinesInCluster(clusterId);
        for (String hostname : hostnames) {
            try {
                deployer.deploy(hostname, deployment.getSourceTar().getUrl());
            } catch(Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }

        return Response.accepted(new DeploymentAcceptMetadata()).build();
    }
}
