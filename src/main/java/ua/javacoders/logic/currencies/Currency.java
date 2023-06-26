package ua.javacoders.logic.currencies;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Currency enumeration contains the currencies used in the application with their codes,
 * names, and symbols.
 */
@AllArgsConstructor
@Getter
public enum Currency {
    USD(840, "Долар", "$"),
    EUR(978, "Євро", "€"),
    UAH(980, "Гривня", "₴");

    private final int code;
    private final String name;
    private final String symbol;
}
