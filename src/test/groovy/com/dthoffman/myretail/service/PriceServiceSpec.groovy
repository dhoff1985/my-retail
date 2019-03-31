package com.dthoffman.myretail.service

import com.dthoffman.myretail.BaseSpec
import com.dthoffman.myretail.domain.Price
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.Dispatchers

import static com.mongodb.client.model.Filters.eq;

class PriceServiceSpec extends BaseSpec {

  MongoCollection<Price> mockPriceColleciton = Mock(MongoCollection)

  PriceService priceService = new PriceService(mockPriceColleciton, Dispatchers.Default)

  String tcin = "123"

  def "get price retrieves price from mongo"() {
    setup:
    Price price = new Price(tcin: tcin, price: '$2.95')
    FindIterable mockFindIterable = Mock(FindIterable)

    when:
    Price result = sync {
      priceService.getPrice(tcin)
    }

    then:
    result == price
    1 * mockPriceColleciton.find({
      it.toString() == eq('tcin', tcin).toString()
    }) >> mockFindIterable
    1 * mockFindIterable.first() >> price
  }

}
