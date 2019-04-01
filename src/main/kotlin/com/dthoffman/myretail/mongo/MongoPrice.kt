package com.dthoffman.myretail.mongo

data class MongoPrice(var id: String?, var value: String?, var currency_code: String?) {
    constructor() : this(null, null, null)
}