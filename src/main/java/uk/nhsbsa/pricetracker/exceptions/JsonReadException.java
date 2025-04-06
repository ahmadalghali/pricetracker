package uk.nhsbsa.pricetracker.exceptions;

public class JsonReadException extends RuntimeException {
  public JsonReadException(String message, Throwable exception) {
    super(message, exception);
  }
}
