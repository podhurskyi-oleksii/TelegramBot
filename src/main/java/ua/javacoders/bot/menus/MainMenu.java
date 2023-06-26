package ua.javacoders.bot.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.user.User;

import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.Emoji.BAR_CHART;
import static ua.javacoders.utils.Emoji.GEAR;

/**
 * Implements the MenuCreator interface to create a main selection menu.
 * This menu displays options for getting currency rates and selecting the settings section.
 */
public class MainMenu implements MenuCreator {

    /**
     * Creates an InlineKeyboardMarkup object representing the main menu,
     * based on the user's preferences and current state.
     *
     * @param user the user for whom the menu is being created
     * @return InlineKeyboardMarkup object representing the main menu
     */
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
        String emoji = BAR_CHART.toString();
        rateButton.setText(emoji + " " + "Отримати курси" + " " + emoji);
        rateButton.setCallbackData("GET_RATES");
        return rateButton;
    }

    private InlineKeyboardButton addSettingsButton() {
        InlineKeyboardButton settingsButton = new InlineKeyboardButton();
        String emoji = GEAR.toString();
        settingsButton.setText(emoji + " " + "Налаштування" + " " + emoji);
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
