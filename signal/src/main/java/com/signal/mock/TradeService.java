package com.signal.mock;

import com.signal.TradeInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

	@Autowired
	TradeInit tradeInit;

	@Autowired
	TradeRepository tradeRepository;

	@Autowired
	TradeClosingService closingService;
	
	@Autowired
	TradeOpeningService openingService;
	
	public String processTrade(String signalprice, String type) {
		long startTime = System.nanoTime();

		Trade liveTrade = tradeInit.getLiveTrade();
		Trade closeTrade = null;
		System.out.println("Live Trade : "+liveTrade);

		if(liveTrade != null) {
			System.out.println("------------ClosingService------------");
			closeTrade = closingService.closeTrade(signalprice, type, liveTrade);
			System.out.println("------------ClosingService: " + (System.nanoTime() - startTime)/1_000_000.0 + "------------");
		}
		long closingServiceTime = System.nanoTime();

		System.out.println("------------OpeningService------------");
		liveTrade = openingService.openTrade(signalprice, type);
		tradeInit.setLiveTrade(liveTrade);
		long openingServiceTime = System.nanoTime();
		System.out.println("------------OpeningService: " + (System.nanoTime() - startTime)/1_000_000.0 + "------------");


		System.out.println("------------Saving Trades------------");
		if (closeTrade != null) {
			tradeRepository.save(closeTrade);
		}
		if (liveTrade != null) {
			tradeRepository.save(liveTrade);
		}
		System.out.println("------------Saving Trades------------");

		long endTime = System.nanoTime();
		System.out.println("Total Time Taken in ms : " + (endTime - startTime)/1_000_000.0);
		System.out.println("Time Taken for Closing Service in ms : " + (closingServiceTime - startTime)/1_000_000.0);
		System.out.println("Time Taken for Opening Service in ms : " + (openingServiceTime - closingServiceTime)/1_000_000.0);
		System.out.println("Time Taken for Saving Trades in ms : " + (endTime - openingServiceTime)/1_000_000.0);

		return "";
	}
}