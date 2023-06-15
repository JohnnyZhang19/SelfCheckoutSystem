package org.lsmr.controlSystem.eventHandlers;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CardReaderListener;

public class CardHandler extends EventHandler implements CardReaderListener {
    public CardHandler(ControlSystem controlSystem){
        super(controlSystem);
    }

    //---------------------------------------------------------------------------------
    // To do : Use case - Customer pays with credit card/debit card
    //---------------------------------------------------------------------------------
    // When customer tries to pay with card, the CardReaderListener will keep notify this CardHandler that certain event has occurred.
    // React with the event, and update the state of the controlSystem.
    // E.G: Ask for pin when card is inserted.

    


	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardInserted(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardRemoved(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardTapped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardSwiped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		// TODO Auto-generated method stub
		
	};


}
