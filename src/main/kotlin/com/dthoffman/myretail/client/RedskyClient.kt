package com.dthoffman.myretail.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import java.util.concurrent.CompletableFuture

@Client("\${backend.redsky.base-url}")
interface RedskyClient {

    @Get("\${backend.redsky.path}")
    fun getPdp(tcin: String) : CompletableFuture<PDPResponse>

}