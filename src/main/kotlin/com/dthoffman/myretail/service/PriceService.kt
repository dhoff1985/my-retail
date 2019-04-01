package com.dthoffman.myretail.service

import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.mongo.MongoPrice
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PriceService(val priceCollection: MongoCollection<MongoPrice>, override val coroutineContext: CoroutineContext) : CoroutineScope {
    fun getPrice(id: String): Deferred<Price?> {
        return async {
            val mongoPrice = priceCollection.find(eq("_id", id)).awaitFirstOrNull()
            if (mongoPrice != null) Price(mongoPrice.value!!, mongoPrice.currency_code!!) else null
        }
    }

    fun savePrice(id: String, price: Price): Deferred<Price> {
        return async {
            priceCollection.replaceOne(eq("_id", id), MongoPrice(id, price.value, price.currency_code), ReplaceOptions().upsert(true)).awaitFirst()
            price
        }
    }
}