package ua.javacoders.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.javacoders.bot.handler.Handler;
import ua.javacoders.utils.ConfigLoader;

/**
 * This code is sooooooo bad...but "it's alive")
 * <p>
 * CurrencyTelegramBot is a Telegram bot that extends TelegramLongPollingBot to
 * handle incoming updates related to currency rates.
 * <p>
 * This bot makes use of a Handler object to process updates and respond
 * accordingly. The bots' token and name are fetched from environment variables
 * for better security and maintainability.
 */
public class CurrencyTelegramBot extends TelegramLongPollingBot {
    private final Handler handler;
    private static final ConfigLoader config = new ConfigLoader();

    public CurrencyTelegramBot() {
        super(config.getBotToken());
        this.handler = new Handler(this);
    }

    @Override
    public String getBotUsername() {
        return config.getBotToken();
    }

    /**
     * Handles an incoming Update object by delegating the processing to the Handler.
     *
     * @param update the Update object received from the Telegram API
     */
    @Override
    public void onUpdateReceived(Update update) {
        handler.handleUpdate(update);
    }
}
