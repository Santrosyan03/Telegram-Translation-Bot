# Telegram Translation Bot
This Telegram bot allows users to translate text from English to either Russian or Armenian. Users can select their preferred language and then type or change text to be translated.

### Features
Translate text from English to Russian or Armenian.
Choose the target language from a selection menu.
Change the selected language at any time.
Notifications for already selected language.

### Usage
Start a conversation with the bot by searching for @TranslationTelegramBot on Telegram or by clicking here.
Type /start to initiate the bot.
Choose your preferred language from the provided options.
Once a language is selected, type any English text, and the bot will translate it to the chosen language.
To change the selected language, type /change.
Select the new language from the options provided.
### Dependencies
#### 1) Java 8 or higher
#### 2) Lombok (for annotation processing)
#### 3) TelegramBots API (for Telegram bot integration)
#### 4) JSON-java (for JSON processing)
#### 5) SLF4J (for logging)
### Setup
#### Clone this repository:

```bash
git clone https://github.com/yourusername/telegram-translation-bot.git
```

### Create a personal.properties file in the src/main/resources directory with the following properties:

```properties
token=YOUR_TELEGRAM_BOT_TOKEN
api_key=YOUR_GOOGLE_TRANSLATE_API_KEY
name=TELEGRAM_BOT_NAME
```
#### Replace YOUR_TELEGRAM_BOT_TOKEN with your Telegram bot token and YOUR_GOOGLE_TRANSLATE_API_KEY with your Google Translate API key.


### Build the project using your preferred build tool (Maven, Gradle, etc.).

Run the bot:

```shell
java -jar yourjarfile.jar
```
Replace yourjarfile.jar with the name of your compiled JAR file.

### Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or create a pull request.