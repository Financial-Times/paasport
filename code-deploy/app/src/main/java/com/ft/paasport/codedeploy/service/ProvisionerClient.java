package com.ft.paasport.codedeploy.service;

import com.ft.paasport.codedeploy.domain.Machine;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anuragkapur
 */
public class ProvisionerClient {

    private String clusterDefEndPointTemplate;
    private Client client;

    public ProvisionerClient(Client client, String clusterDefEndPointTemplate) {
        this.client = client;
        this.clusterDefEndPointTemplate = clusterDefEndPointTemplate;
    }

    public List<String> getMachinesInCluster(String clusterId) {

        String url = String.format(clusterDefEndPointTemplate, clusterId);
        WebTarget resource = client.target(url);
        List<Machine> machines = resource.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Machine>>(){});
        List<String> hostnames = new ArrayList<>(machines.size());
        for (Machine machine : machines) {
            hostnames.add(machine.getHostname());
        }

        return hostnames;
    }
}
