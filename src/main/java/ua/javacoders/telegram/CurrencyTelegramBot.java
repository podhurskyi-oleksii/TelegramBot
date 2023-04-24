package ua.javacoders.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.javacoders.telegram.handler.Handler;
import ua.javacoders.users.UserService;

public class CurrencyTelegramBot extends TelegramLongPollingBot {
    private final Handler handler;

    public CurrencyTelegramBot() {
        UserService userService = new UserService();
        handler = new Handler(userService, this);
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        handler.handleUpdate(update);
    }
}
