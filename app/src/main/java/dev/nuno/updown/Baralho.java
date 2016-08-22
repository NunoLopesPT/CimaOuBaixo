package dev.nuno.updown;

import java.util.ArrayList;


public class Baralho {
    String[] valor = new String[]{"2","3","4","5","6","7","8","9","10","D","V","R","As"};
    String[] naipe = new String[]{"Ouros", "Copas", "Paus", "Espadas"};
    ArrayList<Carta> Baralho = new ArrayList<Carta>();

    public void criar(){
        for (int i = 0; i < valor.length; i++) {
            for (int j = 0; j < naipe.length; j++) {
                Baralho.add(new Carta(i, j));
            }
        }
    }
}
