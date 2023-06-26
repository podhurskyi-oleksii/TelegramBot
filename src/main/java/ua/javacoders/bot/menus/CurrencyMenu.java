package ua.javacoders.bot.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.logic.currencies.Currency;
import ua.javacoders.user.User;

import java.util.ArrayList;
import java.util.List;

import static ua.javacoders.utils.Emoji.*;

/**
 * Implements the MenuCreator interface to create a currency selection menu.
 * This menu displays a list of currencies with checkboxes indicating whether they are selected or not.
 * It also provides navigation buttons to go back or return to the main menu.
 */
public class CurrencyMenu implements MenuCreator {

    /**
     * Creates an InlineKeyboardMarkup object representing the currency selection menu.
     * The menu contains buttons for currencies and navigation buttons.
     *
     * @param user the user for whom the menu is being created
     * @return InlineKeyboardMarkup object representing the currency selection menu
     */
    @Override
    public InlineKeyboardMarkup createMenu(User user) {
        InlineKeyboardMarkup currencyMenu = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow(user));
        rowList.add(secondRow());
        rowList.add(thirdRow());

        currencyMenu.setKeyboard(rowList);
        return currencyMenu;
    }

    private InlineKeyboardButton addUSDButton(User user) {
        InlineKeyboardButton usdButton = new InlineKeyboardButton();
        String emoji = user.getCurrencies().contains(Currency.USD) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        usdButton.setText("USD" + " " + emoji);
        usdButton.setCallbackData("USD");
        return usdButton;
    }

    private InlineKeyboardButton addEURButton(User user) {
        InlineKeyboardButton eurButton = new InlineKeyboardButton();
        String emoji = user.getCurrencies().contains(Currency.EUR) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        eurButton.setText("EUR" + " " + emoji);
        eurButton.setCallbackData("EUR");
        return eurButton;
    }

    private InlineKeyboardButton addBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        String emoji = LEFTWARDS_ARROW_WITH_HOOK.toString();
        backButton.setText("Назад" + " " + emoji);
        backButton.setCallbackData("CURRENCY_BACK");
        return backButton;
    }

    private InlineKeyboardButton addBackToMainMenuButton() {
        InlineKeyboardButton backToMainMenuButton = new InlineKeyboardButton();
        String emoji = HOUSE_BUILDING.toString();
        backToMainMenuButton.setText("До головного меню" + " " + emoji);
        backToMainMenuButton.setCallbackData("BACK_TO_MAIN_MENU");
        return backToMainMenuButton;
    }

    private List<InlineKeyboardButton> firstRow(User user) {
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(addUSDButton(user));
        firstRow.add(addEURButton(user));
        return firstRow;
    }

    private List<InlineKeyboardButton> secondRow() {
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(addBackButton());
        return secondRow;
    }

    private List<InlineKeyboardButton> thirdRow() {
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        thirdRow.add(addBackToMainMenuButton());
        return thirdRow;
    }

}
