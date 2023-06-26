package ua.javacoders.logic.bankdto;

import lombok.Data;
import ua.javacoders.logic.currencies.Currency;

import java.math.BigDecimal;

/**
 * The OschadDto class represents the exchange rate object from the <a href="https://www.oschadbank.ua/currency-rate">OschadBank</a>,
 * which contains information about currency codes and buy and sell rates.
 */
@Data
public class OschadDto {
    /**
     * currency - Currency name
     * rateBuy - The price at which the bank buys the currency
     * rateSell - The price at which the bank sells the currency
     */
    private Currency currency;
    private BigDecimal rateBuy;
    private BigDecimal rateSell;
}
