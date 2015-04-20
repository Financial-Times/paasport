package com.ft.paasport.codedeploy.resources;

import com.ft.paasport.codedeploy.domain.Deployment;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class DeploymentsResource {

    @POST
    @Path("/{clusterId}/deployments")
    @Consumes("application/json")
    public Response deploy(@Valid Deployment deployment) {
        System.out.println(deployment.getSourceTar().getUrl());
        return Response.ok().build();
    }
}
