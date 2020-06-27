import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//1233149306:AAEZ0iq0h6jFDGRPPgo1C5_ioup91XBKhhU

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();

        }
    }

    JSonReader reader = new JSonReader();


    //method, which answer to user:
    public void answer(Message message, String text) {
        SendMessage sendMessage1 = new SendMessage();
        //switch on markup(разметка)>
        sendMessage1.enableMarkdown(true);
        //Which exactly chat bot should answer>
        sendMessage1.setChatId(message.getChatId().toString());
        //Which exactly message bot should answer>
        sendMessage1.setReplyToMessageId(message.getMessageId());
        sendMessage1.setText(text);
        try {
            //create the bottons
            setButton(sendMessage1);
            //creat an answer
            sendMessage(sendMessage1);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


    //set bottons in the bot
    public void setButton(SendMessage sendMessage) {
        //create(initialisation the keyboard>
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        //create markup(разметка) and link the message with the keyboard>
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        //show the keyboard for all users>
        replyKeyboardMarkup.setSelective(true);

        //create size of the keyboard automatically>
        replyKeyboardMarkup.setResizeKeyboard(true);

        //do you need to hide the keyboard?>
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        //create a botton collection of the keyboard>
        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();

        //create the keyboard
        KeyboardRow keyBoardFirstRow = new KeyboardRow();

        //Add the first key to the first row
        keyBoardFirstRow.add(new KeyboardButton("Australia"));
        keyBoardFirstRow.add(new KeyboardButton("Russia"));

        //Add first raw to the keyboard
        keyboardRowList.add(keyBoardFirstRow);

        //install the keyboard to the bot
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }

    //come back botname
    public String getBotUsername() {
        return "Covid_19CheckBot";
    }

    //comeback token
    public String getBotToken() {
        return "1233149306:AAEZ0iq0h6jFDGRPPgo1C5_ioup91XBKhhU";
    }

    //get message within longpull
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    answer(message, "There is no help, mate");

                    break;

                case "/setting":
                    answer(message, "You can't change anything");
                    break;


                default:

                    try {
                        reader.comeback();
                    } catch (IOException e) {
                        answer(message,"comeback exeption");
                        e.printStackTrace();
                    }
                    if(reader.separate(message.getText())!="There is no your country"){

                    reader.directCatch();
                    answer(message, reader.print());
                    System.out.println(reader.print());
                    }else{
                        answer(message,"I can't find the country, try again");
                    }



            }

        }
    }

}
