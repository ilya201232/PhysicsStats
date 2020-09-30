package com.devgroup;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Stats {
    protected static final ArrayList<Double> selection = new ArrayList<>();

    public abstract Pair<Double> statsCalc();

    protected static void MissCheck(){

        //Вычисление размаха выборки
        double R = selection.get(selection.size() - 1) - selection.get(0);

        boolean f1 = true, f2 = true;
        double x_first, x_last;


        double Upn = UPNTaking();

        while (f1 || f2){
            x_first = (selection.get(1) - selection.get(0))/R;
            x_last = (selection.get(selection.size()-1) - selection.get(selection.size()-2))/R;

            f1 = !(x_first < Upn);
            f2 = !(x_last < Upn);

            if (f1){
                selection.remove(0);
            }

            if (f2){
                selection.remove(selection.size()-1);
            }
        }
    }

    protected static double StudentError(){

        float tpn = 0;
        int N = selection.size();

        if (N <= 2){
            tpn = 12.7f;
        }else if (N == 3){
            tpn = 4.3f;
        }else if (N == 4){
            tpn = 3.2f;
        }else if (N == 5){
            tpn = 2.8f;
        }else if (N == 6){
            tpn = 2.6f;
        }else if (N == 7){
            tpn = 2.5f;
        }else if (N == 8){
            tpn = 2.4f;
        }else if (N == 9){
            tpn = 2.3f;
        }else if (N == 10){
            tpn = 2.3f;
        }else if (N == 100){
            tpn = 2.0f;
        }

        return tpn;

    }

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

    protected static void SelectionView(String message){
        System.out.println(message);
        System.out.println(Arrays.toString(selection.toArray()));
    }

}
