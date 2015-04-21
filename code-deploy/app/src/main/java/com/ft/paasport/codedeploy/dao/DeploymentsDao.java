package com.ft.paasport.codedeploy.dao;

import com.ft.paasport.codedeploy.db.MongoDbConnection;
import com.ft.paasport.codedeploy.domain.Deployment;
import com.ft.paasport.codedeploy.domain.TarFile;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author anuragkapur
 */
public class DeploymentsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeploymentsDao.class);
    private static final String DEPLOYMENTS_COLLECTION_NAME = "deployments";
    private static MongoDbConnection connection;

    public DeploymentsDao(MongoDbConnection connection) {
        DeploymentsDao.connection = connection;
    }

    public void insertDeployment(Deployment deployment, String clusterId, String uuid) {

        Document document = new Document("uuid", uuid);
        document.append("clusterId", clusterId);
        document.append("date", deployment.getTimestamp());
        document.append("sourceTar", deployment.getSourceTar().getUrl());
        document.append("status", deployment.getStatus());
        document.append("message", deployment.getMessage());
        document.append("completedCount", deployment.getCompletedCount());
        document.append("hostCount", deployment.getHostCount());
        document.append("timestamp", deployment.getTimestamp());

        connection.getCollection(DEPLOYMENTS_COLLECTION_NAME).insertOne(document);
    }

    public Deployment getDeployment(String deploymentId) {
        Document document = connection.getCollection(DEPLOYMENTS_COLLECTION_NAME).find(eq("uuid", deploymentId)).first();
        LOGGER.debug(document.toJson());
        Deployment deployment = new Deployment();
        deployment.setUuid(document.getString("uuid"));
        deployment.setCompletedCount(document.getInteger("completedCount"));
        deployment.setHostCount(document.getInteger("hostCount"));
        deployment.setMessage(document.getString("message"));
        TarFile tarFile = new TarFile();
        tarFile.setUrl(document.getString("sourceTar"));
        deployment.setSourceTar(tarFile);
        deployment.setStatus(document.getString("status"));
        deployment.setTimestamp(document.getDate("timestamp"));
        return deployment;
    }

    public void updateDeployment(Deployment deployment) {
        Document document = new Document("uuid", deployment.getUuid());
        document.append("completedCount", deployment.getCompletedCount());
        document.append("hostCount", deployment.getHostCount());
        document.append("message", deployment.getMessage());
        document.append("sourceTar", deployment.getSourceTar().getUrl());
        document.append("status", deployment.getStatus());
        document.append("timestamp", deployment.getTimestamp());
        UpdateResult result = connection.getCollection(DEPLOYMENTS_COLLECTION_NAME).updateOne(eq("uuid", deployment.getUuid()), new Document("$set", document));
        LOGGER.debug("Matched :: {}, Updated :: {}", result.getMatchedCount(), result.getModifiedCount());

    }
}
