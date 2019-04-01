package com.dthoffman.myretail.service

import com.dthoffman.myretail.BaseSpec
import com.dthoffman.myretail.client.*
import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.domain.Product
import kotlinx.coroutines.CompletableDeferredKt
import kotlinx.coroutines.Dispatchers

import java.util.concurrent.CompletableFuture

class ProductServiceSpec extends BaseSpec {

  RedskyClient mockRedskyClient = Mock(RedskyClient)

  PriceService mockPriceService = Mock(PriceService)

  ProductService productService = new ProductService(mockRedskyClient, mockPriceService, Dispatchers.Default)

  String id = "123"
  def price = new Price('$2.95',"USD")

  def "product service calls redsky and returns product name"() {
    when:
    Product response = sync { productService.getProduct(id) }

    then:
    1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(id) >> CompletableDeferredKt.CompletableDeferred(null)
    response.id == id
    response.name == "The Big Lebowski (Blu-ray)"
    response.current_price == null
  }

  def "product service calls redsky and returns product name and price"() {
    when:
    Product response = sync { productService.getProduct(id) }

    then:
    1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(id) >> CompletableDeferredKt.CompletableDeferred(price)
    response.id == id
    response.name == "The Big Lebowski (Blu-ray)"
    response.current_price == price
  }

  def "product service handles price errors"() {
    setup:
    def exceptionDeferred = CompletableDeferredKt.CompletableDeferred(null)
    exceptionDeferred.completeExceptionally(new RuntimeException("error"))

    when:
    Product response = sync { productService.getProduct(id) }

    then:
    1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(id) >> { throw  new RuntimeException("error") }

    response.id == id
    response.name == "The Big Lebowski (Blu-ray)"
    response.current_price == null
  }

  PDPResponse buildProductResponse() {
    return new PDPResponse(new PDPProduct(new PDPItem(new ProductDescription("The Big Lebowski (Blu-ray)"))))
  }
}
