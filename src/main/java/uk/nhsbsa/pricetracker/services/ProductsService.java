package uk.nhsbsa.pricetracker.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import uk.nhsbsa.pricetracker.enums.PriceCheckFrequency;
import uk.nhsbsa.pricetracker.exceptions.InvalidDesiredPriceException;
import uk.nhsbsa.pricetracker.exceptions.JsonReadException;
import uk.nhsbsa.pricetracker.exceptions.ProductNotFoundException;
import uk.nhsbsa.pricetracker.models.Product;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;
import uk.nhsbsa.pricetracker.services.alert.AlertService;

@Service
@Slf4j
public class ProductsService {

  private final AlertService alertService;
  private final SchedulingService schedulingService;

  public ProductsService(
      @Qualifier("consoleLoggerAlertService") AlertService alertService,
      SchedulingService schedulingService) {
    this.alertService = alertService;
    this.schedulingService = schedulingService;
  }

  public void trackProductPrice(TrackProductCommand command) {
    Double currentPrice = command.product().getPrice();
    if (currentPrice <= command.desiredPrice()) {
      throw new InvalidDesiredPriceException(command.desiredPrice(), currentPrice);
    } else {

      var cronTrigger = new CronTrigger(getCronExpression(command.priceCheckFrequency()));
      //  TODO: have option to send via all channels by iterating on the alertService
      // implementations
      schedulingService.schedule(() -> alertService.sendPriceDropAlert(command), cronTrigger);
    }
  }

  private String getCronExpression(PriceCheckFrequency frequency) {
    switch (frequency) {
      case MORNING:
        return "0 0 8 * * *"; // 8:00 am every day
      case TWENTY_FOUR_HOURS:
        {
          LocalTime localTime = LocalTime.now();
          return String.format("0 %d %d * * *", localTime.getMinute(), localTime.getHour());
        }
      case null, default:
        return "0 0 0 * * *"; // Midnight everyday
    }
  }

  public Product getProduct(String productUrl) {

    return loadProductsData().stream()
        .filter(product -> product.getUrl().equals(productUrl))
        .findFirst()
        .orElseThrow(() -> new ProductNotFoundException(productUrl));
  }

  List<Product> loadProductsData() {
    //    TODO: area for improvement, add path to application.properties and don't hardcode values
    return loadProductsData("/static/data/products.json");
  }

  List<Product> loadProductsData(String path) {
    ObjectMapper mapper = new ObjectMapper();

    try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
      List<Product> productList =
          mapper.readValue(inputStream, new TypeReference<List<Product>>() {});
      return productList;
    } catch (IOException e) {
      log.warn("Failed to load products data");
      throw new JsonReadException("Could not read Products Data from classpath", e);
    }
  }
}
