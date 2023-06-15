package org.lsmr.testing;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.controlSystem.eventHandlers.CoinHandler;
import org.lsmr.controlSystem.eventHandlers.ScanHandler;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.products.BarcodedProduct;


public class CoinHandlerTest {

	private CoinHandler coinHandler;
	private CoinValidator validator;
	private ControlSystem controlSystem;
	private int[] banknoteDenominations = {5};
	private BigDecimal[] coinDenominations = {new BigDecimal(1)};
	private Coin coin = new Coin(new BigDecimal(1), Currency.getInstance("CAD"));
	private Map<Barcode,BarcodedProduct> database = new HashMap<Barcode,BarcodedProduct>(){{
		put(new Barcode("12345"),new BarcodedProduct(new Barcode("12345"), "Pen", new BigDecimal(4)));
	}};
	

	@Before
	public void setUp() throws Exception {
		this.controlSystem = new ControlSystem(Currency.getInstance("CAD"),banknoteDenominations,coinDenominations,500,1,database);
		this.coinHandler = this.controlSystem.coinHandler;
		this.validator = this.controlSystem.station.coinValidator;
	}

	@After
	public void tearDown() throws Exception {
		this.controlSystem = null;
		this.coinHandler = null;
		this.validator = null;
	}
	

	
	@Test
	public void validCoinDetected_hasSpace_AmountPaidIncreased() {
		this.coinHandler.validCoinDetected(this.validator, new BigDecimal(5));
		assertEquals(this.controlSystem.getAmountPaid(),new BigDecimal(5));
	}
	
	@Test
	public void validCoinDetected_hasNoSpace_AmountPaidStaysTheSame() throws DisabledException {
		for(int i=0;i<this.controlSystem.station.COIN_STORAGE_CAPACITY;i++) {
			validator.accept(coin);
		}
		// hasSpace() in CoinValidator will always return true, potiential a bug to be fixed.
		assertFalse(this.validator.hasSpace());
		
		this.coinHandler.validCoinDetected(this.validator, new BigDecimal(5));
		assertEquals(this.controlSystem.getAmountPaid(),new BigDecimal(0));
	}
	
    @Test
    public void invalidCoinDetected_DoNothing() {
        this.coinHandler.invalidCoinDetected(validator);
    }

    @Test
    public void enabled_DoNothing() {
        this.coinHandler.enabled(validator);
    }

    @Test
    public void disabled_DoNothing() {
        this.coinHandler.disabled(validator);
    }
	
	
	
}
