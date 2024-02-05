package bot.translation.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();

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
    public String getBotToken() {
        return "6690156151:AAERI57Y6UagMbwKOdTdalWFjLLjBbdgJD0";
    }

    @Override
    public String getBotUsername() {
        return "TranslationTelegramBot";
    }
}
