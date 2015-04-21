package com.ft.paasport.codedeploy.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author anuragkapur
 */
public class MongoDbConnection {

    public static MongoDatabase database;

    public MongoDbConnection() {
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        database = mongoClient.getDatabase("paasport-code-deploy");
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }
}
