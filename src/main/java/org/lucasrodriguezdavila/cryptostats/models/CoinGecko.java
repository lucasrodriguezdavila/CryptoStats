package org.lucasrodriguezdavila.cryptostats.models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoinGecko {
    public static Integer bitcoin = 0;
    public static Float ethereum = 0f;
    public static String COIN_GECKO_API_KEY = "";
    public static final String COIN_GECKO_BASE_URL = "https://api.coingecko.com/api/v3";

    public String getEthereumAsUSD() {
        try {
            //fetch data from CoinGecko API
            URL url = new URL(COIN_GECKO_BASE_URL + "/simple/price?ids=ethereum&vs_currencies=usd");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-cg-pro-api-key", COIN_GECKO_API_KEY);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();

            if (status == 200) {
                StringBuilder content = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());

                while (scanner.hasNext()) {
                    content.append(scanner.nextLine());
                }

                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(content.toString());
                JSONObject ethereum = (JSONObject) json.get("ethereum");
                CoinGecko.ethereum = Float.parseFloat(ethereum.get("usd").toString());
                return ethereum.get("usd").toString();
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getBitcoinAsUSD() {
        try {
            //fetch data from CoinGecko API
            URL url = new URL(COIN_GECKO_BASE_URL + "/simple/price?ids=bitcoin&vs_currencies=usd");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-cg-pro-api-key", COIN_GECKO_API_KEY);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();


            if (status == 200) {
                StringBuilder content = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());

                while (scanner.hasNext()) {
                    content.append(scanner.nextLine());
                }

                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(content.toString());
                JSONObject bitcoin = (JSONObject) json.get("bitcoin");
                CoinGecko.bitcoin = Integer.parseInt(bitcoin.get("usd").toString());
                return bitcoin.get("usd").toString();
            } else {
                return "";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
