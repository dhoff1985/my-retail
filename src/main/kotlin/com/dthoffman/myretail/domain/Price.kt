package com.dthoffman.myretail.domain

import javax.validation.constraints.Pattern

data class Price(@field:Pattern(regexp = "\\\$\\d+\\.\\d{2}") val value: String, val currency_code: CurrencyCode)