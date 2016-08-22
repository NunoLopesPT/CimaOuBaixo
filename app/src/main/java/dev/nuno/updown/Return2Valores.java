package dev.nuno.updown;


public class Return2Valores {
    private boolean certo;
    private String nome;
    private String aviso;

    public Return2Valores(boolean certo, String nome, String aviso) {
        this.certo = certo;
        this.nome = nome;
        this.aviso = aviso;
    }

    public boolean getCerto() {
        return certo;
    }

    public String getNome() {
        return nome;
    }

    public String getAviso() {
        return aviso;
    }
}
