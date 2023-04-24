package ua.javacoders.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class User {
    private long chatId;
    private List<Currency> currency;
    private List<Bank> banks;

    public User(long chatId) {
        this.chatId = chatId;
        currency = new ArrayList<>();
        banks = new ArrayList<>();
    }
}