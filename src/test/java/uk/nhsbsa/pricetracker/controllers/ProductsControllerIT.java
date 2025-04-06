package uk.nhsbsa.pricetracker.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.nhsbsa.pricetracker.enums.PriceCheckFrequency;
import uk.nhsbsa.pricetracker.models.Product;
import uk.nhsbsa.pricetracker.services.ProductsService;


@WebMvcTest(ProductsController.class)
class ProductsControllerIT {

  @Autowired MockMvc mockMvc;

  @MockitoBean ProductsService productsService;

  @Test
  @DisplayName("shouldReturnSetAlertMessageUponSuccessfulRequest")
  void shouldReturnSetAlertMessageUponSuccessfulRequest() throws Exception {
    // Given
    String productUrl = "https://someurll.com/123";
    Double desiredPrice = 350.0;
    String userEmail = "john@gmail.com";

    Product mockProduct = new Product(productUrl, 400.0, "Dell Laptop");

    when(productsService.getProduct(productUrl)).thenReturn(mockProduct);

    // When + Then
    mockMvc
        .perform(
            post("/api/products/track")
                .param("productUrl", productUrl)
                .param("desiredPrice", String.valueOf(desiredPrice))
                .param("userEmail", userEmail)
                .param("priceCheckFrequency", PriceCheckFrequency.MORNING.toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(content().string("Price alert has been set successfully."));
  }


  @Test
  @Disabled("Needs to be implemented, currently throws 500")
  @DisplayName("shouldReturnBadRequestWhenProductUrlIsMissing")
  void shouldReturnBadRequestWhenProductUrlIsMissing() throws Exception {
    mockMvc.perform(post("/api/products/track")
                    .param("desiredPrice", "100.0")
                    .param("userEmail", "john@gmail.com")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isBadRequest());
  }
}
