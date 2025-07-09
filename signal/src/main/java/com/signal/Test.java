package com.signal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONException;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Instrument;
import com.zerodhatech.models.Profile;
import com.zerodhatech.models.User;

public class Test {

	public static void main(String[] args) throws JSONException, IOException, KiteException {
		
        KiteConnect kiteConnect = new KiteConnect("bpetdlek7kniupjq");
        kiteConnect.setUserId("FE5916");

        String url = kiteConnect.getLoginURL();

        kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
            @Override
            public void sessionExpired() {
                System.out.println("session expired");
            }
        });

        /* The request token can to be obtained after completion of login process. Check out https://kite.trade/docs/connect/v3/user/#login-flow for more information.
           A request token is valid for only a couple of minutes and can be used only once. An access token is valid for one whole day. Don't call this method for every app run.
           Once an access token is received it should be stored in preferences or database for further usage.
        */
//        User user =  kiteConnect.generateSession("8EJoRAAYJxPHcDZflFgS8iZY5YoG7X22", "s1fivfgl0kzjf6nl4y4xwo5dtyeknm0s");
//        System.out.println(user.accessToken);
//        System.out.println(user.publicToken);
        kiteConnect.setAccessToken("jSPMMfqX0HpGcAlbav1NPz438JUGOkM0");
        kiteConnect.setPublicToken("1P6Rg89sliNFvPpgFsiFzdhTzhodBOxG");
        Profile profile = kiteConnect.getProfile();
        List<Instrument> instruments = kiteConnect.getInstruments("NFO");
        instruments.forEach( i -> {
        	if(i.tradingsymbol.contains("NIFTY") && !i.tradingsymbol.contains("MIDCPNIFTY")
        			&& !i.tradingsymbol.contains("BANKNIFTY") && !i.tradingsymbol.contains("NIFTYNXT")
        			&& !i.tradingsymbol.contains("FINNIFTY") && 
        			!i.tradingsymbol.startsWith("NIFTY26") && !i.tradingsymbol.startsWith("NIFTY27") && !i.tradingsymbol.startsWith("NIFTY28") && !i.tradingsymbol.startsWith("NIFTY29") && !i.tradingsymbol.startsWith("NIFTY30")) {
        		System.out.println(i.tradingsymbol);
        	}
        });
        String[] ins = {"NFO:NIFTY2570325000CE","NFO:NIFTY25JULFUT"};
        var out = kiteConnect.getQuote(ins);
        var out3 = kiteConnect.getLTP(ins);  
//        out3.forEach((k,v) -> {
//        	if()
//        });
        var out1 = kiteConnect.getHoldings();
        var out2 = kiteConnect.getPositions();
        
//        System.out.println(out1);
	}
	
//	void getCall(){
//		try {
//            String urlString = "https://api.example.com/data"; // Replace with your API URL
//            URL url = new URL(urlString);
//            
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            // Optional: set headers
//            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//            int responseCode = con.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 200
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // Print the response
//                System.out.println("Response:\n" + response.toString());
//            } else {
//                System.out.println("GET request failed");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
