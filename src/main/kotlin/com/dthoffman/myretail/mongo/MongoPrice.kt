package com.dthoffman.myretail.mongo

import com.dthoffman.myretail.domain.CurrencyCode

data class MongoPrice(var id: String?, var value: String?, var currency_code: CurrencyCode?) {
    constructor() : this(null, null, null)
}