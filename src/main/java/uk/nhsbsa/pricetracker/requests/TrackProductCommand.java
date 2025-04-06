package uk.nhsbsa.pricetracker.requests;

import uk.nhsbsa.pricetracker.enums.PriceCheckFrequency;
import uk.nhsbsa.pricetracker.models.Product;

public record TrackProductCommand(
        String userEmail,
        Product product,
        Double desiredPrice,
        PriceCheckFrequency priceCheckFrequency) {}
