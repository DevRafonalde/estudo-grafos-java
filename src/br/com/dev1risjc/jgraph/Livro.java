package br.com.dev1risjc.jgraph;

import javax.swing.*;

public class Livro implements INode {

    String numero;

    public Livro(String numero){
        this.numero = numero;
    }

    @Override
    public Object AcaoCliqueDuplo() {
        JFrame tela = new JFrame();
        tela.setSize(400,600);
        tela.setVisible(true);
        return tela;
    }

    @Override
    public Object AcaoCliqueDireito() {
        return null;
    }

    @Override
    public String toString(){
        return numero;
    }
}
