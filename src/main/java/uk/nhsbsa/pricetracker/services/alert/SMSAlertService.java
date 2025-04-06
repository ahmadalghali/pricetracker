package uk.nhsbsa.pricetracker.services.alert;

import org.springframework.stereotype.Service;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;

@Service("smsAlertService")
public class SMSAlertService implements AlertService{

    @Override
    public void sendPriceDropAlert(TrackProductCommand command) {
        // Send alert via SMS
    }
}
