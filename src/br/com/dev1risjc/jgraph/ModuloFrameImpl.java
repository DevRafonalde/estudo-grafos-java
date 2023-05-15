package br.com.dev1risjc.jgraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class ModuloFrameImpl implements IModuloFrame {

    GeradorGrafo telaGrafos;

    public <O> ModuloFrameImpl(List<JMenuItem> itensPopup, HashMap<O, O> filiacoes) {
        this.telaGrafos = new GeradorGrafo(itensPopup, filiacoes);
        telaGrafos.init();
        telaGrafos.setLocationRelativeTo(null);
        telaGrafos.setTitle("Grafo de filiação TRA");
        telaGrafos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaGrafos.pack();
    }

    @Override
    public void setVisible(boolean visible) {
        telaGrafos.setVisible(visible);
    }

    @Override
    public void setSize(int x, int y) {
        telaGrafos.setSize(x, y);
    }

    @Override
    public void limparTela() {
        telaGrafos.limparTela();
    }

    @Override
    public void criarVertices() {
        telaGrafos.criarVertices();
    }

    @Override
    public void criarArestas() {
        telaGrafos.criarArestas();
    }

    @Override
    public void refazerTela() {
        limparTela();
        criarVertices();
        criarArestas();
    }
}
