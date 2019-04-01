package com.dthoffman.myretail.controller

import com.dthoffman.myretail.domain.Product
import com.dthoffman.myretail.service.ProductService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture

@Controller
class ProductController(val productService: ProductService) {

    @Get("/product/{id}")
    fun getProduct(@PathVariable("id") id: String): CompletableFuture<Product> {
        return productService.getProduct(id).asCompletableFuture()
    }

}