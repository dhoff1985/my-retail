package com.dthoffman.myretail.functional

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.junit.WireMockRule
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.Rule
import spock.lang.Specification

import javax.inject.Inject

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

@MicronautTest
class ProductFunctionalSpec extends Specification {

  String productUrl = '/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics'

  ObjectMapper objectMapper = new ObjectMapper()

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

  @Inject
  @Client('/')
  HttpClient client

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

  void stubProductCall() {

    Map productResponse = [
      product: [
        item: [
          product_description: [
            title: "The Big Lebowski (Blu-ray)"
          ]
        ]
      ]
    ]

    wireMockRule.stubFor(get(urlEqualTo(productUrl))
      .willReturn(aResponse()
      .withBody(objectMapper.writeValueAsString(productResponse))
      .withHeader("Content-type", "application/json")))
  }
  void verifyProductCall() {
    verify(getRequestedFor(urlEqualTo(productUrl)))
  }
}
