import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class JSonReader {


    private String fullAPI;
    private String newData;
    private String result;
    Storage storage = new Storage();

    public String getResult() {
        return result;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    private String JSON;

    //Get all JSON file
    public String comeback() throws IOException {

        try {

            URL data = new URL("https://corona-api.com/countries");
            BufferedReader in = new BufferedReader(new InputStreamReader(data.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
            in.close();


            return result;
        } catch (MalformedURLException e) {
            return "site is not found";

        }

    }


    //find information about an input country
    public String separate(String nameOfCountry) {
        int nameOfCityIndex = result.indexOf(nameOfCountry);
        if (nameOfCityIndex == -1) {
            return "There is no your country";
        } else {
            int finish = result.indexOf("calculated", nameOfCityIndex);
            storage.setCountry(nameOfCountry);

            JSON = "{\"name\":" + getResult().substring(nameOfCityIndex - 1, finish - 2) + "}}";
            JSONObject object = new JSONObject();


            // storage.setCases(object.getString());
            setJSON(JSON);
            return JSON;


        }
    }

    //parcing direct information from the country
    public void directCatch() {
        String json = getJSON();
        //convert string to json
        JSONObject object = new JSONObject(json);

        //parcing today
        JSONObject daily = object.getJSONObject("today");
        storage.setCasesToday(daily.getInt("confirmed"));
        storage.setDeathsToday(daily.getInt("deaths"));

        //parcing total
        JSONObject total = object.getJSONObject("latest_data");
        storage.setCasesTotal(total.getInt("confirmed"));
        storage.setRecoveryTotal(total.getInt("recovered"));
        storage.setDeathsTotal(total.getInt("deaths"));

    }

    public String print() {
       return "In " + storage.getCountry() + ":" + "\n" +
                "Cases total: " + storage.getRecoveryTotal() + " Deaths total: " + storage.getDeathsTotal() + "\n" +
                "New cases today: " + storage.getCasesToday() + " Deaths today: " + storage.getDeathsToday() + " Recoveries today: " + storage.getRecoveredToday()
        ;


    }


}









