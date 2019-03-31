package com.dthoffman.myretail.service

import com.dthoffman.myretail.domain.Price
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import spock.lang.Specification

import static com.mongodb.client.model.Filters.*;

class PriceServiceSpec extends Specification {

  MongoCollection<Price> mockPriceColleciton = Mock(MongoCollection)

  PriceService priceService = new PriceService(mockPriceColleciton)

  String tcin = "123"

  def "get price retrieves price from mongo" () {
    setup:
    Price price = new Price(tcin: tcin, price: '$2.95')
    FindIterable mockFindIterable = Mock(FindIterable)

    when:
    Price result = priceService.getPrice(tcin)

    then:
    result == price
    1 * mockPriceColleciton.find({
      it.toString() == eq('tcin', tcin).toString()
    }) >> mockFindIterable
    1 * mockFindIterable.first() >> price
  }
}
