package com.signal.kite;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KiteAuthService {

    @Autowired
    private KiteAuthDetailsRepository kiteRepository;
    
    public void saveKiteAuth(String requestToken){
    	KiteConnect kiteConnect = new KiteConnect("bpetdlek7kniupjq");
        kiteConnect.setUserId("FE5916");
        User user = null;

        kiteConnect.setSessionExpiryHook(new SessionExpiryHook() {
            @Override
            public void sessionExpired() {
                System.out.println("session expired");
            }
        });
        try {
			user =  kiteConnect.generateSession(requestToken, "s1fivfgl0kzjf6nl4y4xwo5dtyeknm0s");
			KiteAuthDetails auth = new KiteAuthDetails();
		      auth.setRequestToken(requestToken);
		      auth.setAccessToken(user.accessToken);
		      auth.setPublicToken(user.publicToken);
		      auth.setApiKey("bpetdlek7kniupjq");
		      auth.setApiSecret("s1fivfgl0kzjf6nl4y4xwo5dtyeknm0s");
		      kiteRepository.save(auth);
		} catch (JSONException | IOException | KiteException e) {
			System.out.println("Exception while generating session");
		}
    }

    public String getLoginUrl() {
    	KiteConnect kiteConnect = new KiteConnect("bpetdlek7kniupjq");
        kiteConnect.setUserId("FE5916");
        return kiteConnect.getLoginURL();
    }
//    public String placeTestOrder(String symbol, Integer quantity, Double price) {
//        if (dryRun) {
//            return "[Dry Run] Would place order: " + symbol + ", qty: " + quantity + ", price: " + price;
//        }
//
//        try {
//            KiteAuthDetails token = null;
//            if (token == null) return "No access token found. Please login via /kite/login.";
//
//            KiteConnect kiteConnect = new KiteConnect(apiKey);
//            kiteConnect.setAccessToken(token.getAccessToken());
//            kiteConnect.setUserId(userId);
//
//            OrderParams order = new OrderParams();
//            order.tradingsymbol = symbol;
//            order.exchange = "NSE";
//            order.transactionType = "BUY";
//            order.orderType = "LIMIT";
//            order.product = "MIS";
//            order.quantity = quantity;
//            order.price = price;
//            order.validity = "DAY";
//
//            Order orderId = kiteConnect.placeOrder(order, "regular");
//            return "Order placed. ID: " + orderId;
//
//        } catch (IOException | KiteException e) {
//            e.printStackTrace();
//            return "Order failed: " + e.getMessage();
//        }
//    }
}