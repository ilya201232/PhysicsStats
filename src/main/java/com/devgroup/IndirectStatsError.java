package com.devgroup;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for calculating Indirect Stats Error
 */
public class IndirectStatsError extends Stats {

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public Pair<Double> statsCalc() {

        System.out.print("Введите кол-во переменных >>> ");

        int params = sc.nextInt();
        int order = 1;

        /*
         * Variables and it data:
         * 1) name
         * 2) diff
         * 3) it's DerivativeStructure object
         */
        HashMap<Character, PairDiv<Pair<Double>, DerivativeStructure>> vars = new HashMap<>();

        //Initialising vars
        for (int i = 0; i < params; i++) {
            System.out.println("Введите название переменной на латиннице: (MAX 1 символ)");

            //Getting it's name
            char name = sc.next().charAt(0);

            /*Making object of class ForwardStatsError */
            ForwardStatsError pop = new ForwardStatsError();

            //Getting result of calc errors of direct measurements
            Pair<Double> res = pop.statsCalc();


            DerivativeStructure var = new DerivativeStructure(params, order, res.getX1());

            vars.put(name, new PairDiv<>(res, var));
        }

        //Инициализирование функции!!!

        /*DerivativeStructure g = x.pow(y);
        //DerivativeStructure g = f.log();

        System.out.println("g        = " + g.getValue());
        System.out.println("dg/dx    = " + g.getPartialDerivative(1, 0));
        System.out.println("dg/dy    = " + g.getPartialDerivative(0, 1));
        System.out.println("d2g/dx2  = " + g.getPartialDerivative(2, 0));
        System.out.println("d2g/dy2  = " + g.getPartialDerivative(0, 2));*/

        return new Pair<>(0.2, 0.2);
    }
}

/**
 * Class for containing two different vars in pairs (some kind of Map, but for 1 connection)
 * @param <P> - first parameter type
 * @param <V> - second parameter type
 */
class PairDiv<P, V> {
    private P x1;
    private V x2;

    public PairDiv(P x1, V x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public void setX1(P x1) {
        this.x1 = x1;
    }

    public void setX2(V x2) {
        this.x2 = x2;
    }

    public P getX1() {
        return x1;
    }

    public V getX2() {
        return x2;
    }
}
