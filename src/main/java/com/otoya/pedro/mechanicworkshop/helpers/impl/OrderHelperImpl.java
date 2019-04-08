package com.otoya.pedro.mechanicworkshop.helpers.impl;

import com.otoya.pedro.mechanicworkshop.domain.order.Order;
import com.otoya.pedro.mechanicworkshop.domain.repair.Repair;
import com.otoya.pedro.mechanicworkshop.domain.repair.RepairMetadata;
import com.otoya.pedro.mechanicworkshop.helpers.OrderHelper;
import org.springframework.stereotype.Component;

@Component
public class OrderHelperImpl implements OrderHelper {

    /**
     * Estimates the orders time to finish.
     * Pre: Order object is unestimated (each repair.estimateTime == null)
     * Post: Order object is estimated (each repair.estimateTime != null)
     * Returns true if the order was estimated succesfully.
     */
    @Override
    public Boolean estimateRepairHours(Order order) {
        // estimate time for each reapair
        for (Repair repair : order.getRepairs()) {
            //update each repairs time based on it's type.
            Double estimatePerSeat = RepairMetadata.getEstimatePerSeat(repair.getType());
            Double finalEstimate = estimatePerSeat * order.getVehicle().getNumOfSeats();
            repair.setEstTimeInHours(finalEstimate);
        }
        return true;
    }

}
