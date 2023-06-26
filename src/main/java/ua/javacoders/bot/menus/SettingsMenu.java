package ua.javacoders.bot.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.user.User;

import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.Emoji.*;

/**
 * Implements the MenuCreator interface to create a settings menu.
 * This menu provides options for selecting banks and currencies to display exchange rates.
 */
public class SettingsMenu implements MenuCreator {

    /**
     * Creates an InlineKeyboardMarkup object representing the settings menu,
     * based on the user's preferences and current state.
     *
     * @param user the user for whom the menu is being created
     * @return InlineKeyboardMarkup object representing the settings menu
     */
    @Override
    public InlineKeyboardMarkup createMenu(User user) {
        InlineKeyboardMarkup settingsMenu = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> settingsKeyboard = new ArrayList<>();

        settingsKeyboard.add(createFirstRow());
        settingsKeyboard.add(createSecondRow());
        settingsKeyboard.add(createThirdRow());

        settingsMenu.setKeyboard(settingsKeyboard);
        return settingsMenu;
    }

    private InlineKeyboardButton addBankButton() {
        InlineKeyboardButton bankButton = new InlineKeyboardButton();
        String emoji = BANK.toString();
        bankButton.setText(emoji + " " + "Обрати Банки" + " " + emoji);
        bankButton.setCallbackData("BANKS_SELECTED");
        return bankButton;
    }

    private InlineKeyboardButton addCurrencyButton() {
        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        String emoji = MONEY_BAG.toString();
        currencyButton.setText(emoji + " " + "Обрати Валюти" + " " + emoji);
        currencyButton.setCallbackData("CURRENCIES_SELECTED");
        return currencyButton;
    }

    private InlineKeyboardButton addBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        String emoji = LEFTWARDS_ARROW_WITH_HOOK.toString();
        backButton.setText("Назад" + " " + emoji);
        backButton.setCallbackData("SETTINGS_BACK");
        return backButton;
    }

    private List<InlineKeyboardButton> createFirstRow() {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(addBankButton());
        return firstRow;
    }

    private List<InlineKeyboardButton> createSecondRow() {
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(addCurrencyButton());
        return secondRow;
    }

    private List<InlineKeyboardButton> createThirdRow() {
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(addBackButton());
        return thirdRow;
    }
}