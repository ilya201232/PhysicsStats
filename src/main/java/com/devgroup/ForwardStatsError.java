package com.devgroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


/**
 * Класс предназначен для вычисления погрешностей прямых измерений
 */

public class ForwardStatsError extends Stats {

    /** Class Scanner for input */
    private static final Scanner sc = new Scanner(System.in);

    private int scaling;

    //Размер выборки
    private int N;

    //Среднее выборочное значение
    private double X;

    //Среднеквадратичное отклонение
    private double RMS;

    //Случайная погрешность
    private double dX;
    
    //Приборная погрешность
    private double theta;

    //Выборочное СКО среднего
    private double measurementError;

    /**
     * This method calculates errors of direct measurements.
     * @return object Pair with mean and error in calc
     */
    @Override
    public Pair<Double> statsCalc() {
        System.out.println("Введите выборку (конец последовательности обозначить любым не числовым значением) >>>");

        //Получаем выборку
        Take_Selection();

        //Сортируем выборку в порядке возрастания
        SortSelection();

        //Проверка на промахи
        MissCheck();

        //Среднее выборочное значение
        Sample_mean();

        //Вычисление СКО
        Calculating_RMS();

        //Оценка случайной погрешности
        Random_error();

        //Вычисление (полуение) приборной погрешности
        Instrument_error();

        //Вычисление выборочного СКО среднего
        Sample_RMS_average();

        //Округление
        Round_local();

        System.out.println("X=L̅ ± ΔL̅ = " + X + " ± " + measurementError);

        return new Pair<>(X, measurementError);
    }



    private void Take_Selection(){
        while (sc.hasNextDouble()) {
            selection.add(sc.nextDouble());
        }
        sc.next();

        System.out.println(BigDecimal.valueOf(selection.get(0)).scale());
        scaling = BigDecimal.valueOf(selection.get(0)).scale() + 2;
        N = selection.size();
    }

    private void SortSelection(){
        //Вывод неотсортированной выборки
        SelectionView("Неотсортированная выборка:");

        //Сортируем выборку в порядке возрастания
        Collections.sort(selection);

        //Вывод отсортированной выборки
        SelectionView("Отсортированная выборка:");
    }

    private void Sample_mean(){
        //Получение суммы элементов выборки
        double sum = 0;

        for (double el :
                selection) {
            sum += el;
        }

        X = round(sum / selection.size(), scaling);

        //TODO - КОСТЫЛЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (BigDecimal.valueOf(X).scale() < scaling){
            StringBuilder s = new StringBuilder(scaling);
            for (int i = 0; i < scaling - BigDecimal.valueOf(X).scale(); i++) {
                s.append("0");
            }
            System.out.println("Среднее выборочное значение: " + X + s);
        }else{
            System.out.println("Среднее выборочное значение: " + X);
        }

        System.out.println();

    }

    private void Calculating_RMS(){
        double pre = 0;
        int i = 0;

        System.out.print("СКО = sqrt(( ");

        for (double el :
                selection) {
            pre += Math.pow(el - X, 2);
            i++;
            if (i == selection.size()){
                System.out.print("(" + el + "-" + X + ")^2");
            }else
                System.out.print("(" + el + "-" + X + ")^2 + ");
        }

        RMS = Math.sqrt((pre) / (N * (N - 1)));

        System.out.println(" )/( " + N + "*" + (N-1) + " ))");

        RMS = round(RMS, scaling);

        System.out.println("СКО: " + RMS);
        System.out.println();
    }

    private void Instrument_error(){
        System.out.println("Введите тип вводимых данных:\n" +
                "1: Цена одного деления\n" +
                "2: Приборная погрешность");

        int answ = sc.nextInt();
        boolean type = answ == 1;

        System.out.println("Введите значение:");
        theta = 0;
        if (type)
            theta = sc.nextDouble() / 2;
        else
            theta = sc.nextDouble();

        System.out.println("θ(L) = " + theta + "\n");
    }

    private void Random_error(){
        dX = round(StudentError() * RMS, scaling);
        System.out.println("Оценка случайной погрешности по Стьюденту: ");
        System.out.println("ΔL = " + StudentError() + "*" + RMS + " = " + (StudentError() * RMS) + " ≈ " + dX + " (коэффициент Стюдента: " + StudentError() + ")");

    }

    private void Sample_RMS_average(){
        System.out.println("Выберите способ получения выборочного СКО среднего:\n" +
                "1. Сложение\n" +
                "2. Корень из суммы квадратов\n");

        if (sc.nextInt() == 1){
            measurementError = dX + theta;

            System.out.println("ΔL̅ = " + dX + " + " + theta + " = " + measurementError);
        } else {
            measurementError = Math.sqrt(dX*dX + theta * theta);
            measurementError = round(measurementError, scaling);

            System.out.println("ΔL̅ = sqrt(" + dX*dX + " + " + theta*theta + " = " + measurementError + ")");
        }
    }

    private void Round_local(){
        System.out.println("Пожалуйста, посмотрите на погрешность измерений (1ое) " +
                "и выбирите до какого знака после запятой округлять\n" +
                "(В случае, если округлять надо до запятой, значение надо сделать отрицательным)");
        System.out.println("ΔL̅ = " + measurementError);
        System.out.println("L̅ = " + X);

        int floating = sc.nextInt();

        measurementError = round(measurementError, floating);
        X = round(X, floating);

        System.out.println("ΔL̅ = " + measurementError);
        System.out.println("L̅ = " + X);
    }


}
