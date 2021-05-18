package com.acme.mytrader.price.impl;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.impl.TradeExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;


public class PriceListenerImpl implements PriceListener {
    private String security;
    private double price;
    private double triggerLevel = 0;
    private int quantityToPurchase =0;
    private ExecutionService executionService ;
    private PriceSource priceSource;
    private String monitorStock;

    public PriceListenerImpl(ExecutionService executionService,PriceSource priceSource, double priceThreshold, int quantityToPurchase,String monitorStock) {
        this.executionService = executionService;
        this.triggerLevel = priceThreshold;
        this.quantityToPurchase = quantityToPurchase;
        this.priceSource = priceSource;
        this.monitorStock = monitorStock;
        priceSource.addPriceListener(this);
    }

    @Override
    public void priceUpdate(String security, double price) {
        this.price = price;
        this.security = security;
        if (canBuy(security, price)) {
            executionService.buy(security, price, quantityToPurchase);
            priceSource.removePriceListener(this);
        }
    }

    private boolean canBuy(String security, double price) {
        boolean canBuy = false;
        if(monitorStock != null && security != null)
            canBuy = this.monitorStock.equalsIgnoreCase(security) && (price <= this.triggerLevel);
        return canBuy;
    }
}
