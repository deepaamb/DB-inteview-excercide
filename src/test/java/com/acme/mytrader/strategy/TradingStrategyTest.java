package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.model.StockPriceUpdate;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import static org.assertj.core.api.Assertions.assertThat;

import com.acme.mytrader.price.impl.PriceListenerImpl;
import com.acme.mytrader.price.impl.PriceSourceImpl;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TradingStrategyTest {

    @Test
    public void testAutoBuyForSuccessfulBuy() {
        ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
        PriceSource priceSource = new PriceSourceImpl();
        PriceListener priceListener = new PriceListenerImpl(tradeExecutionService,priceSource,10,100,"IBM");
        ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
        TradingStrategy tradingStrategy = new TradingStrategy(priceSource,priceListener);
        List<StockPriceUpdate> input = Arrays.asList(new StockPriceUpdate.Builder("IBM").withPrice(10).build());
        tradingStrategy.autoBuy(input);
        verify(tradeExecutionService, times(1))
                .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());
        assertThat(securityCaptor.getValue()).isEqualTo("IBM");
        assertThat(priceCaptor.getValue()).isEqualTo(10.00);
        assertThat(volumeCaptor.getValue()).isEqualTo(100);
    }

    @Test
    public void testAutoBuyForNoBuyWithGreaterPrice() {
        ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
        PriceSource priceSource = new PriceSourceImpl();
        PriceListener priceListener = new PriceListenerImpl(tradeExecutionService,priceSource,10,100,"IBM");
        ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
        TradingStrategy tradingStrategy = new TradingStrategy(priceSource,priceListener);
        List<StockPriceUpdate> input = Arrays.asList(new StockPriceUpdate.Builder("IBM").withPrice(100).build());
        tradingStrategy.autoBuy(input);
        verify(tradeExecutionService, never())
                .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());
    }

    @Test
    public void testAutoBuyForNoBuyWithNoMatchStock() {
        ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
        PriceSource priceSource = new PriceSourceImpl();
        PriceListener priceListener = new PriceListenerImpl(tradeExecutionService,priceSource,0,100,"GOOG");
        ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
        TradingStrategy tradingStrategy = new TradingStrategy(priceSource,priceListener);
        List<StockPriceUpdate> input = Arrays.asList(new StockPriceUpdate.Builder("IBM").withPrice(10).build());
        tradingStrategy.autoBuy(input);
        verify(tradeExecutionService, never())
                .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());
    }

    @Test
    public void testAutoBuyForNoBuyWithNullMatchStock() {
        ExecutionService tradeExecutionService = Mockito.mock(ExecutionService.class);
        PriceSource priceSource = new PriceSourceImpl();
        PriceListener priceListener = new PriceListenerImpl(tradeExecutionService,priceSource,10,100,null);
        ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> volumeCaptor = ArgumentCaptor.forClass(Integer.class);
        TradingStrategy tradingStrategy = new TradingStrategy(priceSource,priceListener);
        List<StockPriceUpdate> input = Arrays.asList(new StockPriceUpdate.Builder("IBM").withPrice(10).build());
        tradingStrategy.autoBuy(input);
        verify(tradeExecutionService, never())
                .buy(securityCaptor.capture(), priceCaptor.capture(), volumeCaptor.capture());
    }
}
