package uk.nhsbsa.pricetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceTrackerApplication.class, args);
	}

}
