package bot.translation.telegram;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class TranslateTextWithAPIKey {

    public static String translateWord(String word, String targetLanguage) throws IOException {
        Properties prop = new Properties();
        try {
            prop.load(TelegramBot.class.getClassLoader().getResourceAsStream("personal.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String apiKey = prop.getProperty("api_key");
        String sourceLanguage = "en";
        String translatedText = "";
        if (!Objects.equals(word, "/start")) {
            translatedText = translateText(apiKey, word, sourceLanguage, targetLanguage);
            System.out.println("Translated text: " + translatedText);
        }
        return translatedText;
    }

    public static String translateText(String apiKey, String text, String sourceLanguage, String targetLanguage) throws IOException {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String url = String.format("https://translation.googleapis.com/language/translate/v2?key=%s&source=%s&target=%s&q=%s",
                apiKey, sourceLanguage, targetLanguage, encodedText);

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return extractTranslatedText(response.toString());
    }

    private static String extractTranslatedText(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray translations = jsonObject.getJSONObject("data").getJSONArray("translations");
        JSONObject translationObject = translations.getJSONObject(0);
        return translationObject.getString("translatedText");
    }
}
