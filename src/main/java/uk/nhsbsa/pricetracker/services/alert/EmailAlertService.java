package uk.nhsbsa.pricetracker.services.alert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;
import uk.nhsbsa.pricetracker.utils.CurrencyFormatter;

@Slf4j
@Service("emailAlertService")
public class EmailAlertService implements AlertService{
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailAlertService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendPriceDropAlert(TrackProductCommand command) {
        String subject = "Price drop alert for %s".formatted(command.product().getName());
        var formattedDesiredPrice = CurrencyFormatter.format(command.desiredPrice());
        var formattedCurrentPrice = CurrencyFormatter.format(command.product().getPrice());
        String body = """
                Hello,
                
                The price of (%s) has dropped to %s and meets the desired price threshold you have set of %s,
                you can visit and purchase the product by clicking on the following link:
                
                %s
                
                Thank you,
                NHSBSA Price Alerts
                """.formatted(command.product().getName(), formattedCurrentPrice, formattedDesiredPrice, command.product().getUrl());

        sendEmail(command.userEmail(), subject, body);
    }
}
