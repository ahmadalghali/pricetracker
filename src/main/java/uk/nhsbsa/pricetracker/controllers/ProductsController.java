package uk.nhsbsa.pricetracker.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.nhsbsa.pricetracker.enums.PriceCheckFrequency;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;
import uk.nhsbsa.pricetracker.services.ProductsService;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductsController {

  private final ProductsService productsService;

  public ProductsController(ProductsService productsService) {
    this.productsService = productsService;
  }

  @PostMapping("/track")
  public ResponseEntity<String> trackProductPrice(
      @RequestParam String productUrl,
      @RequestParam Double desiredPrice,
      @RequestParam String userEmail,
      @RequestParam(required = false, defaultValue = "MIDNIGHT")
          PriceCheckFrequency priceCheckFrequency) {

    var product = productsService.getProduct(productUrl);
    var command = new TrackProductCommand(userEmail, product, desiredPrice, priceCheckFrequency);
    productsService.trackProductPrice(command);

    return ResponseEntity.ok("Price alert has been set successfully.");
  }
}
