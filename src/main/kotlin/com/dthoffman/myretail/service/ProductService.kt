package com.dthoffman.myretail.service

import com.dthoffman.myretail.client.RedskyClient
import com.dthoffman.myretail.domain.Product
import javax.inject.Singleton

@Singleton
class ProductService(val redskyClient: RedskyClient, val priceService: PriceService) {

    fun getProduct(tcin: String): Product {
        val productResponse = redskyClient.getPdp(tcin).get()
        val price = priceService.getPrice(tcin)
        return Product(tcin, productResponse.product.item.product_description.title, price?.price)
    }
}