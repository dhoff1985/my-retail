package com.dthoffman.myretail.functional

import com.dthoffman.myretail.domain.Price

class ProductFunctionalSpec extends BaseFunctionalSpec {


  def "/product/{id} calls redsky and returns product name"() {
    setup:
    stubProductCall()

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == "The Big Lebowski (Blu-ray)"
    verifyProductCall()
  }

  def "/product/{id} calls redsky and returns product name and price"() {
    setup:
    stubProductCall()
    savePrice(new Price(tcin: '13860428', price: '$2.95'))

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == "The Big Lebowski (Blu-ray)"
    response.price == '$2.95'
    verifyProductCall()
  }

}
