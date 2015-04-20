package com.ft.paasport.codedeploy.resources.mock;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author anuragkapur
 */
@Path("/mock/clusters/")
public class ClusterResource {

    @GET
    @Path("{clusterId}/machines")
    @Produces("application/json")
    public String getMachinesInCluster() {
        return "[\n" +
                "\t{\n" +
                "\t\"id\":\"0001\",\n" +
                "\t\"instance_id\": \"i-5498b8b3\",\n" +
                "\t\"hostname\": \"ec2-52-17-65-181.eu-west-1.compute.amazonaws.com\",\n" +
                "\t\"name\":\"demo-application-node-1\",\n" +
                "\t\"region\":\"eu-west-1\"\n" +
                "\t}\t\n" +
                "]";
    }
}
