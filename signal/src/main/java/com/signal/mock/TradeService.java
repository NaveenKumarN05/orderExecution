package com.signal.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

	@Autowired
	TradeClosingService closingService;
	
	@Autowired
	TradeOpeningService openingService;
	
	public String processTrade(String signalprice, String type) {
		long startTime = System.nanoTime();
		closingService.closeTrade(signalprice, type);
		openingService.openTrade(signalprice, type);
		long d = System.nanoTime() - startTime;
		System.out.println("duration in ns : "+ d);
		System.out.println("duration in ms : "+ d/1_000_000.0);
		return "";
	}
}