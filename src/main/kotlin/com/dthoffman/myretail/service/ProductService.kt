package com.dthoffman.myretail.service

import com.dthoffman.myretail.client.RedskyClient
import com.dthoffman.myretail.domain.Product
import javax.inject.Singleton

@Singleton
class ProductService(val redskyClient: RedskyClient) {

    fun getProduct(tcin: String): Product {
        val response = redskyClient.getPdp(tcin).get()
        return Product(tcin, response.product.item.product_description.title)
    }
}