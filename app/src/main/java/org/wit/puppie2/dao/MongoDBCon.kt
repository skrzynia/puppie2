package org.wit.puppie2.dao


import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients

object MongoDBCon {
    private const val CONNECTION_STRING = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.1.1"

    fun getMongoClient() : MongoClient {
        return MongoClients.create(CONNECTION_STRING)
    }
}