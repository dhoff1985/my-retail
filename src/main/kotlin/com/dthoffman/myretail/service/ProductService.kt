package com.dthoffman.myretail.service

import com.dthoffman.myretail.client.PDPResponse
import com.dthoffman.myretail.client.RedskyClient
import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.domain.Product
import io.micronaut.http.client.exceptions.HttpClientException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ProductService(val redskyClient: RedskyClient, val priceService: PriceService, override val coroutineContext: CoroutineContext) : CoroutineScope {

    val log : Logger = LoggerFactory.getLogger(ProductService::class.java)

    fun getProduct(id: String): Deferred<Product> {
        return async {
            val productResponse = getPdpResponse(id)
            val price = getPrice(id)
            Product(id, productResponse?.product?.item?.product_description?.title, price)
        }
    }

    private suspend fun getPrice(id: String): Price? {
        try {
            return priceService.getPrice(id).await()
        } catch (e: Exception) {
            log.warn("Error retrieving price", e)
        }
        return null
    }

    private suspend fun getPdpResponse(id: String): PDPResponse? {
        try {
            return redskyClient.getPdp(id).await()
        } catch (e: HttpClientException) {
            log.warn("Error retrieving product", e)
        }
        return null
    }
}