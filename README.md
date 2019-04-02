# My Retail Application
Aggregates product and price information for products

# Notes/Deviations
* Price update is /price/{id} instead of /product/{id} 
    * /product is an aggregate endpoint
    * It is more idomatic to update at the domain level
    * Realistically a price would be it's own microservice with it's own put endpoint
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

## Tech Stack
* Language: [Kotlin](https://kotlinlang.org/) Modern Language
* Framework: [Micronaut](https://micronaut.io/) Optimized for Microservices
* Concurrency: [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) Readable and efficient
* Data Store: [MongoDB](https://www.mongodb.com/) 

# Architecture
## Principles
[Controller] -> [Service] -> [Data/HTTP]
* Separate technology details from business logic
### Controllers
* Translate HTTP Requests to Service tier calls. 
* Can reference an individual service. 
* Cannot reference multiple services or data/http clients.
### Services
* Home to business logic
* Interact with data/http clients
* Services can also interact with other services. 
* Services should not reference controllers
### Data/Http Clients
* Owned and used exclusively by 1 service.
* Do not interact with services and do not interact with controllers.


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