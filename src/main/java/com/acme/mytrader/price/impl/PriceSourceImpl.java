package com.acme.mytrader.price.impl;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PriceSourceImpl implements PriceSource {
    List<PriceListener> priceListenerList = new CopyOnWriteArrayList<>();

    private int excitingCorpStockPrice = 100;

    public void changeExcitingCorpStockPrice(String security, double price){
        excitingCorpStockPrice += price;
        notifyPriceUpdate(security,excitingCorpStockPrice);
    }

    @Override
    public void addPriceListener(PriceListener listener) {
        priceListenerList.add(listener);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
        priceListenerList.remove(listener);
    }

    @Override
    public void notifyPriceUpdate(String security, double price) {
        priceListenerList.stream().forEach(priceListener -> priceListener.priceUpdate(security,price));
    }


}
