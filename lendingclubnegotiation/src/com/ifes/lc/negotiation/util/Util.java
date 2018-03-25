package com.ifes.lc.negotiation.util;

/**
 * Created by diegosoaresub on 25/06/17.
 */
public class Util {

    public static Double round2Places(Double value){
        return Math.round(value*100)/100.0;
    }


    public static Double round(Double value){
        return Math.ceil(value);
    }

}
