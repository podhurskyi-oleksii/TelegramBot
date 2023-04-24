package ua.javacoders.telegram.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.users.User;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements MenuCreator {
    @Override
    public InlineKeyboardMarkup createMenu(User user) {
        InlineKeyboardMarkup mainMenu = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow());
        rowList.add(secondRow());

        mainMenu.setKeyboard(rowList);
        return mainMenu;
    }

    private InlineKeyboardButton addRateButton() {
        InlineKeyboardButton rateButton = new InlineKeyboardButton();
        rateButton.setText("Отримати курси");
        rateButton.setCallbackData("TAKE_RATES");
        return rateButton;
    }

    private InlineKeyboardButton addSettingsButton() {
        InlineKeyboardButton settingsButton = new InlineKeyboardButton();
        settingsButton.setText("Налаштування");
        settingsButton.setCallbackData("SETTINGS");
        return settingsButton;
    }

    private List<InlineKeyboardButton> firstRow() {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(addRateButton());
        return firstRow;
    }

    private List<InlineKeyboardButton> secondRow() {
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(addSettingsButton());
        return secondRow;
    }
}
