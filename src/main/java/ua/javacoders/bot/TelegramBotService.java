package ua.javacoders.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.javacoders.logic.scheduler.CurrencyRateScheduler;

/**
 * This class is responsible for registering the CurrencyTelegramBot and running the
 * CurrencyRateScheduler for scheduled currency rate fetching.
 */
public class TelegramBotService {
    private static final Logger LOG = LogManager.getLogger(TelegramBotService.class);

    /**
     * The constructor creates a CurrencyTelegramBot instance, a CurrencyRateScheduler instance,
     * registers the bot, and schedules currency rate fetching.
     * <p>
     * In case of any errors during bot registration or currency rate scheduling,
     * a warning message is logged with the relevant exception.
     */
    public TelegramBotService() {
        CurrencyTelegramBot currencyTelegramBot = new CurrencyTelegramBot();
        CurrencyRateScheduler scheduler = new CurrencyRateScheduler();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);
            scheduler.scheduleCurrencyRateFetching();
        } catch (TelegramApiException e) {
            LOG.warn("Error registering telegram bot: {} or running currencyScheduler: {}", currencyTelegramBot, scheduler, e);

        }
    }
}
