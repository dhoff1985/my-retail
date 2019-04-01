package com.dthoffman.myretail.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import java.util.concurrent.CompletableFuture

@Client("redsky")
interface RedskyClient {

    @Get("\${micronaut.http.services.redsky.path}")
    fun getPdp(id: String) : CompletableFuture<PDPResponse?>

}