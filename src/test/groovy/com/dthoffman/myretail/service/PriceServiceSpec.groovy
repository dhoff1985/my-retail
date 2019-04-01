package com.dthoffman.myretail.service

import com.dthoffman.myretail.BaseSpec
import com.dthoffman.myretail.domain.Price
import com.dthoffman.myretail.mongo.MongoPrice
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.reactivestreams.client.FindPublisher
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.core.async.publisher.Publishers
import kotlinx.coroutines.Dispatchers

import static com.mongodb.client.model.Filters.eq;

class PriceServiceSpec extends BaseSpec {

  MongoCollection<MongoPrice> mockPriceColleciton = Mock(MongoCollection)

  PriceService priceService = new PriceService(mockPriceColleciton, Dispatchers.Default)

  String id = "123"

  Price price = new Price('$2.95', "USD")

  MongoPrice mongoPrice = new MongoPrice(id: id, value: '$2.95', currency_code: "USD")

  def "get price retrieves price from mongo"() {
    when:
    Price result = sync {
      priceService.getPrice(id)
    }

    then:
    result == price
    1 * mockPriceColleciton.find({ it.toString() == eq('_id', id).toString() }) >> (Publishers.just(mongoPrice) as FindPublisher)
  }

  def "save price saves price in mongo"() {
    when:
    Price result = sync {
      priceService.savePrice(id, price)
    }

    then:
    result == price
    1 * mockPriceColleciton.replaceOne(
      { it.toString() == eq('_id', id).toString() },
      mongoPrice,
      { it.toString() == new ReplaceOptions().upsert(true).toString() } ) >> (Publishers.just(price) as FindPublisher)
  }

}
