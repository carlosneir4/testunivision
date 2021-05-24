# widgets
Widgets is a spring boot application to make asynchronous calls to an external resource,it deserialize the json response into own models and then expose them in a endpoint.

#### Full Documentation

- RXJava  [more info...](https://github.com/ReactiveX/RxJava)
- Lombok [more info...](https://projectlombok.org/features/all) [How to configure...](https://www.baeldung.com/lombok-ide)
- HttpClientCache [more info...](http://www.ehcache.org/documentation/3.7/)
- Java 8

#### Getting started

```
$ git clone https://github.com/carlosneir4/widgets.git
$ cd widgets/
```

We need to install maven or add it to our IDE, java8 and also we should configure lombok to run JUnits.

#### Build
Java
```
$ mvn compile test package
$ java -jar target/widgets-0.0.1-SNAPSHOT.jar
```
Maven
```
$ mvn compile test package
$ mvn spring-boot:run
```
Docker
```
$ docker build -t testunivision:1.0-SNAPSHOT .
$ docker run -d -p 8080:8080 testunivision:1.0-SNAPSHOT
```
#### Local RestApi endpoints

```
http://localhost:8080/univision/widgets?path=news
http://localhost:8080/univision/widgets
```




