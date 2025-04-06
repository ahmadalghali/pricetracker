package uk.nhsbsa.pricetracker.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productUrl) {
        super("Product not found at url: " + productUrl);
    }
}
