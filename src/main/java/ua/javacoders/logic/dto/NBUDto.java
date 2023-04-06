package ua.javacoders.logic.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import ua.javacoders.logic.currency.Currency;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NBUDto {
    private Currency cc;
    private BigDecimal rate;
}
