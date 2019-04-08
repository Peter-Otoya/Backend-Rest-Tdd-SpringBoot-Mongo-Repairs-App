package com.otoya.pedro.mechanicworkshop.domain.repair;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Optionally, this guy could be a @Component which can be later used to easily load map from a config file or other ds.
 */
public class RepairMetadata {

    // Holds estimation info for each type of repair
    private static Map<Type, Double> map = ImmutableMap.of(
            Type.PROC_A, 3.0, //hours per seat
            Type.PROC_B, 0.5, //hours per seat
            Type.PROC_C, 8.0);//hours per seat

    // Possible types of repairs
    public static enum Type {PROC_A, PROC_B, PROC_C}

    // Possible status of a repair
    public static enum Status {CREATED, IN_PROGRESS, COMPLETED}

    /**
     * Returns estimate time per seat in hours for each type of procedure.
     * Will be used just within package.
     */
    public static Double getEstimatePerSeat(Type type){
        return map.get(type);
    }

}
