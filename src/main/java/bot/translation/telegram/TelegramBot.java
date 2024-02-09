package bot.translation.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        text = translate(text);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        try {
            this.execute(sendMessage);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getBotUsername() {
        return "TranslationTelegramBot";
    }

    @Override
    public String getBotToken() {
        Properties prop = new Properties();
        try {
            prop.load(TelegramBot.class.getClassLoader().getResourceAsStream("personal.properties"));
            System.out.println(prop.getProperty("token"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop.getProperty("token");
    }

    public String translate(String text) {
        Locale locale = new Locale.Builder().setLanguage("fr").setRegion("CA").build();
        ResourceBundle bundle = ResourceBundle.getBundle("translations", locale);
        String translatedText = bundle.getString("hello");
        System.out.println("Translated text: " + translatedText);
        return translatedText;
    }
}
