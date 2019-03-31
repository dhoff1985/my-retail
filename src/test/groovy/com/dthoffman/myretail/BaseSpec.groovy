package com.dthoffman.myretail

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.FutureKt
import spock.lang.Specification

class BaseSpec extends Specification {
  public <T> T sync (Closure<Deferred<T>> operation) {
    Deferred<T> result = operation()
    return FutureKt.asCompletableFuture(result).get()
  }
}
