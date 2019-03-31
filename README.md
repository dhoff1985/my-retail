# My Retail Application
Aggregates product and price information for products

# Endpoint

## GET PRODUCT
### Path
```$xslt
GET /product/{tcin}
```

Returns product and price information for a given tcin

### Request Parameters
| Field | Description                    | Required |
| ------|--------------------------------|----------|
| tcin  | Product Identifier             | true     |

### Model
| Field | Description                    | Nullable |
| ------|--------------------------------|----------|
| tcin  | Product Identifier             | false    |
| name  | The name of the product        | true     |
| price | The formatted price in dollars | true     |


# Architecture
## Stack
* Language: [Kotlin](https://kotlinlang.org/)
* Framework: [Micronaut](https://micronaut.io/)
* Concurrency: [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
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

## Building
```$sh
./gradlew clean build
```

## Running
```$sh
./gradlew run
```

## Intellij IDEA
enable annotation processing [intellij setup instructions](https://guides.micronaut.io/creating-your-first-micronaut-app-kotlin/guide/index.html)