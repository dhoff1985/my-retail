package com.dthoffman.myretail.service

import com.dthoffman.myretail.domain.Price
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.MongoCollection
import javax.inject.Singleton

@Singleton
class PriceService(val priceCollection: MongoCollection<Price>) {
    fun getPrice(tcin: String): Price? {
        return priceCollection.find(eq("tcin", tcin)).first()
    }
}