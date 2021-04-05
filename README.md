# wish-list
Is a Spring Boot based application exposing a REST API to CRUD a wish list.

## Tech stack
The application is based on the following projects:

* **Spring boot** 2.5.0-SNAPSHOT
* **Spring Data - Mongo** 3.2.0-M5
* **JUnit** 4.13.2
* **Mockito** 3.8.0
* **Maven** 3.8.1
* **Project Lombok** 1.18.18
* **Java** 15

## Software Principles
* Clean Architecture: software design principles that allow for SOLID layering of the app, making the app scalable, agile and robust.
* Test-Driven Development: technique that allows abstractions to be readily tested, as a way to ensure robust feature development, allowing failures to happen as soon as possible, so they can be fixed before detailed implementation

## How To Run Locally
* Make sure you have Java, Maven and MongoDB installed.
----
* Start local mongodb.service.

* $ sudo systemctl start mongodb <1>
* $ sudo systemctl status mongodb <2>

<1> Active mongodb.service.
<2> Check mongodb.service is running.
----
* Run the backend application.  
----
* $ git clone https://github.com/andrefalvesp/wishlist <1>
* $ cd wishlist <2>
* $ mvn spring-boot:run <3>
----
<1> Clone the repository.
<2> Switch to the `wishlist` folder (root folder, same as pom.xml)
<3> Run the application
----
Your app should now be running on [localhost:8081](http://localhost:8081/).
Gracefully shutdown hit `ctrl+c`.

## How to Call Endpoints - Examples 

`$ GET /wishlist/item HTTP/1.1
Host: localhost:8081
Content-Type: application/json
customerId: 1234567890
storeId: L_NETSHOES`

`$ GET /wishlist/item/SKU1 HTTP/1.1
Host: localhost:8081
Content-Type: application/json
customerId: 1234567890
storeId: L_NETSHOES`

`$ POST /wishlist/item HTTP/1.1
Host: localhost:8081
Content-Type: application/json
customerId: 1234567890
storeId: L_NETSHOES
productId: SKU1`

`$ DELETE /wishlist/item HTTP/1.1
Host: localhost:8081
Content-Type: application/json
customerId: 1234567890
storeId: L_NETSHOES
productId: SKU1`
