package com.signal.mock;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseChildEntity extends BaseEntity {

    @Column(name = "TRADED_SYMBOL")
    protected String tradedSymbol;
    
    @Column(name = "MARGIN_CALC_SYMBOL")
    protected String marginCalcSymbol;
    
    @Column(name = "TRANSACTION_TYPE")
    protected String transactionType;

    @Column(name = "SOLD_PRICE")
    protected Double soldPrice;
    
	@Column(name = "BOUGHT_PRICE")
    protected Double boughtPrice;
	
	@Column(name = "LTP")
    protected Double ltp;
    
    @Column(name = "LTP_AFTER_1SEC")
    protected Double ltpafter1Sec;
    
    @Column(name = "LTP_AFTER_2SEC")
    protected Double ltpafter2Sec;
    
    @Column(name = "LTP_AFTER_3SEC")
    protected Double ltpafter3Sec;
    
    @Column(name = "LTP_AFTER_4SEC")
    protected Double ltpafter4Sec;    
    
    @Column(name = "EXPECTED_P_L")
    protected Double expectedPnL;
    
    @Column(name = "ACTUAL_P_L")
    protected Double actualPnL;
    
    @Column(name = "DIFF_PERCENTAGE")
    protected String diffPercentage;  
    
    @Column(name = "MONEYNESS")
    protected String moneyness;
    
	@Column(name = "LOT_SIZE")
    protected Integer lotSize = 1;

    @Column(name = "QUANTITY")
    protected Integer quantity = 75;

    @Column(name = "MARGIN_TO_TRADE")
    protected Double marginToTrade;
    
    @Column(name = "TRADE_OPEN_BROKERAGE")
    protected Double tradeOpenBrokerage;
    
    @Column(name = "TRADE_CLOSE_BROKERAGE")
    protected Double tradeCloseBrokerage;
    
    @Column(name = "TRADE_OPEN_EXE_STARTTIME")
    protected String tradeOpenExeStartTime;
    
    @Column(name = "TRADE_OPEN_EXE_ENDTIME")
    protected String tradeOpenExeEndTime;
    
    @Column(name = "TRADE_CLOSE_EXE_STARTTIME")
    protected String tradeCloseExeStartTime;
    
    @Column(name = "TRADE_CLOSE_EXE_ENDTIME")
    protected String tradeCloseExeEndTime;
    
    public String getTradeOpenExeStartTime() {
		return tradeOpenExeStartTime;
	}

	public void setTradeOpenExeStartTime(String tradeOpenExeStartTime) {
		this.tradeOpenExeStartTime = tradeOpenExeStartTime;
	}

	public String getTradeOpenExeEndTime() {
		return tradeOpenExeEndTime;
	}

	public void setTradeOpenExeEndTime(String tradeOpenExeEndTime) {
		this.tradeOpenExeEndTime = tradeOpenExeEndTime;
	}

	public String getTradeCloseExeStartTime() {
		return tradeCloseExeStartTime;
	}

	public void setTradeCloseExeStartTime(String tradeCloseExeStartTime) {
		this.tradeCloseExeStartTime = tradeCloseExeStartTime;
	}

	public String getTradeCloseExeEndTime() {
		return tradeCloseExeEndTime;
	}

	public void setTradeCloseExeEndTime(String tradeCloseExeEndTime) {
		this.tradeCloseExeEndTime = tradeCloseExeEndTime;
	}

	public String getTradedSymbol() {
		return tradedSymbol;
	}

	public void setTradedSymbol(String tradedSymbol) {
		this.tradedSymbol = tradedSymbol;
	}

	public String getMarginCalcSymbol() {
		return marginCalcSymbol;
	}

	public void setMarginCalcSymbol(String marginCalcSymbol) {
		this.marginCalcSymbol = marginCalcSymbol;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(Double soldPrice) {
		this.soldPrice = soldPrice;
	}

	public Double getBoughtPrice() {
		return boughtPrice;
	}

	public void setBoughtPrice(Double boughtPrice) {
		this.boughtPrice = boughtPrice;
	}

	public Double getLtp() {
		return ltp;
	}

	public void setLtp(Double ltp) {
		this.ltp = ltp;
	}

	public Double getLtpafter1Sec() {
		return ltpafter1Sec;
	}

	public void setLtpafter1Sec(Double ltpafter1Sec) {
		this.ltpafter1Sec = ltpafter1Sec;
	}

	public Double getLtpafter2Sec() {
		return ltpafter2Sec;
	}

	public void setLtpafter2Sec(Double ltpafter2Sec) {
		this.ltpafter2Sec = ltpafter2Sec;
	}

	public Double getLtpafter3Sec() {
		return ltpafter3Sec;
	}

	public void setLtpafter3Sec(Double ltpafter3Sec) {
		this.ltpafter3Sec = ltpafter3Sec;
	}

	public Double getLtpafter4Sec() {
		return ltpafter4Sec;
	}

	public void setLtpafter4Sec(Double ltpafter4Sec) {
		this.ltpafter4Sec = ltpafter4Sec;
	}

	public String getMoneyness() {
		return moneyness;
	}

	public void setMoneyness(String moneyness) {
		this.moneyness = moneyness;
	}

	public Integer getLotSize() {
		return lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getMarginToTrade() {
		return marginToTrade;
	}

	public void setMarginToTrade(Double marginToTrade) {
		this.marginToTrade = marginToTrade;
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

	public String getDiffPercentage() {
		return diffPercentage;
	}

	public void setDiffPercentage(String diffPercentage) {
		this.diffPercentage = diffPercentage;
	}

	public Double getTradeOpenBrokerage() {
		return tradeOpenBrokerage;
	}

	public void setTradeOpenBrokerage(Double tradeOpenBrokerage) {
		this.tradeOpenBrokerage = tradeOpenBrokerage;
	}

	public Double getTradeCloseBrokerage() {
		return tradeCloseBrokerage;
	}

	public void setTradeCloseBrokerage(Double tradeCloseBrokerage) {
		this.tradeCloseBrokerage = tradeCloseBrokerage;
	}

	@Override
	public String toString() {
		return "BaseChildEntity [tradedSymbol=" + tradedSymbol + ", marginCalcSymbol=" + marginCalcSymbol
				+ ", transactionType=" + transactionType + ", soldPrice=" + soldPrice + ", boughtPrice=" + boughtPrice
				+ ", ltp=" + ltp + ", ltpafter1Sec=" + ltpafter1Sec + ", ltpafter2Sec=" + ltpafter2Sec
				+ ", ltpafter3Sec=" + ltpafter3Sec + ", ltpafter4Sec=" + ltpafter4Sec + ", expectedPnL=" + expectedPnL
				+ ", actualPnL=" + actualPnL + ", diffPercentage=" + diffPercentage + ", moneyness=" + moneyness
				+ ", lotSize=" + lotSize + ", quantity=" + quantity + ", marginToTrade=" + marginToTrade
				+ ", tradeOpenBrokerage=" + tradeOpenBrokerage + ", tradeCloseBrokerage=" + tradeCloseBrokerage
				+ ", tradeOpenExeStartTime=" + tradeOpenExeStartTime + ", tradeOpenExeEndTime=" + tradeOpenExeEndTime
				+ ", tradeCloseExeStartTime=" + tradeCloseExeStartTime + ", tradeCloseExeEndTime="
				+ tradeCloseExeEndTime + "]";
	}	
}
