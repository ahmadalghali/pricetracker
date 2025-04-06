package uk.nhsbsa.pricetracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.nhsbsa.pricetracker.enums.PriceCheckFrequency;
import uk.nhsbsa.pricetracker.exceptions.InvalidDesiredPriceException;
import uk.nhsbsa.pricetracker.exceptions.JsonReadException;
import uk.nhsbsa.pricetracker.models.Product;
import uk.nhsbsa.pricetracker.requests.TrackProductCommand;
import uk.nhsbsa.pricetracker.services.alert.AlertService;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTests {

  @InjectMocks ProductsService productsService;

  @Mock AlertService alertService;
  @Mock SchedulingService schedulingService;

  @BeforeEach
  void setUp() {
    productsService = new ProductsService(alertService, schedulingService);
  }

  @Nested
  class PriceChecks {

    @Test
    @DisplayName("should set alert on valid arguments provided")
    void shouldSetAlertOnValidArgumentsProvided() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 40.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 30.0, PriceCheckFrequency.MIDNIGHT);

      // When
      productsService.trackProductPrice(command);

      // Then
      verify(alertService).sendPriceDropAlert(command);
    }

    @Test
    @DisplayName("should throw error when desired price more than current price")
    void shouldThrowErrorWhenDesiredPriceMoreThanCurrentPrice() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 30.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 40.0, PriceCheckFrequency.MIDNIGHT);

      // When

      // Then
      InvalidDesiredPriceException ex =
          assertThrows(
              InvalidDesiredPriceException.class,
              () -> {
                productsService.trackProductPrice(command);
              });

      assertEquals(
          "The set desired price £40.00 needs to be less than the current product price (£30.00)",
          ex.getMessage());
    }
  }

  @Nested
  class ProductDataTests {

    @Test
    @DisplayName("should throw error when desired price is equal to the current price")
    void shouldThrowExceptionWhenDesiredPriceIsEqualToCurrentPrice() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 30.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 30.0, PriceCheckFrequency.MIDNIGHT);

      // When

      // Then
      InvalidDesiredPriceException ex =
          assertThrows(
              InvalidDesiredPriceException.class,
              () -> {
                productsService.trackProductPrice(command);
              });

      assertEquals(
          "The set desired price £30.00 needs to be less than the current product price (£30.00)",
          ex.getMessage());
    }

    @Test
    @DisplayName("should throw exception if unsuccessful retrieval of product data")
    void shouldThrowExceptionIfUnsuccessfulRetrievalOfProductData() {
      // Given

      // When

      // Then
      JsonReadException ex =
          assertThrows(
              JsonReadException.class,
              () -> {
                productsService.loadProductsData("/static/data/malformed-products.json");
              });

      assertEquals("Could not read Products Data from classpath", ex.getMessage());
    }
  }

  @Nested
  class AlertTests {

    @Test
    @DisplayName("should set alert upon successful request")
    void shouldSetAlertUponSuccessfulRequest() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 30.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 20.0, PriceCheckFrequency.MIDNIGHT);

      // When
      productsService.trackProductPrice(command);

      // Then
      verify(alertService).sendPriceDropAlert(command);
    }

    @Test
    @DisplayName("should not set alert when desired price more than current price")
    void shouldNotSetAlertWhenDesiredPriceMoreThanCurrentPrice() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 20.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 30.0, PriceCheckFrequency.MIDNIGHT);

      // When
      assertThrows(
          InvalidDesiredPriceException.class,
          () -> {
            productsService.trackProductPrice(command);
          });

      // Then
      verify(alertService, Mockito.never()).sendPriceDropAlert(command);
    }

    @Test
    @DisplayName("should not set alert when desired price is equal t current price")
    void shouldNotSetAlertWhenDesiredPriceIsEqualToCurrentPrice() {
      // Given
      Product product = new Product("https://amazon.com/products/123", 20.0, "Samsung Microwave");
      var command =
          new TrackProductCommand("john@gmail.com", product, 20.0, PriceCheckFrequency.MIDNIGHT);

      // When

      assertThrows(
          InvalidDesiredPriceException.class,
          () -> {
            productsService.trackProductPrice(command);
          });

      // Then
      verify(alertService, Mockito.never()).sendPriceDropAlert(command);
    }
  }
}
