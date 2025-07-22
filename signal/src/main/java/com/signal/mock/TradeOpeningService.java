package com.signal.mock;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerodhatech.models.LTPQuote;

@Service
public class TradeOpeningService {

    @Autowired
    TradeCacheService tradeCacheService;
    
    @Autowired
    private TradeUtil util;
    
    public String openTrade(String signalPrice, String type) {
    	Trade trade = new Trade();
    	trade.setEntrySignalPrice(Double.valueOf(signalPrice));
    	trade.setSignalType("CE".equals(type) ? "LONG" : "SHORT");
    	util.buildInstrument(signalPrice, type, trade);
    	String[] ltpIns = Stream.concat(trade.getWeeklyOrderBook().stream().map(WeeklyOrderBook::getTradedSymbol), trade.getMonthlyOrderBook().stream().map(MonthlyOrderBook::getTradedSymbol)).toArray(String[]::new);
    	Map<String, LTPQuote> ltp = util.getLTP(ltpIns);
    	util.sleep1Sec();
    	Map<String, LTPQuote> ltpAfter1Sec = util.getLTP(ltpIns);
    	util.sleep1Sec();
    	Map<String, LTPQuote> ltpAfter2Sec = util.getLTP(ltpIns);
    	util.sleep1Sec();
    	Map<String, LTPQuote> ltpAfter3Sec = util.getLTP(ltpIns);
    	util.sleep1Sec();
    	Map<String, LTPQuote> ltpAfter4Sec = util.getLTP(ltpIns);
    	trade.getWeeklyOrderBook().forEach(w -> {
    		LTPQuote quote = ltp.get(w.getTradedSymbol());
    		if(quote != null) {
    			w.setBoughtPrice("BUY".equals(w.getTransactionType()) ? quote.lastPrice : null);
    			w.setSoldPrice("SELL".equals(w.getTransactionType()) ? quote.lastPrice : null);
    			w.setLtp(quote.lastPrice);	
    		}
    		if(ltpAfter1Sec.get(w.getTradedSymbol()) != null)
    			w.setLtpafter1Sec(ltpAfter1Sec.get(w.getTradedSymbol()).lastPrice);
    		if(ltpAfter2Sec.get(w.getTradedSymbol()) != null)
    			w.setLtpafter2Sec(ltpAfter2Sec.get(w.getTradedSymbol()).lastPrice);
    		if(ltpAfter3Sec.get(w.getTradedSymbol()) != null)
    			w.setLtpafter3Sec(ltpAfter3Sec.get(w.getTradedSymbol()).lastPrice);
    		if(ltpAfter4Sec.get(w.getTradedSymbol()) != null)
    			w.setLtpafter4Sec(ltpAfter4Sec.get(w.getTradedSymbol()).lastPrice);
    		w.setLotSize(1);
    	});
    	trade.getMonthlyOrderBook().forEach(m -> {
    		LTPQuote quote = ltp.get(m.getTradedSymbol());
    		if(quote != null) {
    			m.setBoughtPrice("BUY".equals(m.getTransactionType()) ? quote.lastPrice : null);
    			m.setSoldPrice("SELL".equals(m.getTransactionType()) ? quote.lastPrice : null);
    			m.setLtp(quote.lastPrice);	
    		}
    		if(ltpAfter1Sec.get(m.getTradedSymbol()) != null)
    			m.setLtpafter1Sec(ltpAfter1Sec.get(m.getTradedSymbol()).lastPrice);
    		if(ltpAfter2Sec.get(m.getTradedSymbol()) != null)
    			m.setLtpafter2Sec(ltpAfter2Sec.get(m.getTradedSymbol()).lastPrice);
    		if(ltpAfter3Sec.get(m.getTradedSymbol()) != null)
    			m.setLtpafter3Sec(ltpAfter3Sec.get(m.getTradedSymbol()).lastPrice);
    		if(ltpAfter4Sec.get(m.getTradedSymbol()) != null)
    			m.setLtpafter4Sec(ltpAfter4Sec.get(m.getTradedSymbol()).lastPrice);
    		m.setLotSize(1);
    	});
    	trade.setTradeStatus("LIVE");
    	util.calcMarginForTrade(trade);
    	//tradeRepository.save(trade);
        tradeCacheService.updateTrade(trade);
    	System.out.println("Live Trade Being Opened: "+trade);
    	return "Trade processed: ";
    }
}
