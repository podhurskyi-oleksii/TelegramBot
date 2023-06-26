package ua.javacoders.user;

import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The UserService class is responsible for managing user operations and storing user data.
 * It provides methods for creating, updating, and retrieving user information.
 */
public class UserService {

    private final UsersStorage storage;
    private final ConcurrentHashMap<Long, User> users;

    /**
     * Constructs a UserService instance and initializes the storage and users objects.
     */
    public UserService() {
        storage = new UsersStorage();
        if (storage.loadUsers().isEmpty()) {
            users = new ConcurrentHashMap<>();
        } else {
            users = storage.loadUsers();
        }
    }

    /**
     * Creates a new user with the specified chatId if the user does not exist.
     *
     * @param chatId the chatId of the new user
     */
    public void createNewUser(long chatId) {
        if (isNewUser(chatId)) {
            User newUser = new User(chatId);
            users.put(chatId, newUser);
        }
    }

    /**
     * Checks if a user with the specified chatId exists in the users' collection.
     *
     * @param chatId the chatId to check for user existence
     * @return true if the user exists, false otherwise
     */
    public boolean isNewUser(long chatId) {
        return !users.containsKey(chatId);
    }

    /**
     * Retrieves the user with the specified chatId from the users' collection.
     *
     * @param chatId the chatId of the user to retrieve
     * @return the User object with the specified chatId, or null if not found
     */
    public User getUser(long chatId) {
        return users.get(chatId);
    }

    /**
     * Updates the banks for the specified user.
     * If the bank is already in the user's bank list, it is removed. Otherwise, it is added.
     *
     * @param user the user to update the bank for
     * @param bank the bank to add or remove from the user's bank list
     */
    public void updateBank(User user, Bank bank) {
        List<Bank> userBanks = user.getBanks();
        if (userBanks.contains(bank)) {
            userBanks.remove(bank);
        } else {
            userBanks.add(bank);
        }
        addUser(user);
    }

    /**
     * Updates the currencies for the specified user.
     * If the currency is already in the user's currency list, it is removed. Otherwise, it is added.
     *
     * @param user     the user to update the currency for
     * @param currency the currency to add or remove from the user's currency list
     */
    public void updateCurrency(User user, Currency currency) {
        List<Currency> userCurrencies = user.getCurrencies();
        if (userCurrencies.contains(currency)) {
            userCurrencies.remove(currency);
        } else {
            userCurrencies.add(currency);
        }
        addUser(user);
    }

    /**
     * Checks if a user's settings (currencies and banks) are empty.
     *
     * @param user the user to check the settings for
     * @return true if the user's settings are empty, false otherwise
     */
    public boolean userSettingsIsEmpty(User user) {
        return user.getCurrencies().isEmpty() || user.getBanks().isEmpty();
    }

    /**
     * Adds or updates the specified user in the users' collection.
     * If a user with the same chatId exists, the user data is updated.
     * Otherwise, the user is added to the collection.
     * After the user is added or updated, the users collection is saved to the storage.
     *
     * @param user the User object to add or update in the users collection
     */
    public void addUser(User user) {
        users.put(user.getChatId(), user);
        storage.saveUsers(users);
    }
}