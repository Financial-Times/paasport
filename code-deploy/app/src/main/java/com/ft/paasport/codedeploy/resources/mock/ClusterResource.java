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
                "\t\"id\":\"0000000000f1a\",\n" +
                "\t\"instance_id\": \"blah\",\n" +
                "\t\"name\":\"friendly name\",\n" +
                "\t\"region\":\"eu-west-1\"\n" +
                "\t}\t\n" +
                "]";
    }
}
