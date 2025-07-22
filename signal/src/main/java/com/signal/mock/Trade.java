package com.signal.mock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "TRADE")
public class Trade extends BaseEntity{

    @Column(name = "STRATEGY_ID")
    private String strategyId = "RIDETHETIDE";

    @Column(name = "ACCOUNT")
    private String account = "ZERODHAVINOTH";

    @Column(name = "ENTRY_SIGNAL_PRICE")
    private Double entrySignalPrice;
    
    @Column(name = "EXIT_SIGNAL_PRICE")
    private Double exitSignalPrice;

    @Column(name = "SIGNAL_TYPE")
    private String signalType;

    @Column(name = "NO_OF_POINTS_CAUGHT_BY_TRADE")
    private Double noOfPointsCaughtByTrade;

    @Column(name = "TRADE_OUTCOME")
    private String tradeOutcome;
    
    @Column(name = "EXPECTED_P_L")
    private Double expectedPnL;
    
    @Column(name = "ACTUAL_P_L")
    private Double actualPnL;
    
    @Column(name = "BROKERAGE")
    private Double brokerage;
    
    @Column(name = "DIFF_PERCENTAGE")
    private String diffPercentage;    
    
    @Column(name = "TRADE_STATUS")
    private String tradeStatus;
    
    @Column(name = "TRADE_OPEN_DATETIME")
    private String tradeOpenDtTime;
    
    @Column(name = "TRADE_CLOSE_DATETIME")
    private String tradeCloseDtTime;    
    
    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WeeklyOrderBook> weeklyOrderBook;
    
    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MonthlyOrderBook> monthlyOrderBook;

    public Trade() {
    	this.weeklyOrderBook = new ArrayList<WeeklyOrderBook>();
    	this.monthlyOrderBook = new ArrayList<MonthlyOrderBook>();
    }
    
	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getEntrySignalPrice() {
		return entrySignalPrice;
	}

	public void setEntrySignalPrice(Double entrySignalPrice) {
		this.entrySignalPrice = entrySignalPrice;
	}

	public Double getExitSignalPrice() {
		return exitSignalPrice;
	}

	public void setExitSignalPrice(Double exitSignalPrice) {
		this.exitSignalPrice = exitSignalPrice;
	}

	public String getSignalType() {
		return signalType;
	}

	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}

	public Double getNoOfPointsCaughtByTrade() {
		return noOfPointsCaughtByTrade;
	}

	public void setNoOfPointsCaughtByTrade(Double noOfPointsCaughtByTrade) {
		this.noOfPointsCaughtByTrade = noOfPointsCaughtByTrade;
	}

	public String getTradeOutcome() {
		return tradeOutcome;
	}

	public void setTradeOutcome(String tradeOutcome) {
		this.tradeOutcome = tradeOutcome;
	}

	public Double getExpectedPnL() {
		return expectedPnL;
	}

	public void setExpectedPnL(Double expectedPnL) {
		this.expectedPnL = expectedPnL;
	}

	public Double getActualPnL() {
		return actualPnL;
	}

	public void setActualPnL(Double actualPnL) {
		this.actualPnL = actualPnL;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	public String getDiffPercentage() {
		return diffPercentage;
	}

	public void setDiffPercentage(String diffPercentage) {
		this.diffPercentage = diffPercentage;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeOpenDtTime() {
		return tradeOpenDtTime;
	}

	public void setTradeOpenDtTime(String tradeOpenDtTime) {
		this.tradeOpenDtTime = tradeOpenDtTime;
	}

	public String getTradeCloseDtTime() {
		return tradeCloseDtTime;
	}

	public void setTradeCloseDtTime(String tradeCloseDtTime) {
		this.tradeCloseDtTime = tradeCloseDtTime;
	}

	public List<WeeklyOrderBook> getWeeklyOrderBook() {
		return weeklyOrderBook;
	}

	public void setWeeklyOrderBook(List<WeeklyOrderBook> weeklyOrderBook) {
		this.weeklyOrderBook = weeklyOrderBook;
	}

	public List<MonthlyOrderBook> getMonthlyOrderBook() {
		return monthlyOrderBook;
	}

	public void setMonthlyOrderBook(List<MonthlyOrderBook> monthlyOrderBook) {
		this.monthlyOrderBook = monthlyOrderBook;
	}

	@PrePersist
	public void onCreate() {
		this.tradeOpenDtTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
	}

	@Override
	public String toString() {
		return "Trade [strategyId=" + strategyId + ", account=" + account + ", entrySignalPrice=" + entrySignalPrice
				+ ", exitSignalPrice=" + exitSignalPrice + ", signalType=" + signalType + ", noOfPointsCaughtByTrade="
				+ noOfPointsCaughtByTrade + ", tradeOutcome=" + tradeOutcome + ", expectedPnL=" + expectedPnL
				+ ", actualPnL=" + actualPnL + ", brokerage=" + brokerage + ", diffPercentage=" + diffPercentage
				+ ", tradeStatus=" + tradeStatus + ", tradeOpenDtTime=" + tradeOpenDtTime + ", tradeCloseDtTime="
				+ tradeCloseDtTime + ", weeklyOrderBook=" + weeklyOrderBook + ", monthlyOrderBook=" + monthlyOrderBook
				+ "]";
	}
} 