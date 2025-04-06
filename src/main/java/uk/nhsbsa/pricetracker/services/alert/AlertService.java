package uk.nhsbsa.pricetracker.services.alert;

import uk.nhsbsa.pricetracker.requests.TrackProductCommand;

public interface AlertService {
    void sendPriceDropAlert(TrackProductCommand command);
}
