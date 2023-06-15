package org.lsmr.controlSystem.eventHandlers;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CoinValidatorListener;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import java.math.BigDecimal;

public class CoinHandler extends EventHandler implements CoinValidatorListener {
    public CoinHandler(ControlSystem controlSystem) {
        super(controlSystem);
    }

    @Override
    public void validCoinDetected(CoinValidator validator, BigDecimal value) {	
    	this.controlSystem.increaseAmountPaid(value);
    	System.out.println("Valid coin detected, coin is accepted.\nBalance is updated.");
    }

    @Override
    public void invalidCoinDetected(CoinValidator validator) {
        System.out.println("Invalid coin detected, coin is rejected.\nBalance remains the same.");
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("The CoinValidator is enabled.");
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("The CoinValidator is disabled.");
    }
}