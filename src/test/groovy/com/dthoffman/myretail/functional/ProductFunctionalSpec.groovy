package com.dthoffman.myretail.functional

import com.dthoffman.myretail.mongo.MongoPrice

import static com.github.tomakehurst.wiremock.client.WireMock.*

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

  def "/product/{id} handles redsky error codes"() {
    setup:
    wireMockRule.stubFor(get(urlEqualTo(PRODUCT_URL))
      .willReturn(aResponse()
      .withStatus(errorCode)
      .withHeader("Content-type", "application/json")))

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == null
    verifyProductCall()

    where:
    errorCode << [500, 404, 400, 401]
  }

  def "/product/{id} handles redsky timeout"() {
    setup:
    stubProductCall(productResponse().withFixedDelay(5000))

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == null
    verifyProductCall()
  }

  def "/product/{id} handles incomplete product response"() {
    setup:
    stubProductCall(aResponse()
      .withBody(objectMapper.writeValueAsString(productResponse))
      .withHeader("Content-type", "application/json"))

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == null
    verifyProductCall()

    where:
    productResponse << [[product: [item: [product_description: []]]], [product: [item: [:]]]]
  }

  def "/product/{id} calls redsky and returns product name and price"() {
    setup:
    stubProductCall(productResponse())
    savePrice(new MongoPrice('13860428', '$2.95', 'USD'))

    when:
    Map response = client.toBlocking().retrieve('/product/13860428', Map)

    then:
    response.id == "13860428"
    response.name == "The Big Lebowski (Blu-ray)"
    response.current_price.value == '$2.95'
    response.current_price.currency_code == 'USD'
    verifyProductCall()
  }

}
