package com.dthoffman.myretail.service

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Factory
class CoroutineContextFactory {
    @Bean
    @Singleton
    fun coroutineContext(): CoroutineContext {
        return Dispatchers.Default
    }
}