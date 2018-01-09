package com.felipecrescencio.bot;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.felipecrescencio.service.WeeklyCoffeeService;

public class CafeTiTotvsBot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(CafeTiTotvsBot.class);
	
	private String botUsername;
	
	private String botToken;
	
	@Autowired
    public WeeklyCoffeeService weeklyCoffeeService;

	public CafeTiTotvsBot() {
		Map<String, String> env = System.getenv();
		this.botUsername = env.get("BOT_USERNAME");		
		this.botToken = env.get("BOT_TOKEN");
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		handlemessage:
		if (update.hasMessage() && update.getMessage().hasText()) {
			String message2 = update.getMessage().getText().toLowerCase().trim();
			if(message2.indexOf("@cafetitotvsbot") < 0) {
				break handlemessage;
			}

			log.info("message: "+ update.getMessage().getText());
			
			if(weeklyCoffeeService != null) {
				String s = weeklyCoffeeService.processMessage(message2);
				
				if(s != null && !s.trim().isEmpty()) {

					SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
							.setChatId(update.getMessage().getChatId())
							.setText(s);

					try {
						execute(message); // Call method to send the message
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}
}
