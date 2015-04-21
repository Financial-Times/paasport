package com.ft.paasport.codedeploy;

import com.ft.paasport.codedeploy.health.HelloWorldHealthCheck;
import com.ft.paasport.codedeploy.resources.DeploymentsResource;
import com.ft.paasport.codedeploy.resources.HelloWorldResource;
import com.ft.paasport.codedeploy.resources.mock.ClusterResource;
import com.ft.paasport.codedeploy.service.Deployer;
import com.ft.paasport.codedeploy.service.ProvisionerClient;
import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.net.URISyntaxException;

/**
 * @author anuragkapur
 */
public class App extends Application<AppConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {

        // Init and register hello-world resource
        final HelloWorldResource helloWorldResource = new HelloWorldResource(appConfig.getGreeting());
        environment.jersey().register(helloWorldResource);

        // Mocks
        final ClusterResource clusterResource = new ClusterResource();
        environment.jersey().register(clusterResource);

        // Core resources
        final ProvisionerClient provisioner =
                new ProvisionerClient(ClientBuilder.newClient(), appConfig.getProvisionerClusterDefEndpoint());
        final DeploymentsResource deploymentsResource =
                new DeploymentsResource(new Deployer(appConfig.getRemoteDeployScriptPath()), provisioner);
        environment.jersey().register(deploymentsResource);

        final HelloWorldHealthCheck helloWorldHealthCheck = new HelloWorldHealthCheck();
        environment.healthChecks().register("helloWorldHealthCheck", helloWorldHealthCheck);
    }

    @Override
    public String getName() {
        return "appName";
    }

    public static void main(String[] args) throws Exception {
        new App().run(getDefaultedArgs(args));
    }

    private static String[] getDefaultedArgs(String[] args) {
        final String[] actualArgs;
        if (args.length == 0) {
            String yamlPath = null;
            try {
                yamlPath = new File(Resources.getResource("code-deploy-config.yaml").toURI()).getAbsolutePath();
            } catch (URISyntaxException e) {
                LOGGER.error("error reading config", e);
            }
            actualArgs = new String[]{"server", yamlPath};
        } else {
            actualArgs = args;
        }
        return actualArgs;
    }
}
