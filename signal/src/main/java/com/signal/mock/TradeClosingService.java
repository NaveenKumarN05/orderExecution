package com.signal.mock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerodhatech.models.LTPQuote;

@Service
public class TradeClosingService {

    @Autowired
    TradeCacheService tradeCacheService;
    
    @Autowired
    private TradeUtil util;
    
	public String closeTrade(String signalPrice, String type) {
		//Trade liveTrade = tradeRepository.findByTradeStatus("LIVE");
		Trade liveTrade = tradeCacheService.getTrade("LIVE");
		System.out.println("Live Trade : "+liveTrade);
		if(liveTrade != null) {
	    	String[] liveIns = Stream.concat(liveTrade.getWeeklyOrderBook().stream().map(WeeklyOrderBook::getTradedSymbol), liveTrade.getMonthlyOrderBook().stream().map(MonthlyOrderBook::getTradedSymbol)).toArray(String[]::new);
	    	Map<String, LTPQuote> ltp = util.getLTP(liveIns);
	    	liveTrade.getWeeklyOrderBook().forEach(w -> {
	    		LTPQuote quote = ltp.get(w.getTradedSymbol());
	    		if(quote != null) {
	    			if("BUY".equals(w.getTransactionType())){
	    				w.setSoldPrice(quote.lastPrice);
	    			}
	    			if("SELL".equals(w.getTransactionType())){
	    				w.setBoughtPrice(quote.lastPrice);
	    			}	    			
	    		}    		
	    	});
	    	liveTrade.getMonthlyOrderBook().forEach(m -> {
	    		LTPQuote quote = ltp.get(m.getTradedSymbol());
	    		if(quote != null) {
	    			if("BUY".equals(m.getTransactionType())){
	    				m.setSoldPrice(quote.lastPrice);
	    			}
	    			if("SELL".equals(m.getTransactionType())){
	    				m.setBoughtPrice(quote.lastPrice);
	    			}	    			
	    		}	    		
	    	});
	    	liveTrade.setTradeStatus("CLOSED");
	    	liveTrade.setExitSignalPrice(Double.valueOf(signalPrice));
	    	liveTrade.setTradeCloseDtTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")));
	    	util.calcMarginForTrade(liveTrade);
	    	util.calcTradeOutcome(liveTrade);
	    	util.calcPnL(liveTrade);
            tradeCacheService.updateTrade(liveTrade);
	    	//tradeRepository.save(liveTrade);
	    	System.out.println("Live Trade Being Closed: "+liveTrade);
		}
        return "Trade processed: ";	
	}
}
