package com.devgroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Абстрактный класс для наследования функциями.
 * Он является основой для классов по вычислению погрешностей.
 */
public abstract class Stats {

    /**
     *  Выборки
     */
    protected static final ArrayList<Double> selection = new ArrayList<>();

    /**
     * Abstract class for error calculations
     * @return mean and error in calc
     */
    public abstract Pair<Double> statsCalc();

    /**
     * Проверка на промахи
     */
    protected static void MissCheck(){

        System.out.println("\n________________________________\nПроверка на промахи!");


        int scaling = BigDecimal.valueOf(selection.get(0)).scale()+2;

        //Вычисление размаха выборки
        double R = round(selection.get(selection.size() - 1) - selection.get(0), scaling);

        System.out.println("R = " + R);

        boolean f1 = true, f2 = true;
        double x_first, x_last;


        double Upn = round(UPNTaking(), 2);

        while (f1 || f2){
            System.out.println("_____________________________\n");
            x_first = round((selection.get(1) - selection.get(0))/R, scaling);
            System.out.println("L(1) = (" + selection.get(1) + " - " + selection.get(0) +")/" + R + " = " + x_first);

            x_last = round((selection.get(selection.size()-1) - selection.get(selection.size()-2))/R, scaling);
            System.out.println("L(last) = (" + selection.get(selection.size()-1) + " - " + selection.get(selection.size()-2) +")/" + R + " = " + x_last);

            f1 = !(x_first < Upn);
            f2 = !(x_last < Upn);

            if (f1){
                System.out.println("L(1) - промах, т.к. " + x_first + " >= " + Upn);
                selection.remove(0);
            }else{
                System.out.println("L(1) - не промах, т.к. " + x_first + " < " + Upn);
            }

            System.out.println();

            if (f2){
                System.out.println("L(last) - промах, т.к. " + x_last + " >= " + Upn);
                selection.remove(selection.size()-1);
            }else{
                System.out.println("L(last) - не промах, т.к. " + x_last + " < " + Upn);
            }

            System.out.println("_____________________________\n");

        }

        System.out.println("Прверка на промахи завершена!\n");
    }

    /**
     * Choosing student coefficient based on N
     * @return student coefficient
     */
    protected static double StudentError(){

        double tpn = 0;
        int N = selection.size();

        if (N <= 2){
            tpn = 12.7D;
        }else if (N == 3){
            tpn = 4.3D;
        }else if (N == 4){
            tpn = 3.2D;
        }else if (N == 5){
            tpn = 2.8D;
        }else if (N == 6){
            tpn = 2.6D;
        }else if (N == 7){
            tpn = 2.5D;
        }else if (N == 8){
            tpn = 2.4D;
        }else if (N == 9){
            tpn = 2.3D;
        }else if (N == 10){
            tpn = 2.3D;
        }else if (N == 100){
            tpn = 2.0D;
        }

        return tpn;

    }

    /**
     * Choosing Upn coefficient based on N
     * @return Upn coefficient
     */
    protected static double UPNTaking(){
        float Upn = 0;
        int N = selection.size();

        if (N <= 3){
            Upn = 0.94f;
        }else if (N == 4){
            Upn = 0.76f;
        }else if (N == 5){
            Upn = 0.64f;
        }else if (N <= 7){
            Upn = 0.51f;
        }else if (N <= 10){
            Upn = 0.41f;
        }else if (N <= 15){
            Upn = 0.34f;
        }else if (N <= 20){
            Upn = 0.3f;
        }else if (N <= 30){
            Upn = 0.26f;
        }else if (N <= 100){
            Upn = 0.2f;
        }

        return Upn;
    }

    /**
     * Method printing message and current selection
     * @param message - message to print before selection
     */
    protected static void SelectionView(String message){
        System.out.println(message);
        System.out.println(Arrays.toString(selection.toArray()));
    }

    protected static double round (double value, int places){
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
