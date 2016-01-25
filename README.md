Currency Api
==============

> A JSON RESTFul Api that provides foreign exchange rates.

* **/currencies** - Return actual currencies.

* **/currencies/:currency** Return the exchange rate for specified currency. i.e: /currencies/BRL  

## Motivation
I'm using this project for experimental purposes. So I'm using some lightweight frameworks and tools to lets project's structure and code simple, easy to read and with **no magical things**.

## Technologies

### Spark Framework
> Spark - A tiny Sinatra inspired framework for creating web applications in Java 8 with minimal effort.
http://sparkjava.com/

### Jodd - HTTP
> Jodd is set of Java micro frameworks, tools and utilities, under 1.5 MB. Designed with common sense to make things simple, but not simpler.
http://jodd.org/doc/http.html

### Google Guava - CacheLoader
> A simple in memory cache api.
Google Guava - https://github.com/google/guava/tree/a9f8b899c07a33c2203b4e6cf84861646952aeed

## Tests
```sh
# Test - Run all project's tests
gradle test
```

## Running
```sh
# Run - Starts a HTTP Server at http://localhost:4567 
gradle run
```

