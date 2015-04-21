package com.ft.paasport.codedeploy.dao;

import com.ft.paasport.codedeploy.db.MongoDbConnection;
import com.ft.paasport.codedeploy.domain.Deployment;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author anuragkapur
 */
public class DeploymentsDaoTest {

    @Test
    public void testInsertDeployment() throws Exception {
        MongoDbConnection connection = new MongoDbConnection();
        DeploymentsDao deploymentsDao = new DeploymentsDao(connection);
        deploymentsDao.insertDeployment(new Deployment(), "test", "12345659dhndy238");
    }
}