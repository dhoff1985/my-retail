package com.dthoffman.myretail

import com.dthoffman.myretail.mongo.MongoPrice
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import javax.inject.Singleton


@Factory
class MongoFactory(val mongoClient: MongoClient) {

    @Bean
    @Singleton
    fun  priceCollection(codecRegistry: CodecRegistry): MongoCollection<MongoPrice> {
        return mongoClient.getDatabase("myRetail").getCollection("price", MongoPrice::class.java).withCodecRegistry(codecRegistry)
    }

    @Bean
    @Singleton
    fun codecRegistry() : CodecRegistry {
        return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()))
    }
}