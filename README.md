# Product Price Tracker with Custom Alerts - (Band 6 Developer - Assignment)

## About the Product Price Tracker with Custom Alerts

This is a RESTful API developed in **Java with Spring Boot** as part of the Band 6 Developer Assignment for the NHSBSA.  
It simulates product price tracking by fetching a static JSON file representing product prices and allows users to:

- Submit a product URL and their desired alert price.
- Receive a notification when the product price meets or drops below the threshold.
- Configure how often the system should check for price changes (e.g., daily, morning, midnight).

## Quick start

### Building the application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/ahamadalghali/pricetracker.git
   cd pricetracker
   ```
2. **Build using Maven**:
```bash
./mvnw clean install

```

### Running the application

Start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

### Example API Requests and Responses

1. Track a product and set a price alert with all arguments

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/AULA-Mechanical-Swappable-Pre-lubed-Reaper/dp/B0D14N2QZF/ref=sr_1_1
&desiredPrice=90
&userEmail=ahmad@gmail.com
&priceCheckFrequency=MORNING

```

Response:

```txt
Price alert has been set successfully.
```

2. Track a product and set a price alert without optional 'priceCheckFrequency' argument, (Default: MIDNIGHT)

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/AULA-Mechanical-Swappable-Pre-lubed-Reaper/dp/B0D14N2QZF/ref=sr_1_1
&desiredPrice=90
&userEmail=ahmad@gmail.com
```

Response:

```txt
Price alert has been set successfully.
```

3. Track a product with a set desired price more than the current price

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/Razer-Basilisk-Customizable-Ergonomic-Gaming/dp/B09C13PZX7/ref=sr_1_4
&desiredPrice=60
&userEmail=ahmad@gmail.com
&priceCheckFrequency=TWENTY_FOUR_HOURS
```

Response:

```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "The set desired price £60.00 needs to be less than the current product price (£53.66)",
    "instance": "/api/products/track"
}
```

4. Track a product with a set desired equal to the current price

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/Razer-Basilisk-Customizable-Ergonomic-Gaming/dp/B09C13PZX7/ref=sr_1_4
&desiredPrice=53.66
&userEmail=ahmad@gmail.com
&priceCheckFrequency=TWENTY_FOUR_HOURS
```

Response:

```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "The set desired price £53.66 needs to be less than the current product price (£53.66)",
    "instance": "/api/products/track"
}
```

5. Track a non-existing product

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://shouldreturnnoproductfound.com/123
&desiredPrice=14
&userEmail=ahmad@gmail.com
&priceCheckFrequency=MIDNIGHT
```

Response:

```json
{
    "type": "about:blank",
    "title": "Product not found",
    "status": 404,
    "detail": "Product not found at url: https://shouldreturnnoproductfound.com/123",
    "instance": "/api/products/track"
}
```

6. Track a product with missing required argument (desiredPrice)

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/Razer-Basilisk-Customizable-Ergonomic-Gaming/dp/B09C13PZX7/ref=sr_1_4
&userEmail=ahmad@gmail.com
&priceCheckFrequency=MIDNIGHT
```

Response:

```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "Required request parameter 'desiredPrice' for method parameter type Double is not present",
    "instance": "/api/products/track"
}
```

7. Track a product with missing required argument (userEmail)

Request:
```txt
http://localhost:8080/api/products/track?
productUrl=https://www.amazon.com/Razer-Basilisk-Customizable-Ergonomic-Gaming/dp/B09C13PZX7/ref=sr_1_4
&desiredPrice=14
&priceCheckFrequency=MIDNIGHT
```

Response:

```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "Required request parameter 'userEmail' for method parameter type String is not present",
    "instance": "/api/products/track"
}
```

8. Track a product with missing required argument (productUrl)

Request:
```txt
http://localhost:8080/api/products/track?
&userEmail=ahmad@gmail.com
&desiredPrice=14
&priceCheckFrequency=MIDNIGHT
```

Response:

```json
{
    "type": "about:blank",
    "title": "Internal Server Error",
    "status": 500,
    "detail": "Required request parameter 'productUrl' for method parameter type String is not present",
    "instance": "/api/products/track"
}
```


## Contributions

We operate a [code of conduct](CODE_OF_CONDUCT.md) for all contributors.

See our [contributing guide](CONTRIBUTING.md) for guidance on how to contribute.

## License

Released under the [Apache 2 license](LICENCE.txt).
