package org.lsmr.controlSystem.eventHandlers;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.devices.SimulationException;

public class EventHandler {
    public ControlSystem controlSystem;
    
    public EventHandler(ControlSystem controlSystem){
        if(controlSystem==null){
            throw new SimulationException("controlSystem is null!");
        }
        this.controlSystem = controlSystem;
    }

    

}
