package com.dthoffman.myretail.controller

import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.service.PriceService
import io.micronaut.http.annotation.*
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture

@Controller
class PriceController(val priceService: PriceService) {

    @Put("/price/{id}")
    fun addUpdatePrice(@PathVariable("id") id: String, @Body price: Price): CompletableFuture<Price> {
        return priceService.savePrice(id, price).asCompletableFuture()
    }

}