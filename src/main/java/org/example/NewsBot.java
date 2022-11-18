package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NewsBot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("");

        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text();
                    //logic
                    int number = Integer.parseInt(incomeMessage);
                    Document doc = Jsoup.connect("https://lenta.ru/rss").get();
                    int index = number - 1;
                    Element news = doc.select("item").get(index);
                    String category = news.select("category").text();
                    String title = news.select("title").text();
                    String link = news.select("link").text();
                    String description = news.select("description").text();
                    String result = category + "\n" + title + "\n" + description + "\n" + link;
                    //send response
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}

