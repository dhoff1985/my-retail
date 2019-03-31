package com.dthoffman.myretail.service

import com.dthoffman.myretail.BaseSpec
import com.dthoffman.myretail.domain.Price
import com.mongodb.reactivestreams.client.FindPublisher
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.core.async.publisher.Publishers
import kotlinx.coroutines.Dispatchers

import static com.mongodb.client.model.Filters.eq;

class PriceServiceSpec extends BaseSpec {

  MongoCollection<Price> mockPriceColleciton = Mock(MongoCollection)

  PriceService priceService = new PriceService(mockPriceColleciton, Dispatchers.Default)

  String tcin = "123"

  def "get price retrieves price from mongo"() {
    setup:
    Price price = new Price(tcin: tcin, price: '$2.95')

    when:
    Price result = sync {
      priceService.getPrice(tcin)
    }

    then:
    result == price
    1 * mockPriceColleciton.find({ it.toString() == eq('tcin', tcin).toString() }) >> (Publishers.just(price) as FindPublisher)
  }

}
