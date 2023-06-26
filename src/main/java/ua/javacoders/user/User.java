package ua.javacoders.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user and stores their preferences for currency rates and banks.
 * It contains a unique chat ID, a list of currencies the user is interested in, and a list of banks.
 */
@Data
@AllArgsConstructor
public class User {
    private long chatId;
    private List<Currency> currencies;
    private List<Bank> banks;

    /**
     * Initializes a new instance of the User class with a given chat ID.
     * Creates empty lists for currencies and banks.
     *
     * @param chatId the unique chat ID of the user
     */
    public User(long chatId) {
        this.chatId = chatId;
        currencies = new ArrayList<>();
        banks = new ArrayList<>();
    }
}