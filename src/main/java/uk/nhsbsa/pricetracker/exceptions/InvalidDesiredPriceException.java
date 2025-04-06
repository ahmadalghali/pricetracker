package uk.nhsbsa.pricetracker.exceptions;

import uk.nhsbsa.pricetracker.utils.CurrencyFormatter;

public class InvalidDesiredPriceException extends RuntimeException {

    public InvalidDesiredPriceException(Double desiredPrice, Double currentPrice) {
        this(CurrencyFormatter.format(desiredPrice), CurrencyFormatter.format(currentPrice));
    }

    public InvalidDesiredPriceException(String formattedDesiredPrice, String formattedCurrentPrice) {
        super("The set desired price %s needs to be less than the current product price (%s)"
                .formatted(formattedDesiredPrice, formattedCurrentPrice));
    }
}
