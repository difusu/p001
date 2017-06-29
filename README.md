# Project requirement
* maven 3.04 and above
* java 1.8

# Code Structure
## Web server class: jettyserver.WSSerer
## REST endpoint class: jettyserver.rest.Shops
## In memory data: jettyserver.data.CoffeeShops.
It contains a map of id -> LocationRecord, and a lookup table Name -> id.The data structure is singleton and is initialized at the first time to be accessed.

# Build project
In the project root directory run:
  mvn clean install package

# Start the web service server
In the project root directory run:
  java -cp target\location.jar jettyserver.WsServer

The web service server will be start at port 9999.

# Test APIs
## Create:
 Send a POST request to http://localhost:9999/shops with data as

  name=NAME, address=ADDRESS, lat=LATITUTE, lng=LONGTITUTE

  on success, return the id for the record. On failure for name existing, return 409

## Get by id:
  Send a GET request to http://localhost:9999/shops/id/ID, ID whould be an integer

  on success, return the found record. On failure for id not existing, return 404

## Get by name:
  Send a GET request to http://localhost:9999/shops/name/NAME

  on success, return the found record. On failure for name not existing, return 404

## Update:
  Send a PUT request to http://localhost:9999/shops/id/ID, with some of the following data set:

  name=NAME, address=ADDRESS, lat=LATITUTE, lng=LONGTITUTE

  on success, return the updated record. On failure, return 404

## Delete:
  Send a DELETE request to http://localhost:9999/shops/id/ID

## Find nearest coffee shop:
  Send a GET request to: http://localhost:9999/shops/address/ADDRESS, example: "http://localhost:9999/shops/address/252 Guerrero St, San Francisco, CA 94103, USA"

  example output:
  {
      "address": "375 Valencia St",
      "lng": -122.42195860692624,
      "name": "Four Barrel Coffee",
      "id": 28,
      "lat": 37.76702438676065
  }
