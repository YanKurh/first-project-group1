package constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public class Constants {

    public static final String BOT_TOKEN = "6044788279:AAH9hc_cBhtpICNCRz3Gxl3AWMDKEjg32_Q"; //"6078623462:AAFAG54arlBUkvzNbuuSFj7O7ipKkncj8v0";
    public static final String BOT_NAME = "InterestingUN_bot"; //"Group1ExchangeRatesBot";
    public static final String PRIVAT_API_URL = "https://api.privatbank.ua/p24api/pubinfo?exchange";
    public static final String NBU_API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    public static final String MONO_API_URL = "https://api.monobank.ua/bank/currency";
    public static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
 