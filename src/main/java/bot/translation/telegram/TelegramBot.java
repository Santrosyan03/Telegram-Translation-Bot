package bot.translation.telegram;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.client.Client;

@ApplicationScoped
public class TelegramBot {

    @ConfigProperty(name = "telegram.token")
    String token;

    @ConfigProperty(name = "telegram.chatId")
    String chatId;
    private WebTarget baseTarget;
    private Client client;
    @PostConstruct
    public void initClient() {
        client = ClientBuilder.newClient();
        baseTarget = client.target("https://api.telegram.org/bot{token}")
                .resolveTemplate("token", token);
    }

    public void sendMessage(String message) {
        try {
            Response response = baseTarget
                    .path("sendMessage")
                    .queryParam("chat_id", chatId)
                    .queryParam("text", message)
                    .request()
                    .get();
            JsonObject json = response.readEntity(JsonObject.class);
            boolean ok = json.getBoolean("ok", false);
            if (!ok) System.out.println("Couldn't successfully send a message");
        } catch (Exception exception) {
            System.out.println("Couldn't successfully send a message, " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    @PreDestroy
    public void closeClient() {
        client.close();
    }
}
