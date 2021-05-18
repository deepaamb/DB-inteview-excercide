package com.acme.mytrader.price.impl;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PriceSourceImpl implements PriceSource {
    List<PriceListener> priceListenerList = new CopyOnWriteArrayList<>();


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
