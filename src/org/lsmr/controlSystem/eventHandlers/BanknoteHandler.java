package org.lsmr.controlSystem.eventHandlers;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;

public class BanknoteHandler extends EventHandler implements BanknoteValidatorListener {

	public BanknoteHandler(ControlSystem controlSystem) {
		super(controlSystem);
	}

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		this.controlSystem.increaseAmountPaid(new BigDecimal(value));
		System.out.println("Valid banknote detected, banknote is accepted.\nBalance is updated.");

	}

	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		System.out.println("Invalid banknote detected, banknote is rejected.\nBalance remains the same.");
	}
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		System.out.println("The BanknoteValidator is enabled.");
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		System.out.println("The BanknoteValidator is disabled.");
		
	}
}
