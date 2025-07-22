package com.signal;

import com.signal.kite.KiteAuthDetails;
import com.signal.kite.KiteAuthDetailsRepository;
import com.signal.mock.Trade;
import com.signal.mock.TradeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TradeInit {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private KiteAuthDetailsRepository repository;

    Map<LocalDate, Optional<KiteAuthDetails>> auth = new HashMap<>();

    Trade liveTrade;

    @PostConstruct
    private void postConstruct() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        auth.put(today, repository.findByAuthDate(today));

        System.out.println("initializing live trade");
        List<Trade> trades = tradeRepository.findByTradeStatus("LIVE");

        if (trades.size() != 1) {
            throw new RuntimeException("Multiple Live Trades Found");
        }
        liveTrade = trades.get(0);
        System.out.println(liveTrade.toString());
    }

    public Trade getLiveTrade() {
    	return liveTrade;
    }

    public void setLiveTrade(Trade trade) {
        this.liveTrade = trade;
    }


    public Optional<KiteAuthDetails> getKiteAuthDetails() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return auth.get(today);
    }
}