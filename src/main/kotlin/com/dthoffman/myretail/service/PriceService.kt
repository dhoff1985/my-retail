package com.dthoffman.myretail.service

import com.dthoffman.myretail.domain.Price
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PriceService(val priceCollection: MongoCollection<Price>, override val coroutineContext: CoroutineContext) : CoroutineScope {
    fun getPrice(tcin: String): Deferred<Price?> {
        return async {
            priceCollection.find(eq("tcin", tcin)).first()
        }
    }
}