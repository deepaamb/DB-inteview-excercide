package com.acme.mytrader.price;

//Observer
public interface PriceListener {
    void priceUpdate(String security, double price);
}
