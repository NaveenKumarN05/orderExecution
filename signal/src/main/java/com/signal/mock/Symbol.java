package com.signal.mock;

import jakarta.persistence.*;

@Entity
@Table(name = "SYMBOL")
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "TRADED_SYMBOL")
    private String tradedSymbol;
    
    @Column(name = "MARGIN_CALC_SYMBOL")
    private String marginCalcSymbol;

	@Column(name = "EXPIRY_TYPE")
    private String expiryType;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;
    
    @Column(name = "MONEYNESS")
    private String moneyness;
    
	public Symbol() {
    }
	
	public Symbol(String tradedSymbol, String marginCalcSymbol, String transactionType) {
		this.tradedSymbol = tradedSymbol;
		this.marginCalcSymbol = marginCalcSymbol;
		this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTradedSymbol() {
        return tradedSymbol;
    }

    public void setTradedSymbol(String tradedSymbol) {
        this.tradedSymbol = tradedSymbol;
    }

    public String getExpiryType() {
        return expiryType;
    }

    public void setExpiryType(String expiryType) {
        this.expiryType = expiryType;
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
    
	public String getMoneyness() {
		return moneyness;
	}

	public void setMoneyness(String moneyness) {
		this.moneyness = moneyness;
	}

	@Override
	public String toString() {
		return "Symbol [id=" + id + ", priority=" + priority + ", tradedSymbol=" + tradedSymbol + ", marginCalcSymbol="
				+ marginCalcSymbol + ", expiryType=" + expiryType + ", transactionType=" + transactionType
				+ ", moneyness=" + moneyness + "]";
	}
}
