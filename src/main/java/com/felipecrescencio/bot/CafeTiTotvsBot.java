package com.felipecrescencio.bot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.felipecrescencio.entity.WeeklyCoffee;
import com.felipecrescencio.service.WeeklyCoffeeService;

public class CafeTiTotvsBot extends TelegramLongPollingBot {

	private static final Logger log = LoggerFactory.getLogger(CafeTiTotvsBot.class);

	@Autowired
    public WeeklyCoffeeService weeklyCoffeeService;
    
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			String message2 = update.getMessage().getText().toLowerCase().trim(); 
			if((message2.indexOf("cafe") >= 0 || message2.indexOf("café") >= 0) && message2.indexOf("semana") >= 0) {
				String s = "";
				if(weeklyCoffeeService != null) {
					List<WeeklyCoffee> lwc = weeklyCoffeeService.findAll();

					s = "O café dessa semana é do(a) "+ lwc.get(0).getName() +".";
				}

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

	@Override
	public String getBotUsername() {
		return "cafetitotvsbot";
	}

	@Override
	public String getBotToken() {
		return "521421948:AAGXUg8X9inlLDaBpxvO0Rz5SRwmiJBEhS8";
	}
}
