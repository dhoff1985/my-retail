package com.dthoffman.myretail.controller

import com.dthoffman.myretail.domain.Product
import com.dthoffman.myretail.service.ProductService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller
class ProductController(val productService: ProductService) {

    @Get("/product/{tcin}")
    fun getHeartbeat(@PathVariable("tcin") tcin: String): Product {
        return productService.getProduct(tcin)
    }

}