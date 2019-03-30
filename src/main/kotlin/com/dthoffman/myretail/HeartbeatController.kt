package com.dthoffman.myretail

import io.micronaut.context.annotation.Value
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HeartbeatController(
        @Value("\${micronaut.application.version}") val version: String,
        @Value("\${micronaut.application.name}") val name: String) {

    @Get("/heartbeat")
    fun getHeartbeat(): Map<String, String> {
        return mapOf("application" to name, "version" to version)
    }
}