package dev.nuno.updown;

public class Carta {
    private int valores;
    private int naipes;

    final String[] valor = new String[]{"2","3","4","5","6","7","8","9","10","D","V","R","A"};
    final String[] naipe = new String[]{"♦", "♥", "♣", "♠"};

    public Carta(int valor, int naipe){
        valores = valor;
        naipes = naipe;
    }

    public String toString(){
        return valor[valores] + "_" + naipe[naipes];
    }

    public int getNaipes() {
        return naipes;
    }

    public int getValores() {
        return valores;
    }
}
