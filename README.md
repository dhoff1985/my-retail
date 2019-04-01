# My Retail Application
Aggregates product and price information for products

# Notes/Deviations/Outstanding issues
* Price update is /price/{id} instead of /product/{id} because it only updates price
* MongoPrice fields are nullable because Mongo PojoCodecProvider requires a no-arg constructor
however they will never be null.  

# Endpoint

## GET PRODUCT
### Path
```$xslt
GET /product/{id}
```

Returns product and price information for a given tcin

### Request Parameters
| Field | Description                    | Required |
| ------|--------------------------------|----------|
| id    | Product Identifier             | true     |

### Response Model
| Field                       | Description                    | Nullable |
| ----------------------------|--------------------------------|----------|
| id                          | Product Identifier             | false    |
| name                        | The name of the product        | true     |
| current_price               | The current price info         | true     |
| current_price.value         | The formatted price in dollars | false    |
| current_price.currency_code | Currency code of price (USD)   | false    |

## PUT Price
### Path
```$xslt
PUT /price/{id}
```

Adds/Updates a price

### Request Parameters
| Field | Description                    | Required |
| ------|--------------------------------|----------|
| id    | Product Identifier             | true     |

### Request Model
| Field            | Description                    | Required |
| -----------------|--------------------------------|----------|
| value            | The formatted price '$2.95'    | true     |
| currency_code    | currency code (USD)            | true     |




# Architecture
## Stack
* Language: [Kotlin](https://kotlinlang.org/) Modern Language
* Framework: [Micronaut](https://micronaut.io/) Optimized for Microservices
* Concurrency: [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) Readable and efficient
* Data Store: [MongoDB](https://www.mongodb.com/) 

## Testing
[Spock Framework](http://spockframework.org/)

### Test Types
#### Unit Tests

Unit tests are lightweight, quick tests. 
They should be small and test one specific subject with all dependencies mocked
   
#### Functional Tests

Functional tests test the complete application end to end.
They should test the running application mocking only external dependencies.
Functional tests should focus on testing the application at the feature level.
Each test should be focused on specific functional requirements.

## Local Development

### Start Mongo
The local development requires a running mongo instance
```$sh
bin/start-mongo.sh
```

### Build
```$sh
./gradlew clean build
```

### Running Locally
```$sh
./gradlew run
```

### Writing Test data
```$sh
bin/write-price.sh
```

### Intellij IDEA
enable annotation processing [intellij setup instructions](https://guides.micronaut.io/creating-your-first-micronaut-app-kotlin/guide/index.html)