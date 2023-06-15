package org.lsmr.controlSystem.eventHandlers;

import org.lsmr.controlSystem.ControlSystem;

public class DispenseHandler extends EventHandler{ 
    // The following interface realization may be needed since simply emit banknote and coin from dispenser may not be enough. 
    // One possible reason for realize the interfaces can be checking whether the banknote or coin is successfully dispensed after a dispense.
    // Implements BanknoteDispenserListener, CoinDispenser {


    public DispenseHandler(ControlSystem controlSystem) {
        super(controlSystem);

    }

        //---------------------------------------------------------------------------------
    // TODO : Use case - Customer receives change.
    //---------------------------------------------------------------------------------
    // You can call requiredFundsMet() to check whether the customer paid the total already.
    // If the requiredFundsMet() is true, then calculate the amount that customer should receive and dispense it by interact with the hardware:this.station.
    // I.E: Dispense coins and banknotes with Dispensers.
    // Don't forget to change the total && amountPaid.   
    public void DispenseChanges(){

    }
    
}
