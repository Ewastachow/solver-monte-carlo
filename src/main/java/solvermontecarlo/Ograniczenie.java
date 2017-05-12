/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solvermontecarlo;

import java.util.*;
import net.objecthunter.exp4j.*;


public class Ograniczenie {
    String ograniczenie;
    String[] tokens;
    Expression[] wyrazenia;
    String znak;
    
    public Ograniczenie() {
        
    }
    
    public Ograniczenie(String ograniczenie, String[] tokens, Expression[] wyrazenia, String znak) {
        this.ograniczenie = ograniczenie;
        this.tokens = tokens;
        this.wyrazenia = wyrazenia;
        this.znak = znak;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < tokens.length; ++i) {
            if(tokens[i] != null)
                s += tokens[i] + "\n";
        }
        return s;
    }
    
}
