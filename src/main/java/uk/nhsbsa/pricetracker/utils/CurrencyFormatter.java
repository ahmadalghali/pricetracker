package uk.nhsbsa.pricetracker.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String format(Double value) {
        return NumberFormat.getCurrencyInstance(Locale.UK).format(value);
    }
}
