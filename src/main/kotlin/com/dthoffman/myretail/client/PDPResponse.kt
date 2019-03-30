package com.dthoffman.myretail.client

class PDPResponse(val product: PDPProduct)
class PDPProduct(val item: PDPItem)
class PDPItem(val product_description: ProductDescription)
class ProductDescription(val title: String)