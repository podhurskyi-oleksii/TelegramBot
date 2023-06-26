package ua.javacoders.bot.menus;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.javacoders.user.User;

/**
 * The MenuCreator interface is used to create customized inline keyboard menus
 * for different purposes in the Telegram bot.
 */
public interface MenuCreator {
    
    /**
     * Creates an InlineKeyboardMarkup object representing a specific menu
     * based on the user's preferences and current state.
     *
     * @param user the user for whom the menu is being created
     * @return InlineKeyboardMarkup object representing the specific menu
     */
    InlineKeyboardMarkup createMenu(User user);
}
