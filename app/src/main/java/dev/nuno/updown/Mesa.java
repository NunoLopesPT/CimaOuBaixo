package dev.nuno.updown;

import java.util.Random;


public class Mesa {
    private int cartasnamesa;
    private int posicao = 0;
    Random rand = new Random();
    Carta[] Linha1 = new Carta[5];
    Carta[] Linha2 = new Carta[5];
    Baralho baralho = new Baralho();

    public Mesa(int cartasnamesa){
        this.cartasnamesa = cartasnamesa;
    }

    public void inicio(){
        baralho.criar();
        for (int i = 0; i < cartasnamesa; i++) {
            int random = rand.nextInt(baralho.Baralho.size());
            Linha1[i] = baralho.Baralho.get(random);
            baralho.Baralho.remove(random);
        }
    }

    public Return2Valores baixo(int posicao){
        boolean certo = false;
        String aviso = null;
        if(posicao < 0){
            posicao = -posicao-1;
        }
        int random = rand.nextInt(baralho.Baralho.size()-1);
        Linha2[posicao] = baralho.Baralho.get(random); // Mete a carta aleatoria do baralho na mesa
        aviso = "Ganhaste es um bocado de merda";
        if( baralho.Baralho.get(random).getValores() < Linha1[posicao].getValores()){
            this.posicao += 1;
            certo = true;
        }

        else if(baralho.Baralho.get(random).getValores() > Linha1[posicao].getValores()){
            aviso = "Bebe: " + (baralho.Baralho.get(random).getValores()-Linha1[posicao].getValores() + " golo");
            if((baralho.Baralho.get(random).getValores()-Linha1[posicao].getValores())>1){
                aviso += "s";
            }
            this.posicao = 0;
        }
        else {
            aviso = "Bebe: Penalti !!";
            this.posicao = 0;
        }

        baralho.Baralho.remove(baralho.Baralho.get(random)); //Tira do baralho a carta aleatoria
        return new Return2Valores(certo, Linha2[posicao].toString(), aviso);
    }

    public Return2Valores cima(int posicao){
        boolean certo = false;
        String aviso = null;

        if(posicao < 0){
            posicao = -posicao-1;
        }

        int random = rand.nextInt(baralho.Baralho.size()-1);
        Linha2[posicao] = baralho.Baralho.get(random); // Mete a carta aleatoria do baralho na mesa
        aviso = "Ganhaste es um bocado de merda";
        if( baralho.Baralho.get(random).getValores() > Linha1[posicao].getValores()){
            this.posicao += 1;
            certo = true;
        }
        else if( baralho.Baralho.get(random).getValores() < Linha1[posicao].getValores()){
            aviso = "Bebe: " + (Linha1[posicao].getValores()-baralho.Baralho.get(random).getValores() + " golo");
            if((Linha1[posicao].getValores()-baralho.Baralho.get(random).getValores()>1)){
                aviso += "s";
            }
            this.posicao = 0;
        }
        else {baralho.Baralho.remove(baralho.Baralho.get(random)); //Tira do baralho a carta aleatoria
            aviso = "Bebe: Penalti !!";
            this.posicao = 0;
        }
        baralho.Baralho.remove(baralho.Baralho.get(random)); //Tira do baralho a carta aleatoria
        return new Return2Valores(certo, Linha2[posicao].toString(), aviso);
    }

    public void organizar(){
        for(int i = 0; i < 5; i++){
            if(Linha2[i] != null){
                baralho.Baralho.add(Linha1[i]);
                Linha1[i] = Linha2[i];
                Linha2[i] = null;
            }
        }
    }

    public boolean checkwin(boolean certo){
        System.out.println(certo + " " + posicao);
        if(certo && (posicao == 0 || posicao == 5)){
            organizar();
            return true;
        }
        return false;
    }
    public int getCartasnamesa() {
        return cartasnamesa;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
