/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.felipecrescencio.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.felipecrescencio.bot.CafeTiTotvsBot;
import com.felipecrescencio.config.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.felipecrescencio"})
public class CoffeeITApplication {

	private static final Logger log = LoggerFactory.getLogger(CoffeeITApplication.class);
	
	public static CafeTiTotvsBot cttb;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(CoffeeITApplication.class, args);

		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			CafeTiTotvsBot cttb2 = new CafeTiTotvsBot();
			CoffeeITApplication.cttb = cttb2;
			botsApi.registerBot(cttb2);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

		// run each 25 min
		final long timeInterval = 1500000;
		Runnable runnable = new Runnable() {

			public void run() {
				while (true) {
					try {
						sendGET();
						System.out.println("GET DONE");
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

	private static void sendGET() throws IOException {
		URL obj = new URL("https://cafe-ti-totvs.herokuapp.com/");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
	}
}

/*
=== cafe-ti-totvs Config Vars
DATABASE_URL: postgres://txnhxbncdblocb:339e0947b314465fd63c66b15dcdeba9ab85b424ab7e8bb86ca9e00bb605e833@ec2-23-21-164-107.compute-1.amazonaws.com:5432/ddgpuvc9d545qs

 */
