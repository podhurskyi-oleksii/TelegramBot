package ua.javacoders.logic.bankdto;

import lombok.Data;
import ua.javacoders.logic.currencies.Currency;

import java.math.BigDecimal;

/**
 * The NBUDto class represents the exchange rate object from the NBU API, which contains information
 * about currency, buy and sell rates.
 */
@Data
public class NBUDto {
    /**
     * cc - Currency name
     * rate - Official rate for buying and selling currency from National Bank of Ukraine
     */
    private Currency cc;
    private BigDecimal rate;
}
