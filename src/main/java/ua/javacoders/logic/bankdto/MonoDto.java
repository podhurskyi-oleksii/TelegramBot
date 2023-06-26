package ua.javacoders.logic.bankdto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * The MonoDto class represents the exchange rate object from the Monobank API, which contains information
 * about currency codes and buy and sell rates.
 */
@Data
public class MonoDto {
    /**
     * currencyCodeA - Currency code A (UAH currency).
     * currencyCodeB - Currency code B (the currency in which the buy and sell rates of the main currency are indicated).
     * rateBuy - The buying rate of the main currency in currency B.
     * rateSell - The selling rate of the main currency in currency B.
     */
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
