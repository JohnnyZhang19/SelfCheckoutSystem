package org.lsmr.controlSystem.eventHandlers;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ElectronicScaleListener;

public class BagAreaHandler extends EventHandler implements ElectronicScaleListener {
    public BagAreaHandler(ControlSystem controlSystem) {
        super(controlSystem);
    }

    /**
     * announce BaggingArea is enabled
     * @param device
     *         the BaggingArea
     * 
     */
    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("The Bagging Area is enabled.");
    }

    /**
     * announce BaggingArea is disabled
     * @param device
     *          the BaggingArea
     */
    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("The Bagging Area is disabled.");
    }


    /**
     * Annouce weight has been changed, and check whether currentWeights on scale and Bagging Area 
     * @param scale
     * 
     * @param weightInGrams
     *          this should be equal to the currentWeight of the Baggaing Area
     */
    @Override
    public void weightChanged(ElectronicScale scale, double weightInGrams) throws SimulationException {
        System.out.println("The weight is changed from the last scale");
        try {
            double scaleCurrentWeight = this.controlSystem.station.scale.getCurrentWeight();
            double bagAreaCurrentWeight = this.controlSystem.station.baggingArea.getCurrentWeight();

            if (scaleCurrentWeight != bagAreaCurrentWeight) {
                this.controlSystem.setBaggageState(false);
                System.out.println("Weight Baggaging Area doesn't match weight on Scale. Please add/remove scanned/unscanned items.");
            }
            else {
                this.controlSystem.setBaggageState(true);
            }
        } catch (OverloadException e) {
            throw new SimulationException(e);
        }

    }

    /** 
     * Annouce that excessive weight has been placed.
     * @param scale
     *          the Bagging Area
     */
    @Override
    public void overload(ElectronicScale scale) {
        System.out.println("Excessive weight has been placed, please remove the item.");
    }

    /**
     * Annouce that excessive weight has been removed.
     * @param scale
     *          the Bagging Area
     */
    @Override
    public void outOfOverload(ElectronicScale scale) {
        System.out.println("Excessive weight is removed.");
    }


}
