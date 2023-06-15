package org.lsmr.controlSystem;

import org.lsmr.controlSystem.eventHandlers.CardHandler;
import org.lsmr.controlSystem.eventHandlers.DispenseHandler;
import org.lsmr.controlSystem.eventHandlers.BagAreaHandler;
import org.lsmr.controlSystem.eventHandlers.ScanHandler;
import org.lsmr.controlSystem.eventHandlers.BanknoteHandler;
import org.lsmr.controlSystem.eventHandlers.CoinHandler;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.Barcode;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

/**
 * ControlSystem which contains SelfCheckoutStation, EventHandlers to control the flow of events.
 * This class will eventually contains a "Main"
 */
public class ControlSystem {
    public SelfCheckoutStation station;
    public ScanHandler scanHandler;
    public BagAreaHandler bagAreaHandler;
    public CardHandler cardHandler;
    public DispenseHandler dispenseHandler; 
    public CoinHandler coinHandler;
    public BanknoteHandler banknoteHandler;

    // TODO: use case customer scans their membership card
    // May need to
    public String membership;

    private Map<Barcode, BarcodedProduct> database;
    private ArrayList<BarcodedProduct> scannedItems;
    private BigDecimal total;
    private BigDecimal amountPaid;
    private boolean baggageState;

    /**Initial the ControlSystem
     * 
     * @param currency  
     *          the currency this system will use
     * @param banknoteDenominations
     *          the banknote denominations that accept
     * @param coinDenominations
     *          the coin denominations that accept
     * @param scaleMaximumWeight
     *          the maximum weight that scale can hold
     * @param scaleSensitivity
     *          the minimum weight change can be detected 
     */
    public ControlSystem(Currency currency, int[] banknoteDenominations, BigDecimal[] coinDenominations,
            			 int scaleMaximumWeight, int scaleSensitivity,Map<Barcode, BarcodedProduct> database) {
        this.station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight,
                							   scaleSensitivity);

        
        //  initial eventHandlers and pass reference of this controlSystem to handlers in order to communicate
        this.coinHandler = new CoinHandler(this);
        this.banknoteHandler = new BanknoteHandler(this);
        this.scanHandler = new ScanHandler(this);
        this.bagAreaHandler = new BagAreaHandler(this);
        this.cardHandler = new CardHandler(this);
        this.dispenseHandler = new DispenseHandler(this);

        //  initial the database
        if(database==null) {
        	throw new SimulationException("databse is null!");
        }
        this.database = database;

        //  inital total, and amountPaid.
        this.total = new BigDecimal(0);
        this.amountPaid = new BigDecimal(0);


        //  scanned item for virtual cart
        this.scannedItems = new ArrayList<>();
        this.baggageState = true;
    }

    /**
     *  Add a BarcodedProduct into virtual cart
     * @param item
     */
    public void addScannedItem(BarcodedProduct item) {
    		scannedItems.add(item);
    }
    
    /**
     *  increase the total
     * @param value
     */
    public void increaseTotal(BigDecimal value){
        this.total = this.total.add(value);
    }

    /**
     *  decrease the total
     * @param value
     */
    public void decreaseTotal(BigDecimal value) {
        this.total = this.total.subtract(value);
    }
    
    /**
     *  return the total 
     */
    public BigDecimal getTotal(){
        return new BigDecimal(this.total.toString());
    }
    
    /**
     *  increase the amount paid
     * @param value
     */
    public void increaseAmountPaid(BigDecimal value) {
    	this.amountPaid = this.amountPaid.add(value);
    }
    
    /**
     *  decrease the amount paid
     * @param value
     */
    public void decreaseAmountPaid(BigDecimal value) {
    	this.amountPaid = this.amountPaid.subtract(value);
    }
    
    /**
     *  get the amount paid
     */
    public BigDecimal getAmountPaid(){
        return new BigDecimal(this.amountPaid.toString());
    }
    
    /**
     *  set BaggageState
     * @param value
     *          the value to assign to baggageState
     */
    public void setBaggageState(boolean value) {
    	this.baggageState = value;
    }
    
    /**
     * 
     * @return
     *      the baggageState
     */
    public boolean getBaggageState() {
    	return new Boolean(this.baggageState);
    }

    /**
     * check whether amountPaid excessed the total
     * @return
     */
    public boolean requiredFundsMet() {
    	if (this.getAmountPaid().compareTo(this.getTotal())!=-1) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * 
     * @return
     *      the database of products and items
     */
    public Map<Barcode,BarcodedProduct> getDatabase() {
        return new HashMap<Barcode,BarcodedProduct>(this.database);
    }


    public void DispenseChanges(){
        this.dispenseHandler.DispenseChanges();
    }

}