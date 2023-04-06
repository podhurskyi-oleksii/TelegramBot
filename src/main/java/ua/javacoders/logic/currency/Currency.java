package ua.javacoders.logic.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD(840, "Долар", "$"),
    EUR(978, "Євро", "€"),
    UAH(980, "Гривня", "₴");

    private final int code;
    private final String name;
    private final String symbol;
}
