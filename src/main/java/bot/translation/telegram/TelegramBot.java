package bot.translation.telegram;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {
    private Map<Long, String> userLanguageSelection = new HashMap<>();
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if ("/start".equals(messageText) || "/change".equals(messageText)) {
                sendLanguageOptions(chatId);
                return;
            }

            if (!userLanguageSelection.containsKey(chatId)) {
                sendLanguageOptions(chatId);
            } else {
                String selectedLanguage = userLanguageSelection.get(chatId);
                if ("/change".equals(messageText)) {
                    sendLanguageOptions(chatId);
                    return;
                }
                messageText = TranslateTextWithAPIKey.translateWord(messageText, selectedLanguage);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(messageText);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            // If the user has already selected the language
            if (userLanguageSelection.containsKey(chatId) && userLanguageSelection.get(chatId).equals(callbackData)) {
                SendMessage alreadySelectedMessage = new SendMessage();
                alreadySelectedMessage.setChatId(chatId);
                alreadySelectedMessage.setText("You have already selected this language.");
                try {
                    execute(alreadySelectedMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            }

            // Update user's language selection
            userLanguageSelection.put(chatId, callbackData);

            // Send a confirmation message
            SendMessage confirmationMessage = new SendMessage();
            confirmationMessage.setChatId(chatId);
            confirmationMessage.setText("Language selected: " + callbackData);
            try {
                execute(confirmationMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public @NonNull List<List<InlineKeyboardButton>> sendLanguageOptions(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Select language:");
        InlineKeyboardMarkup inlineKeyboardMarkup = createLanguageSelectionMarkup();
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return inlineKeyboardMarkup.getKeyboard();
    }

    public InlineKeyboardMarkup createLanguageSelectionMarkup() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton russianButton = new InlineKeyboardButton();
        russianButton.setText("Russian");
        russianButton.setCallbackData("ru"); // You can set callback data to identify the language
        row.add(russianButton);

        InlineKeyboardButton armenianButton = new InlineKeyboardButton();
        armenianButton.setText("Armenian");
        armenianButton.setCallbackData("hy"); // You can set callback data to identify the language
        row.add(armenianButton);

        rowsInline.add(row);
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    private List<InlineKeyboardButton> createLanguageRow(String text1, String text2) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton first = new InlineKeyboardButton();
        first.setText(text1);
        rowInline.add(first);
        InlineKeyboardButton second = new InlineKeyboardButton();
        second.setText(text2);
        rowInline.add(second);
        return rowInline;
    }



    @Override
    public String getBotUsername() {
        Properties prop = new Properties();
        try {
            prop.load(org.telegram.telegrambots.meta.generics.TelegramBot.class.getClassLoader().getResourceAsStream("personal.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop.getProperty("name");

    }

    @Override
    public String getBotToken() {
        Properties prop = new Properties();
        try {
            prop.load(TelegramBot.class.getClassLoader().getResourceAsStream("personal.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop.getProperty("token");
    }
}