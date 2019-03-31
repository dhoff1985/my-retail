package com.dthoffman.myretail.service

import com.dthoffman.myretail.domain.Price
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
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
    fun  priceCollection(codecRegistry: CodecRegistry): MongoCollection<Price> {
        return mongoClient.getDatabase("myRetail").getCollection("price", Price::class.java).withCodecRegistry(codecRegistry)
    }

    @Bean
    @Singleton
    fun codecRegistry() : CodecRegistry {
        return fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()))
    }
}