package org.lsmr.controlSystem.eventHandlers;

import java.math.BigDecimal;

import org.lsmr.controlSystem.ControlSystem;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BarcodeScannerListener;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;

public class ScanHandler extends EventHandler implements BarcodeScannerListener {
    public ScanHandler(ControlSystem controlSystem) {
        super(controlSystem);
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("BarcodeScanner is enabled.");
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        System.out.println("BarcodeScanner is disabled.");
    }

    /**
     *  If baggageState is true, we search for the barcodedProduct in database and add the price to total;
     *  otherwise, ignores and warns customer to place/remove item in/from bagging area. 
     * @param   barcodeScanner
     *          the scanner that scanned the barcode
     * @param   barcode
     *          the scanned barcode
     * @throws  SimulationException
     *          If the there is no item matches the barcode in the database 
     */
    @Override
    public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) throws SimulationException {

        if (this.controlSystem.getBaggageState()) {
            BarcodedProduct item = this.controlSystem.getDatabase().get(barcode);
            if (item == null) {
                throw new SimulationException("Barcode "+barcode.toString()+" is not in the Barcode Product Database!");
            }

            BigDecimal price = item.getPrice();
            this.controlSystem.addScannedItem(item);
            this.controlSystem.increaseTotal(price);
        } else {
            System.out.println("Please place/remove item in/from bagging area before scanning next item.");
        }
    };

}
