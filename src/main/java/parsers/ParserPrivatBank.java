package parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Currencies;
import dto.CurrenciesPack;
import dto.CurrencyHolder;
import dto.general.PrivatBank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Currencies.*;

import static constants.Constants.*;


public class ParserPrivatBank {

    private static CurrenciesPack pack = new CurrenciesPack();

    public static List<PrivatBank> sendRequest(String endpoint) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(PRIVAT_API_URL + endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        Type type = TypeToken.getParameterized(List.class, PrivatBank.class).getType();
        List<PrivatBank> currencies = new Gson().fromJson(String.valueOf(response), type);

        return currencies;
    }

    public static CurrenciesPack getCurrencyFromPrivatBank() {
        List<PrivatBank> currencies = sendRequest("&coursid=11");
        List<PrivatBank> gbp = sendRequest("&coursid=12").stream()
                .filter(currency -> currency.getCcy().equals(GBP.name()))
                .collect(Collectors.toList());
        currencies.add(gbp.get(0));

        Date date = new Date();
        if (pack.getLastUpdate().getTime() - date.getTime() < 300000) return pack;

        pack.setLastUpdate(new Date(date.getTime()));
        pack.setBankName("ПриватБанк");

        List<CurrencyHolder> collect = currencies.stream()
                .map(cur -> new CurrencyHolder(
                        cur.getSale(),
                        0,
                        cur.getBuy(),
                        UAH,
                        Currencies.getByName(cur.getCcy())))
                .collect(Collectors.toList());
        pack.setCurrencies(collect);

        return pack;
    }
}
