package com.acme.mytrader.model;

public class StockPriceUpdate {

    private String security;
    private double price;

    private StockPriceUpdate(){

    }

    public String getSecurity() {
        return security;
    }

    public double getPrice() {
        return price;
    }



    public static class Builder {
        private String security;
        private double price;


        private Builder(){

        }
        public Builder(String security) {
            this.security = security;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }



        public StockPriceUpdate build(){
            //Here we create the actual  object
            StockPriceUpdate priceUpdate = new StockPriceUpdate();
            priceUpdate.price = this.price;

            priceUpdate.security = this.security;

            return priceUpdate;
        }
    }
}
