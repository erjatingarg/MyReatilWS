# MyReatilWS

MyRetail Restful service

# Technologies

JDK 1.8.0

No SQL Database ( Cassandra 2.2.12 )

Maven 3.5.3

SpringBoot 1.4.2.RELEASE

EhCache 

# Cassandra database and collection

Cassandra host = localhost

KEYSPACE myretail

Table name = product



CREATE KEYSPACE myretail WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE myretail.product(id int PRIMARY KEY, price decimal, currency text);

INSERT INTO myretail.product (id, price, currency) VALUES(13860428, 56.78, 'USD');


# Build, Test and Run application 



Run MyRetail application :
1)Start Cassandra Server
./cassandra -f
./cqlsh



2) Calling myreail API:-

cd myretail

mvn clean package

java -jar target/myretail-0.0.1-SNAPSHOT.jar

Application will start running on port 8080


Endpoints for MyRetail operations:

-----------------------
1)Get Product:
HTTP Method : GET
http://localhost:8080/myretail/products/{productId}
productId = 13860428

Content-Type: application/json

Sample Response:
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray) (Widescreen)",
    "priceDetails": {
        "productId": 13860428,
        "value": 200,
        "currencyCode": "USD"
    }
}
-----------------------
2) Update Price:
HTTP Method : PUT
http://localhost:8080/myretail/products/{productId}
productId = 13860428

Headers:
Content-Type: application/json
Accept: application/json

Request Body:-
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray) (Widescreen)",
    "priceDetails": {
        "productId": 13860428,
        "value": 1000,
        "currencyCode": "USD"
    }
}

Response Body:-
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray) (Widescreen)",
    "priceDetails": {
        "productId": 13860428,
        "value": 1000,
        "currencyCode": "USD"
    }
}

3) Not Found
http://localhost:8080/myretail/products/100
Status: 404

