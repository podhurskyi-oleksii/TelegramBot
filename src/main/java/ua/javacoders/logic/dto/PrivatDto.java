package ua.javacoders.logic.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import ua.javacoders.logic.currency.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PrivatDto {
    private Currency ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
