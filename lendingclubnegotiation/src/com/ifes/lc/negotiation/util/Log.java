package com.ifes.lc.negotiation.util;

/**
 * Created by diegosoaresub on 03/07/17.
 */
public class Log {

    public static int level;

    public static void println(Object str){
        println(1, str);
    }

    public static void println(){
        println(1, "");
    }


    public static void println(int lvl, Object str){
        if (level >= lvl)
            System.out.println(str.toString());
    }


    public static void print(Object str){
        print(1, str);
    }

    public static void print(){
        print(1, "");
    }


    public static void print(int lvl, Object str){
        if (level >= lvl)
            System.out.print(str.toString());
    }


}
