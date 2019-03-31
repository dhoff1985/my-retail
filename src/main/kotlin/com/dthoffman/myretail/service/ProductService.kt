package com.dthoffman.myretail.service

import com.dthoffman.myretail.client.RedskyClient
import com.dthoffman.myretail.domain.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ProductService(val redskyClient: RedskyClient, val priceService: PriceService, override val coroutineContext: CoroutineContext) : CoroutineScope {

    fun getProduct(tcin: String): Deferred<Product> {
        return async {
            val productResponse = redskyClient.getPdp(tcin).await()
            val price = priceService.getPrice(tcin).await()
            Product(tcin, productResponse.product.item.product_description.title, price?.price)
        }
    }
}