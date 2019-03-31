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

  String tcin = "123"

  def "product service calls redsky and returns product name"() {
    when:
    Product response = sync { productService.getProduct(tcin) }

    then:
    1 * mockRedskyClient.getPdp(tcin) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(tcin) >> CompletableDeferredKt.CompletableDeferred(null)
    response.id == tcin
    response.name == "The Big Lebowski (Blu-ray)"
    response.price == null
  }

  def "product service calls redsky and returns product name and price"() {
    when:
    Product response = sync { productService.getProduct(tcin) }

    then:
    1 * mockRedskyClient.getPdp(tcin) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(tcin) >> CompletableDeferredKt.CompletableDeferred(new Price(tcin: tcin, price: '$2.95'))
    response.id == tcin
    response.name == "The Big Lebowski (Blu-ray)"
    response.price == '$2.95'
  }

  def "product service handles price errors"() {
    setup:
    def exceptionDeferred = CompletableDeferredKt.CompletableDeferred(null)
    exceptionDeferred.completeExceptionally(new RuntimeException("error"))

    when:
    Product response = sync { productService.getProduct(tcin) }

    then:
    1 * mockRedskyClient.getPdp(tcin) >> CompletableFuture.completedFuture(buildProductResponse())
    1 * mockPriceService.getPrice(tcin) >> { throw  new RuntimeException("error") }

    response.id == tcin
    response.name == "The Big Lebowski (Blu-ray)"
    response.price == null
  }

  PDPResponse buildProductResponse() {
    return new PDPResponse(new PDPProduct(new PDPItem(new ProductDescription("The Big Lebowski (Blu-ray)"))))
  }
}
