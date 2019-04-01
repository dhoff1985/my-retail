package com.dthoffman.myretail.controller

import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.service.PriceService
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import kotlinx.coroutines.future.asCompletableFuture
import java.util.concurrent.CompletableFuture
import javax.validation.Valid

@Validated
@Controller
class PriceController(val priceService: PriceService) {

    @Put("/price/{id}")
    fun addUpdatePrice(@PathVariable("id") id: String, @Valid @Body price: Price): CompletableFuture<Price> {
        return priceService.savePrice(id, price).asCompletableFuture()
    }

}