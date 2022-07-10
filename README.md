# shopping-cart

The program is built in Java ("17.0.3.1") and Spring Boot 2.
It provides the function to calculate the cost of the orders with promotions.
It exposes the REST API (/checkout) as enter point, referring to the example below for details.

## Design
To simplify the demo application,
* H2 database is used to store the inventory items.
* If the item sku doesn't exist, an error message will be returned.
* If the inventory quantity is less than the order quantity, an error message will be returned.
* The "order" is the only required parameter in request.
* The "order" string must be split with comma if there are many items.
* The value in the order string must be the SKU of a specific item existing in database.
* Response will be a string format.
* The result rounds to 2 decimal places.

## Running code
To run the program in MacOS/Linux:

```aidl
./mvnw spring-boot:run
```

To run the test cases:

```aidl
mvn test
```

## Example
Use the API to get the result.
```aidl
GET http://localhost:8080/checkout?order=43N23P,234234,120P90,120P90,120P90,A304SD,A304SD,A304SD
```

Response
```aidl
$5795.62
```

## Docker

Make sure the jar file is created under the target folder:
```aidl
target/shopping-cart-0.0.1-SNAPSHOT.jar
```

Build docker image:
```aidl
docker build -t kinvinjin/shopping-cart-docker .
```

Run image:
```aidl
docker run -p 8080:8080 kinvinjin/shopping-cart-docker
```