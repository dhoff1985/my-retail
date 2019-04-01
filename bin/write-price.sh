#!/bin/bash

curl -H 'Content-type: application/json' -X PUT -d '{"value": "$2.95", "currency_code": "USD"}' 'http://localhost:8080/price/13860428'