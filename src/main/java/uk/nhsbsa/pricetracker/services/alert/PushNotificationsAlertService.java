package uk.nhsbsa.pricetracker.services.alert;

import org.springframework.stereotype.Service;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;


@Service("pushNotificationsAlertService")
public class PushNotificationsAlertService implements AlertService {

    @Override
    public void sendPriceDropAlert(TrackProductCommand command) {
    // Send alert via push notification
    }
}
