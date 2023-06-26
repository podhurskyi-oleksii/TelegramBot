package ua.javacoders.logic.bankdto;

import lombok.Data;
import ua.javacoders.logic.currencies.Currency;

import java.math.BigDecimal;

/**
 * The PrivatDto class represents the exchange rate object from the Privatbank API, which contains information
 * about currency codes and buy and sell rates.
 */
@Data
public class PrivatDto {
    /**
     * ccy - Currency name
     * buy - The price at which the bank buys the currency
     * sell - The price at which the bank sells the currency
     */
    private Currency ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
