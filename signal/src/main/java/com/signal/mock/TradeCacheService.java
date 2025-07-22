package com.signal.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TradeCacheService {

    @Autowired
    TradeRepository tradeRepository;

    @Cacheable(value = "LIVE_TRADE")
    public Trade getTrade(String status) {
        System.out.println("Database Hit");
        return tradeRepository.findByTradeStatus(status);
    }

    @CachePut(value = "LIVE_TRADE")
    public Trade updateTrade(Trade trade) {
        System.out.println("Updating Database " + trade.toString());
        return tradeRepository.save(trade);
    }

    @CacheEvict(value = "LIVE_TRADE")
    public void deleteProduct(Trade trade) {
        System.out.println("Updating Database " + trade.toString());
        tradeRepository.delete(trade);
    }
}