package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.impl.TradeExecutionService;
import com.acme.mytrader.model.StockPriceUpdate;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.price.impl.PriceListenerImpl;
import com.acme.mytrader.price.impl.PriceSourceImpl;

import java.util.Arrays;
import java.util.List;

import static com.acme.mytrader.model.StockPriceUpdate.Builder;
import static java.util.Arrays.asList;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {


    PriceSource priceSource;
    PriceListener listener;

    public TradingStrategy(PriceSource priceSource,PriceListener listener) {
            this.priceSource = priceSource;

            this.listener = listener;
    }

    public void autoBuy(List<StockPriceUpdate> request)  {
        for (StockPriceUpdate r : request) {
           priceSource.notifyPriceUpdate(r.getSecurity(),r.getPrice());
        }

    }

    public static void main(String[] args) {
        final PriceSource priceSource = new PriceSourceImpl();
        PriceListener listener = new PriceListenerImpl(new TradeExecutionService(),priceSource,50,100,"IBM");
        TradingStrategy tradingStrategy = new TradingStrategy(priceSource,listener);
        final StockPriceUpdate ibm = new StockPriceUpdate.Builder("IBM").withPrice(10).build();
        final StockPriceUpdate google = new StockPriceUpdate.Builder("GOOGL").withPrice(10).build();
        tradingStrategy.autoBuy(asList(ibm,google));
    }
}
