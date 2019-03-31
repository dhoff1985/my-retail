package com.dthoffman.myretail.functional

import com.dthoffman.myretail.domain.Price
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.bson.codecs.configuration.CodecRegistry
import org.junit.Rule
import spock.lang.Specification

import javax.inject.Inject

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.client.WireMock.verify
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig


@MicronautTest
class BaseFunctionalSpec extends Specification {

  @Inject
  @Client('/')
  HttpClient client

  @Inject
  MongoClient mongoClient

  @Inject
  CodecRegistry codecRegistry

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089))

  String PRODUCT_URL = '/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics'

  ObjectMapper objectMapper = new ObjectMapper()

  def cleanup() {
    dropDp()
  }

  private MongoDatabase getMyRetailMongoDatabase() {
    mongoClient.getDatabase('myRetail').withCodecRegistry(codecRegistry)
  }

  void savePrice(Price price) {
    getMyRetailMongoDatabase().getCollection('price', Price).insertOne(price)
  }

  void dropDp() {
    getMyRetailMongoDatabase().drop()
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

    wireMockRule.stubFor(get(urlEqualTo(PRODUCT_URL))
      .willReturn(aResponse()
      .withBody(objectMapper.writeValueAsString(productResponse))
      .withHeader("Content-type", "application/json")))
  }

  void verifyProductCall() {
    verify(getRequestedFor(urlEqualTo(PRODUCT_URL)))
  }
}