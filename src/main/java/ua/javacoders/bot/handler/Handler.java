package ua.javacoders.bot.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.javacoders.logic.banks.Bank;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.logic.formatter.CurrencyRateTextFormatter;
import ua.javacoders.bot.commands.Command;
import ua.javacoders.bot.menus.*;
import ua.javacoders.user.User;
import ua.javacoders.user.UserService;

import static ua.javacoders.utils.Emoji.*;
import static ua.javacoders.bot.commands.Command.*;


/**
 * The Handler class is responsible for processing incoming messages and callback requests from Telegram users.
 * This class contains methods for processing commands, keyboard responses, getting currency rates, etc.
 * It is important to note that the structure of some methods in this class can be improved by splitting them
 * into smaller, more specific methods, which would make the code easier to understand and maintain.
 */
public class Handler {
    private static final Logger LOG = LogManager.getLogger(Handler.class);
    private final UserService userService;
    private final AbsSender absSender;

    public Handler(AbsSender absSender) {
        this.userService = new UserService();
        this.absSender = absSender;
    }

    /**
     * Handles incoming messages and callback requests from users.
     * This method calls the appropriate methods to handle commands or callbacks based on the content of the Update.
     *
     * @param update an Update object containing input from the user
     */
    public void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processCommand(update);

        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }
    }

    /**
     * Processes commands sent by users via text message.
     * This method parses the text of the command and performs the appropriate action depending on the command.
     *
     * @param update an Update object containing input from the user
     */
    private void processCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        int messageId = update.getMessage().getMessageId();
        User user = getOrCreateUser(chatId);
        switch (message) {
            case "/start":
                deleteMessage(chatId, messageId);
                answerCommand(chatId, START);
                sendInlineKeyboard(chatId, HOUSE_BUILDING + "<b>Головне меню</b>" + HOUSE_BUILDING, new MainMenu(), user);
                break;
            case "/menu":
                deleteMessage(chatId, messageId);
                sendInlineKeyboard(chatId, HOUSE_BUILDING + "<b>Головне меню</b>" + HOUSE_BUILDING, new MainMenu(), user);
                break;
            case "/help":
                deleteMessage(chatId, messageId);
                answerCommand(chatId, HELP);
                break;
            case "/rates":
                deleteMessage(chatId, messageId);
                sendCurrencyRates(chatId, user);
                break;
            default:
                deleteMessage(chatId, messageId);
                break;
        }
    }

    /**
     * Processes callback requests received from users after they press the keyboard buttons.
     * This method analyzes the callbackData received from the keyboard and performs the appropriate action depending on the data.
     *
     * @param update an Update object containing the input from the user
     */
    private void processCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();
        String callbackId = update.getCallbackQuery().getId();
        User user = getOrCreateUser(chatId);
        String inlineKeyboardName;
        switch (callbackData) {
            case "GET_RATES":
                sendCurrencyRates(chatId, user);
                break;
            case "BACK_TO_MAIN_MENU":
            case "SETTINGS_BACK":
                inlineKeyboardName = HOUSE_BUILDING + " " + "<b>Головне меню</b>" + " " + HOUSE_BUILDING;
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new MainMenu(), user);
                break;
            case "SETTINGS":
            case "BANK_BACK":
            case "CURRENCY_BACK":
                inlineKeyboardName = GEAR + " " + "<b>Налаштування</b>" + " " + GEAR;
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new SettingsMenu(), user);
                break;
            case "CURRENCIES_SELECTED":
                inlineKeyboardName = MONEY_BAG + " " + "<b>Оберіть валюту</b>" + " " + MONEY_BAG;
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new CurrencyMenu(), user);
                break;
            case "EUR":
            case "USD":
                inlineKeyboardName = MONEY_BAG + " " + "<b>Оберіть валюту</b>" + " " + MONEY_BAG;
                Currency selectedCurrency = Currency.valueOf(callbackData);
                userService.updateCurrency(user, selectedCurrency);
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new CurrencyMenu(), user);
                break;
            case "BANKS_SELECTED":
                inlineKeyboardName = BANK + " " + "<b>Оберіть банк</b>" + " " + BANK;
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new BankMenu(), user);
                break;
            case "MONO":
            case "PRIVAT":
            case "NBU":
            case "OSCHAD":
            case "PUMB":
                inlineKeyboardName = BANK + " " + "<b>Оберіть банк</b>" + " " + BANK;
                // Get the bank from the callbackData
                Bank selectedBank = Bank.valueOf(callbackData);
                userService.updateBank(user, selectedBank);
                editInlineKeyboard(chatId, messageId, inlineKeyboardName, new BankMenu(), user);
                break;
        }
        answerCallbackQuery(callbackId);
    }

    /**
     * Retrieves an existing user by chat ID or creates a new user if one does not exist.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Check if a user with the specified chat ID already exists</li>
     *     <li>If the user does not exist, create a new user with the chat ID</li>
     *     <li>Retrieve the user by chat ID</li>
     * </ul>
     *
     * @param chatId the chat ID used to identify the user
     * @return an existing or newly created User object with the specified chat ID
     */
    private User getOrCreateUser(long chatId) {
        if (userService.isNewUser(chatId)) {
            userService.createNewUser(chatId);
        }
        return userService.getUser(chatId);
    }

    /**
     * Sends a response message to the user for the specified command.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create a SendMessage object</li>
     *     <li>Set the chat ID for the message</li>
     *     <li>Set the message text using the command description</li>
     *     <li>Execute the message using AbsSender</li>
     *     <li>Log a warning if there is an error while sending the message</li>
     * </ul>
     *
     * @param chatId  chat ID to send the response message to
     * @param command the command for which the response message is sent
     */
    private void answerCommand(long chatId, Command command) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(command.getDescription());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            LOG.warn("Error processing a command {}", chatId, e);
        }
    }

    /**
     * Sends exchange rates for the currencies and banks selected by the user.
     * If the user's settings are empty, sends an empty settings message and opens the settings menu.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create a message and set a chat ID</li>
     *     <li>Formatting exchange rates according to selected user settings</li>
     *     <li>Setting the message text</li>
     *     <li>Checking if the user settings are empty</li>
     *     <li>Send a message about empty settings and open the settings menu</li>
     *     <li>Sending exchange rates and the main menu keyboard</li>
     * </ul>
     *
     * @param chatId chat ID to send exchange rates to
     * @param user   user for whom the exchange rates are sent
     */
    private void sendCurrencyRates(long chatId, User user) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (userService.userSettingsIsEmpty(user)) {
            answerCommand(chatId, EMPTY_SETTINGS);
            String inlineKeyboardName = GEAR + " " + "<b>Налаштування</b>" + " " + GEAR;
            sendInlineKeyboard(chatId, inlineKeyboardName, new SettingsMenu(), user);
        } else {
            String currencyRates = new CurrencyRateTextFormatter().formatCurrencyRates(user.getCurrencies(), user.getBanks());
            message.setParseMode("HTML");
            message.setText(currencyRates);
            try {
                absSender.execute(message);
            } catch (TelegramApiException e) {
                LOG.warn("Error sending exchange rates to the user {}", chatId, e);
            }
            String inlineKeyboardName = HOUSE_BUILDING + " " + "<b>Головне меню</b>" + " " + HOUSE_BUILDING;
            sendInlineKeyboard(chatId, inlineKeyboardName, new MainMenu(), user);
        }
    }

    /**
     * Deletes a message in a chat by its ID.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create a DeleteMessage object and set chat ID and message ID</li>
     *     <li>Execute the delete message action using the AbsSender</li>
     *     <li>Log any errors that occur during the deletion process</li>
     * </ul>
     *
     * @param chatId    the chat ID where the message is to be deleted
     * @param messageId the ID of the message to be deleted
     */
    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            absSender.execute(deleteMessage);
        } catch (TelegramApiException e) {
            LOG.warn("Error deleting message {}", chatId, e);
        }
    }

    /**
     * Sends an inline keyboard with a message to a user.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create an InlineKeyboardMarkup object using the provided MenuCreator</li>
     *     <li>Create a SendMessage object and set chat ID, message text, and reply markup</li>
     *     <li>Execute the send message action using the AbsSender</li>
     *     <li>Log any errors that occur during the sending process</li>
     * </ul>
     *
     * @param chatId      the chat ID where the inline keyboard is to be sent
     * @param text        the message text to accompany the inline keyboard
     * @param menuCreator the MenuCreator used to create the inline keyboard
     * @param user        the user for whom the inline keyboard is being created
     */
    private void sendInlineKeyboard(long chatId, String text, MenuCreator menuCreator, User user) {

        InlineKeyboardMarkup inlineKeyboardMarkup = menuCreator.createMenu(user);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode("HTML");
        message.setText(text);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            LOG.warn("Error sending inlineKeyboard {}", chatId, e);
        }
    }

    /**
     * Edits an existing inline keyboard message, updating the text and keyboard layout.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create an EditMessageText object and set chat ID, message ID, and new text</li>
     *     <li>Create an InlineKeyboardMarkup object using the provided MenuCreator</li>
     *     <li>Update the reply markup of the EditMessageText object</li>
     *     <li>Execute the edit message action using the AbsSender</li>
     *     <li>Log any errors that occur during the editing process</li>
     * </ul>
     *
     * @param chatId      the chat ID of the message with the inline keyboard to be edited
     * @param messageId   the message ID of the message with the inline keyboard to be edited
     * @param newText     the new message text to accompany the edited inline keyboard
     * @param menuCreator the MenuCreator used to create the new inline keyboard
     * @param user        the user for whom the inline keyboard is being edited
     */
    public void editInlineKeyboard(long chatId, int messageId, String newText, MenuCreator menuCreator, User user) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(String.valueOf(chatId));
        editMessage.setMessageId(messageId);
        editMessage.setParseMode("HTML");
        editMessage.setText(newText);
        editMessage.setReplyMarkup(menuCreator.createMenu(user));

        try {
            absSender.execute(editMessage);
        } catch (TelegramApiException e) {
            LOG.warn("Error editing InlineKeyboard {}", chatId, e);
        }
    }

    /**
     * Answers a callback query by sending an empty response.
     * <p>
     * The method performs the following steps:
     * </p>
     * <ul>
     *     <li>Create an AnswerCallbackQuery object and set the callback query ID</li>
     *     <li>Execute the answer using the AbsSender</li>
     *     <li>Log any errors that occur during the answering process</li>
     * </ul>
     *
     * @param callbackQueryId the ID of the callback query to be answered
     */
    private void answerCallbackQuery(String callbackQueryId) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQueryId);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            LOG.warn("Error sending answer on callbackQuery {}", callbackQueryId, e);
        }
    }

}