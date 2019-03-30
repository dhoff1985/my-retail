package com.dthoffman.myretail.service

import com.dthoffman.myretail.client.PDPItem
import com.dthoffman.myretail.client.PDPProduct
import com.dthoffman.myretail.client.PDPResponse
import com.dthoffman.myretail.client.ProductDescription
import com.dthoffman.myretail.client.RedskyClient
import com.dthoffman.myretail.domain.Product
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class ProductServiceSpec extends Specification {

  RedskyClient mockRedskyClient = Mock(RedskyClient)

  ProductService productService = new ProductService(mockRedskyClient)

  String tcin = "123"

  def "product service calls redsky and returns product name" () {
    when:
    Product response = productService.getProduct(tcin)

    then:
    1 * mockRedskyClient.getPdp(tcin) >> CompletableFuture.completedFuture(buildProductResponse())
    response.id == tcin
    response.name == "The Big Lebowski (Blu-ray)"
  }

  PDPResponse buildProductResponse() {
    return new PDPResponse(new PDPProduct(new PDPItem(new ProductDescription("The Big Lebowski (Blu-ray)"))))
  }
}
