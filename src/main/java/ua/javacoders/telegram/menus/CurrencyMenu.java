package ua.javacoders.telegram.menus;

import static ua.javacoders.utils.Emoji.WHITE_HEAVY_CHECK_MARK;
import static ua.javacoders.utils.Emoji.BACK_WITH_LEFTWARDS_ARROW_ABOVE;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.javacoders.logic.currency.Currency;
import ua.javacoders.users.User;

import java.util.ArrayList;
import java.util.List;

public class CurrencyMenu implements MenuCreator {
    @Override
    public InlineKeyboardMarkup createMenu(User user) {
        InlineKeyboardMarkup currencyMenu = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(firstRow(user));
        rowList.add(secondRow());

        currencyMenu.setKeyboard(rowList);
        return currencyMenu;
    }

    private InlineKeyboardButton addUSDButton(User user) {
        InlineKeyboardButton usdButton = new InlineKeyboardButton();
        String emoji = user.getCurrency().contains(Currency.USD) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        usdButton.setText("USD" + emoji);
        usdButton.setCallbackData("USD");
        return usdButton;
    }

    private InlineKeyboardButton addEURButton(User user) {
        InlineKeyboardButton eurButton = new InlineKeyboardButton();
        String emoji = user.getCurrency().contains(Currency.EUR) ? WHITE_HEAVY_CHECK_MARK.toString() : "";
        eurButton.setText("EUR" + emoji);
        eurButton.setCallbackData("EUR");
        return eurButton;
    }

    private InlineKeyboardButton addBackButton() {
        InlineKeyboardButton backButton = new InlineKeyboardButton();
        String emoji = BACK_WITH_LEFTWARDS_ARROW_ABOVE.toString();
        backButton.setText("Назад" + emoji);
        backButton.setCallbackData("BANK_BACK");
        return backButton;
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
}
