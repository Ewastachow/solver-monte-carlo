/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermontecarlo;

import static java.lang.Math.pow;

/**
 *
 * @author Un1kalny
 */
public class Wielomian {
    int stopien;
    double[] wspolczynniki;
    
    public Wielomian() {
        
    }
    
    public Wielomian(int stopien, double[] wspolczynniki) {
        this.stopien = stopien;
        this.wspolczynniki = wspolczynniki;
    }
    
    double wartoscWPkt(double x) {
        double tmp = 0.0;
        for(int i = stopien - 1; i >= 0; --i) {
            tmp += wspolczynniki[i] * pow(x, i);
        }
        return tmp;
    }
    
    @Override
    public String toString() {
        String tmp = "";
        for(int i = stopien - 1; i >= 0; --i) {
            if(wspolczynniki[i] != 0) {
                if(i == 0)
                    tmp += "" + wspolczynniki[i] + " + ";
                else if(i == 1)
                    tmp += "" + wspolczynniki[i] + "x" + " + ";
                else
                    tmp += "" + wspolczynniki[i] + "x^" + i + " + ";
            }
        }
        return tmp.substring(0, tmp.length() - 3);
    }
    
}
