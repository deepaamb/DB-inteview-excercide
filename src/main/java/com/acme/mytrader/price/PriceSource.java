package com.acme.mytrader.price;

//Subject
public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
    void notifyPriceUpdate(String security,double price);
}
