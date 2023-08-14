# Telegram bot 

<h3><a href="https://t.me/exchange_rate_goit_bot">Example of a working bot</a></h3><br>

This bot will send you notification with currency exchange rates of the selected currencies from the selected bank at a certain time of the day.

# Configuration
Bot settings are stored in the <b>config</b> directory.
<br>
The <b>application.properties</b> file stores the application's default settings such as bank, currency and notification time, off by default.
<br>
The <b>currency-bot.properties</b> file stores bot settings (name and token).

# Run:
1. Create bot via BotFather (t.me/BotFather)
2. Specify token and username in config/currency-bot.properties
3. Run Application.main()
4. Start using bot. Your bot language settings is the same as Telegram language. 
5. By default, You will receive exchange rates for US Dollar from Privatbank with notifications switched off.
6. You can change that in the settings menu.

# Bot created using
* Gradle
* Java 17 LTS

# Adding new bots.
Adding new bots. New bot classes are added to the telegrambots package.
