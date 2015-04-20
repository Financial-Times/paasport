package com.ft.paasport.codedeploy.resources;

import com.ft.paasport.codedeploy.domain.Deployment;
import com.ft.paasport.codedeploy.service.Deployer;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/")
public class DeploymentsResource {

    private Deployer deployer;

    public DeploymentsResource(Deployer deployer) {
        this.deployer = deployer;
    }

    @POST
    @Path("/{clusterId}/deployments")
    @Consumes("application/json")
    public Response deploy(@Valid Deployment deployment, @PathParam("clusterId") final String clusterId) {
        try {
            deployer.deploy(clusterId, deployment.getSourceTar().getUrl());
        } catch(Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
}
