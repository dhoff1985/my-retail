package com.dthoffman.myretail.functional

import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.mongo.MongoPrice
import io.micronaut.http.HttpRequest

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
}
