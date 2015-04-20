package com.ft.paasport.codedeploy.integrationtests;

import com.ft.paasport.codedeploy.testssupport.AbstractIntegrationTest;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

public class DummyDependencyTestIT extends AbstractIntegrationTest {



    @Test
    public void testCanGetExampleDotCom() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getConfig().getDummyDependencyUrl()).path("/");
        assertEquals(200, target.request().head().getStatus());
    }
}
