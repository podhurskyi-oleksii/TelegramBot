package ua.javacoders.users;

import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currency.Currency;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final UsersStorage storage;
    public ConcurrentHashMap<Long, User> users;


    public UserService() {
        storage = new UsersStorage();
        if (storage.loadUsers().isEmpty()) {
            users = new ConcurrentHashMap<>();
        } else {
            users = storage.loadUsers();
        }
    }

    public void addUser(User user) {
        users.put(user.getChatId(), user);
        storage.saveUsers(users);
    }

    public boolean isNewUser(long chatId) {
        return !users.containsKey(chatId);
    }

    public User getUser(long chatId) {
        return users.get(chatId);
    }

    public void createNewUser(long chatId) {
        if (isNewUser(chatId)) {
            User newUser = new User(chatId);
            users.put(chatId, newUser);
        }
    }

    public void updateBank(User user, Bank bank) {
        List<Bank> userBanks = user.getBanks();
        if (userBanks.contains(bank)) {
            userBanks.remove(bank);
        } else {
            userBanks.add(bank);
        }
        addUser(user);
    }

    public void updateCurrency(User user, Currency currency) {
        List<Currency> userCurrencies = user.getCurrency();
        if (userCurrencies.contains(currency)) {
            userCurrencies.remove(currency);
        } else {
            userCurrencies.add(currency);
        }
        addUser(user);
    }
}