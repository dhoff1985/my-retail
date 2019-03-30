package com.dthoffman.myretail

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.dthoffman.myretail")
                .mainClass(Application.javaClass)
                .start()
    }
}