package com.devgroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ForwardStatsError extends Stats {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public Pair<Double> statsCalc() {
        System.out.println("Введите выборку (конец последовательности обозначить любым не числовым значением) >>>");

        //Получаем выборку
        while (sc.hasNextDouble()) {
            selection.add(sc.nextDouble());
        }
        sc.next();

        //Вывод неотсортированной выборки
        SelectionView("Неотсортированная выборка:");

        //Сортируем выборку в порядке возрастания
        Collections.sort(selection);

        //Вывод отсортированной выборки
        SelectionView("Отсортированная выборка:");

        //Проверка на промахи
        MissCheck();

        //Получение размера выборки
        int N = selection.size();

        //Получение суммы элементов выборки
        double sum = 0;

        for (double el :
                selection) {
            sum += el;
        }

        //Среднее выборочное значение
        double X = (float) 1 / selection.size() * sum;

        //Вычисление выборочного СКО среднего
        double SKO, pre = 0;

        for (double el :
                selection) {
            pre += Math.pow(el - X, 2);
        }

        SKO = Math.sqrt((pre) / (N * (N - 1)));

        double dX = StudentError() * SKO;

        //Вычисление (полуение) случайной погрешности
        System.out.println("Введите тип вводимых данных:\n1: Цена одного деления\n2: Приборная погрешность");

        int answ = sc.nextInt();
        boolean type = answ == 1;

        System.out.println("Введите значение:");
        double tetta = 0;
        if (type)
            tetta = sc.nextDouble() / 2;
        else
            tetta = sc.nextDouble();

        double measurementError = dX + tetta;

        //Округление
        System.out.println("Пожалуйста, посмотрите на погрешность измерений (1ое) " +
                "и выбирите до какого знака после запятой округлять\n" +
                "(В случае, если округлять надо до запятой, значение надо сделать отрицательным)");
        System.out.println("ΔL = " + measurementError);
        System.out.println("X = " + X);

        int floating = sc.nextInt();

        measurementError = Math.round(measurementError * Math.pow(10, floating)) * Math.pow(10, -floating);
        X = Math.round(X * Math.pow(10, floating)) * Math.pow(10, -floating);

        System.out.println("ΔL = " + measurementError);
        System.out.println("X = " + X);

        //System.out.println(Arrays.toString(selection.toArray()));

        System.out.println("X=L̅ ± ΔL̅ = " + X + " ± " + measurementError);

        return new Pair<>(measurementError, X);
    }
}
