package com.signal.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.signal.kite.KiteAuthDetails;
import com.signal.kite.KiteAuthDetailsRepository;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Instrument;
import com.zerodhatech.models.LTPQuote;
import com.zerodhatech.models.MarginCalculationData;
import com.zerodhatech.models.MarginCalculationParams;

@Service
public class TradeUtil {

    @Autowired
    private KiteAuthDetailsRepository repository;
    
    @Value("#{'${weekly.this.month.this.week}'}")
    private String currentWeeklyExpiry;
    
    @Value("#{'${monthly.this.month}'}")
    private String currentMonthlyExpiry;
    
    @Value("#{'${weekly.this.month.subsequent.weeks}'.split(',')}")
    private List<String> subsequentThisMonthWeeklyExpiries;
    
    public Map<String, LTPQuote> getLTP(String[] ins) {
    	var kite = getKiteConnectObject();
    	if(kite != null) {
    		try {
				return kite.getLTP(ins);
			} catch (JSONException | IOException | KiteException e) {
				e.printStackTrace();
			}
    	}
    	return null;
    }
    
    public MarginCalculationParams initCalcParam() {
    	MarginCalculationParams params = new MarginCalculationParams();
    	params.exchange = "NFO";
    	params.variety = "regular";
    	params.product = "NRML";
    	params.orderType = "MARKET";
    	params.quantity = 75;    	
    	return params;
    }
    
    public List<Instrument> getNFOInstruments() {
    	var kite = getKiteConnectObject();
    	List<Instrument> instruments = new ArrayList<>();
    	List<Instrument> filtered = new ArrayList<>();    	
		try {
			instruments = kite.getInstruments("NFO");
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
        instruments.forEach( i -> {
        	if(i.tradingsymbol.contains("NIFTY") && !i.tradingsymbol.contains("MIDCPNIFTY")
        			&& !i.tradingsymbol.contains("BANKNIFTY") && !i.tradingsymbol.contains("NIFTYNXT")
        			&& !i.tradingsymbol.contains("FINNIFTY") && 
        			!i.tradingsymbol.startsWith("NIFTY26") && !i.tradingsymbol.startsWith("NIFTY27") && !i.tradingsymbol.startsWith("NIFTY28") && !i.tradingsymbol.startsWith("NIFTY29") && !i.tradingsymbol.startsWith("NIFTY30")) {
        		filtered.add(i);
        	}
        });
        return filtered;
    }
    
    public List<MarginCalculationData> getMarginCalculation(List<MarginCalculationParams> params) {
    	var kite = getKiteConnectObject();
    	List<MarginCalculationData> margins = new ArrayList<>();
    	try {
    		margins = kite.getMarginCalculation(params);
		} catch (JSONException | IOException | KiteException e) {
			e.printStackTrace();
		}
    	return margins;
    }
    
	public KiteConnect getKiteConnectObject() {
    	KiteConnect kiteConnect = null;
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
    	Optional<KiteAuthDetails> existing = repository.findByAuthDate(today);
        if (existing.isPresent()) {
        	kiteConnect = new KiteConnect(existing.get().getApiKey());
        	kiteConnect.setAccessToken(existing.get().getAccessToken());
            kiteConnect.setPublicToken(existing.get().getPublicToken());        	
        }
    	return kiteConnect;    	
    }
	
	public void buildInstrument(String signalPrice, String type, Trade trade) {		
		int strike = 0;
		String s = "";
    	if("CE".equals(type)) {
    		strike = Integer.valueOf(roundNFToNearestITMForCE(signalPrice));
    		s = "PE";
    	} else {
    		strike = Integer.valueOf(roundNFToNearestITMForPE(signalPrice));
    		s = "CE";
    	}    	
    	final String sell = s;
    	
    	String strikeA[] = {String.valueOf(strike-100),String.valueOf(strike-50),String.valueOf(strike),String.valueOf(strike+50),String.valueOf(strike+100)};
    	for (int i = 0; i<=4; i++) {
    		WeeklyOrderBook weeklyBuy = new WeeklyOrderBook();
    		weeklyBuy.setTradedSymbol("NFO:"+currentWeeklyExpiry+strikeA[i]+type);
    		weeklyBuy.setMarginCalcSymbol(currentWeeklyExpiry+strikeA[i]+type);
    		weeklyBuy.setTransactionType("BUY");
    		setBuyMoneyness(weeklyBuy, i);
    		weeklyBuy.setTrade(trade);
    		trade.getWeeklyOrderBook().add(weeklyBuy);    		    			                     
        	WeeklyOrderBook weeklySell = new WeeklyOrderBook();
        	weeklySell.setTradedSymbol("NFO:"+currentWeeklyExpiry+strikeA[i]+sell);
        	weeklySell.setMarginCalcSymbol(currentWeeklyExpiry+strikeA[i]+sell);
        	weeklySell.setTransactionType("SELL");
        	setSellMoneyness(weeklySell, i);
        	weeklySell.setTrade(trade);
        	trade.getWeeklyOrderBook().add(weeklySell);
    	}
    	for (int i = 0; i<=4; i++) {
    		MonthlyOrderBook monthlyBuy = new MonthlyOrderBook();
    		monthlyBuy.setTradedSymbol("NFO:"+currentMonthlyExpiry+strikeA[i]+type);
    		monthlyBuy.setMarginCalcSymbol(currentMonthlyExpiry+strikeA[i]+type);
    		monthlyBuy.setTransactionType("BUY");
    		setBuyMoneyness(monthlyBuy, i);
    		monthlyBuy.setTrade(trade);
    		trade.getMonthlyOrderBook().add(monthlyBuy);
    		MonthlyOrderBook monthlySell = new MonthlyOrderBook();        	
    		monthlySell.setTradedSymbol("NFO:"+currentMonthlyExpiry+strikeA[i]+sell);
    		monthlySell.setMarginCalcSymbol(currentMonthlyExpiry+strikeA[i]+sell);
    		monthlySell.setTransactionType("SELL");
    		setSellMoneyness(monthlySell, i);
    		monthlySell.setTrade(trade);
    		trade.getMonthlyOrderBook().add(monthlySell);
    	}
    	if(!CollectionUtils.isEmpty(subsequentThisMonthWeeklyExpiries)) {
    		subsequentThisMonthWeeklyExpiries.forEach(nextWeek -> {
            	for (int i = 0; i<=4; i++) {
            		WeeklyOrderBook weeklyBuy = new WeeklyOrderBook();
            		weeklyBuy.setTradedSymbol("NFO:"+nextWeek+strikeA[i]+type);
            		weeklyBuy.setMarginCalcSymbol(nextWeek+strikeA[i]+type);
            		weeklyBuy.setTransactionType("BUY");
            		setBuyMoneyness(weeklyBuy, i);
            		weeklyBuy.setTrade(trade);
            		trade.getWeeklyOrderBook().add(weeklyBuy);    		    			                     
                	WeeklyOrderBook weeklySell = new WeeklyOrderBook();
                	weeklySell.setTradedSymbol("NFO:"+nextWeek+strikeA[i]+sell);
                	weeklySell.setMarginCalcSymbol(nextWeek+strikeA[i]+sell);
                	weeklySell.setTransactionType("SELL");
                	setSellMoneyness(weeklySell, i);
                	weeklySell.setTrade(trade);
                	trade.getWeeklyOrderBook().add(weeklySell);
            	}    		
        	});	
    	}    	
	}
	
	public void setBuyMoneyness(BaseChildEntity a, int i) {
		if(i==0)
			a.setMoneyness("ITM-50");
		if(i==1)
			a.setMoneyness("ITM");
		if(i==2)
			a.setMoneyness("ATM");
		if(i==3)
			a.setMoneyness("OTM");
		if(i==4)
			a.setMoneyness("OTM+50");
	}
	
	public void setSellMoneyness(BaseChildEntity b, int i) {
		if(i==0)
			b.setMoneyness("OTM+50");
		if(i==1)
			b.setMoneyness("OTM");
		if(i==2)
			b.setMoneyness("ATM");
		if(i==3)
			b.setMoneyness("ITM");
		if(i==4)
			b.setMoneyness("ITM-50");
	}
	
	public void sleep1Sec() {
		try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public void calcTradeOutcome(Trade trade) {
		BigDecimal entryPrice = BigDecimal.valueOf(trade.getEntrySignalPrice());
		BigDecimal exitPrice = BigDecimal.valueOf(trade.getExitSignalPrice());
		Double noOfPoints = 0.0d;
		if("LONG".equals(trade.getSignalType())){
			if(entryPrice.compareTo(exitPrice) < 0) {
				noOfPoints = exitPrice.subtract(entryPrice).doubleValue();
			}else if(entryPrice.compareTo(exitPrice) > 0) {
				noOfPoints = exitPrice.subtract(entryPrice).doubleValue();
			}else
			noOfPoints = 0.0d;
		}
		if("SHORT".equals(trade.getSignalType())){
			if(entryPrice.compareTo(exitPrice) < 0) {
				noOfPoints = entryPrice.subtract(exitPrice).doubleValue();
			}else if(entryPrice.compareTo(exitPrice) > 0) {
				noOfPoints = entryPrice.subtract(exitPrice).doubleValue();
			}else
			noOfPoints = 0.0d;			
		}
		trade.setNoOfPointsCaughtByTrade(noOfPoints);
		trade.setTradeOutcome(noOfPoints > 0 ? "WIN" :"LOSS");
	}
	
	public void calcPnL(Trade trade) {		
		AtomicReference<Double> totalBrokerage = new AtomicReference<>(0.0);
		AtomicReference<Double> expectedPnL = new AtomicReference<>(0.0);
		AtomicReference<Double> actualPnL = new AtomicReference<>(0.0);
		trade.getWeeklyOrderBook().forEach(w -> {
			if(w.getSoldPrice() != null && w.getBoughtPrice() != null && trade.getNoOfPointsCaughtByTrade() != null && w.getTradeOpenBrokerage() !=null && w.getTradeCloseBrokerage() != null && w.getExpectedPnL() != null ) {
				Double d = w.getSoldPrice() - w.getBoughtPrice();
				w.setExpectedPnL(75 * trade.getNoOfPointsCaughtByTrade());
				w.setActualPnL(d*75);
		        totalBrokerage.updateAndGet(b -> b + w.getTradeOpenBrokerage() + w.getTradeCloseBrokerage());
		        expectedPnL.updateAndGet(e -> e + w.getExpectedPnL());
		        actualPnL.updateAndGet(p -> p + w.getActualPnL());				
			}
		});
		trade.getMonthlyOrderBook().forEach(m -> {
			if(m.getSoldPrice() != null && m.getBoughtPrice() != null && trade.getNoOfPointsCaughtByTrade() != null && m.getTradeOpenBrokerage() !=null && m.getTradeCloseBrokerage() != null && m.getExpectedPnL() != null ) {
				Double d = m.getSoldPrice() - m.getBoughtPrice();
				m.setExpectedPnL(75 * trade.getNoOfPointsCaughtByTrade());
				m.setActualPnL(d*75);
		        totalBrokerage.updateAndGet(b -> b + m.getTradeOpenBrokerage() + m.getTradeCloseBrokerage());
		        expectedPnL.updateAndGet(e -> e + m.getExpectedPnL());
		        actualPnL.updateAndGet(p -> p + m.getActualPnL());
			}
		});
		trade.setBrokerage(totalBrokerage.get());
		trade.setExpectedPnL(expectedPnL.get());		
		trade.setActualPnL(actualPnL.get());
		trade.setDiffPercentage(String.valueOf((trade.getActualPnL()/trade.getExpectedPnL())*100)+"%");
	}
	
    public void calcMarginForTrade(Trade trade) {
    	List<MarginCalculationParams> params = new ArrayList<MarginCalculationParams>();
    	buildMarginCalcParams(trade.getWeeklyOrderBook(), params);
    	buildMarginCalcParams(trade.getMonthlyOrderBook(), params);
    	var tradeMargins = getMarginCalculation(params);
    	tradeMargins.forEach(tradeMargin -> {
    		trade.getWeeklyOrderBook().forEach(weekly -> {
    			if((tradeMargin.tradingSymbol).equals(weekly.getMarginCalcSymbol())) {
    				if("LIVE".equals(trade.getTradeStatus())) {
    					weekly.setTradeOpenBrokerage(tradeMargin.charges.total);
        				if("BUY".equals(weekly.getTransactionType()))
        					weekly.setMarginToTrade(tradeMargin.option_premium);
        				else
        					weekly.setMarginToTrade(tradeMargin.total);
    				}    					
    				if("CLOSED".equals(trade.getTradeStatus()))
    					weekly.setTradeCloseBrokerage(tradeMargin.charges.total);    
    			}    	
    		});    		
    	});
    	tradeMargins.forEach(tradeMargin -> {
    		trade.getMonthlyOrderBook().forEach(monthly -> {
    			if((tradeMargin.tradingSymbol).equals(monthly.getMarginCalcSymbol())) {    				
    				if("LIVE".equals(trade.getTradeStatus())) {
    					monthly.setTradeOpenBrokerage(tradeMargin.charges.total);
    					if("BUY".equals(monthly.getTransactionType()))
        					monthly.setMarginToTrade(tradeMargin.option_premium);
        				else
        					monthly.setMarginToTrade(tradeMargin.total);
    				}    					
    				if("CLOSED".equals(trade.getTradeStatus()))
    					monthly.setTradeCloseBrokerage(tradeMargin.charges.total);
    			}    	
    		});    		
    	});
    }
    
    public void buildMarginCalcParams(List<? extends BaseChildEntity> childEntity, List<MarginCalculationParams> params) {
    	childEntity.forEach(c -> {
    		MarginCalculationParams param = initCalcParam();
        	param.tradingSymbol = c.getMarginCalcSymbol();
        	param.transactionType = c.getTransactionType();
        	params.add(param);
    	});
    }
    
	public String getDayOfWeek() {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        String day = "";
        int value = dayOfWeek.getValue();
        if(value == 1)
        	day = "MONDAY";
        else if (value == 2)
        	day = "TUESDAY";
        else if (value == 3)
        	day = "WEDNESDAY";
        else if (value == 4)
        	day = "THURSDAY";
        else if (value == 5)
        	day = "FRIDAY";
        else if (value == 6)
        	day = "SATURDAY";
        else if (value == 7)
        	day = "SUNDAY";
        return day;
	}
	
	public String getMonthString() {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));		
        Month m = today.getMonth();
        String month = "";
        int value = m.getValue();
        if(value == 1)
        	month = "JANUARY";
        else if (value == 2)
        	month = "FEBRUARY";
        else if (value == 3)
        	month = "MARCH";
        else if (value == 4)
        	month = "APRIL";
        else if (value == 5)
        	month = "MAY";
        else if (value == 6)
        	month = "JUNE";
        else if (value == 7)
        	month = "JULY";
        else if (value == 7)
        	month = "AUGUST";
        else if (value == 7)
        	month = "SEPTEMBER";
        else if (value == 7)
        	month = "OCTOBER";
        else if (value == 7)
        	month = "NOVEMBER";
        else if (value == 7)
        	month = "DECEMBER";
        return month;
	}
	
	public String roundNFToNearestITMForCE(String price) {
		float f = Float.valueOf(price);
		int roundedValue = (int) (Math.floor(f/50f))*50;
		return String.valueOf(roundedValue);
	}
	
	public String roundNFToNearestITMForPE(String price) {
		float f = Float.valueOf(price);
		int roundedValue = (int) (Math.ceil(f/50f))*50;
		return String.valueOf(roundedValue);
	}
}

