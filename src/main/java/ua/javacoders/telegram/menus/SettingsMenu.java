package ua.javacoders.telegram.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.users.User;

import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.Emoji.BACK_WITH_LEFTWARDS_ARROW_ABOVE;

public class SettingsMenu implements MenuCreator {
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
        bankButton.setText("Обрати Банки");
        bankButton.setCallbackData("BANKS_SELECTED");
        return bankButton;
    }

    private InlineKeyboardButton addCurrencyButton() {
        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Обрати Валюти");
        currencyButton.setCallbackData("CURRENCIES_SELECTED");
        return currencyButton;
    }

    private InlineKeyboardButton addBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        String emoji = BACK_WITH_LEFTWARDS_ARROW_ABOVE.toString();
        backButton.setText("Назад" + emoji);
        backButton.setCallbackData("BANK_BACK");
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
        secondRow.add(addCurrencyButton());
        return secondRow;
    }

    private List<InlineKeyboardButton> createThirdRow() {
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(addBackButton());
        return thirdRow;
    }
}