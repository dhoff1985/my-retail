package com.dthoffman.myretail.functional

import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.mongo.MongoPrice
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.exceptions.HttpClientResponseException
import spock.lang.Unroll

import static com.mongodb.client.model.Filters.eq

class PriceFunctionalSpec extends BaseFunctionalSpec {

  def "put to price saves price"() {
    when:
    client.toBlocking().exchange(HttpRequest.PUT('/price/13860428', new Price('$3.95', 'USD')))

    then:
    MongoPrice price = getPriceCollection().find(eq('_id', '13860428')).first()
    price.id == "13860428"
    price.value == '$3.95'
    price.currency_code == 'USD'
  }

  def "put to price updates price"() {
    setup:
    savePrice(new MongoPrice(id: "13860429", value: '$2.95'))

    when:
    client.toBlocking().exchange(HttpRequest.PUT('/price/13860429', new Price('$3.95', 'USD')))

    then:
    getPriceCollection().countDocuments(eq('_id', '13860429')) == 1
    MongoPrice price = getPriceCollection().find(eq('_id', '13860429')).first()
    price.id == "13860429"
    price.value == '$3.95'
    price.currency_code == 'USD'
  }

  @Unroll
  def "rejects incomplete requests"() {
    when:
    client.toBlocking().exchange(HttpRequest.PUT('/price/13860429', priceBody))

    then:
    HttpClientResponseException e = thrown(HttpClientResponseException)
    e.response.status().code == 400
    getPriceCollection().countDocuments() == 0

    where:
    priceBody << [[:], [currency_code: 'USD'], [value: '$3.95'], [currency_code: '', value: '$3.95'], [currency_code: 'USD', value: "abc"]]
  }
}
