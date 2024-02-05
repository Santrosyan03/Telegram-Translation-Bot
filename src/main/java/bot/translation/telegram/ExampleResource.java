package bot.translation.telegram;

import jakarta.inject.Inject;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@ApplicationScoped
@Path("/translate")
public class ExampleResource {
    @Inject
    TelegramBot bot;

    @GET
    public void message() {
        String welcomeMessage = "Hello and Welcome to Telegram Online Translating BOT!!!";
        bot.sendMessage(welcomeMessage);
    }
}
