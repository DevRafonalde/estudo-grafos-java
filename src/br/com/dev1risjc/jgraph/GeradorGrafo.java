package br.com.dev1risjc.jgraph;

import br.com.dev1risjc.jgraph.helpers.mapper.DozerMapper;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeradorGrafo <O> extends JFrame {

    List<JMenuItem> itensPopup;
    ListenableGraph<INode, DefaultEdge> grafo;
    List<INode> listaVertices = new ArrayList<>();
    HashMap<Livro, Livro> filiacoes;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 330);

    public GeradorGrafo(List<JMenuItem> itensPopup, HashMap<O, O> filiacoes) {
        this.itensPopup = itensPopup;
        this.filiacoes = DozerMapper.parseHashMapObject(filiacoes, Livro.class);
    }

//    public static void main(String[] args) {
//        GeradorGrafo frame = new GeradorGrafo(new ArrayList<>(), new HashMap<>());
//        frame.init();
//        frame.setLocationRelativeTo(null);
//        frame.setTitle("JGraphT Adapter to JGraphX Demo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//    }

    public void init() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));

        if (!itensPopup.isEmpty()) {
            for (JMenuItem menuItem : itensPopup) {
                popupMenu.add(menuItem);
            }
        }

        grafo = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        JGraphXAdapter<INode, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(grafo);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter) {
            @Override
            protected void installDoubleClickHandler(){
                this.graphControl.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                            mxCell cell = (mxCell) getGraph().getSelectionModel().getCell();

                            limparTela();
                        }
                    }

                    private void showPopupMenu(MouseEvent e) {
                        popupMenu.show(graphControl, e.getX(), e.getY());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopupMenu(e);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.isPopupTrigger()) {
                            showPopupMenu(e);
                        }
                    }
                });
            }
        };

        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);

        mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);
        layout.setHorizontal(false);

        layout.setEdgeRouting(false);
        layout.setNodeDistance(100);
        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void criarVertices() {
        for (Livro l : filiacoes.keySet()) {
            grafo.addVertex(l);
            listaVertices.add(l);
            grafo.addVertex(filiacoes.get(l));
            listaVertices.add(filiacoes.get(l));
        }
    }

    public void criarArestas() {
        for (Livro l : filiacoes.keySet()) {
            grafo.addEdge(l, filiacoes.get(l));
        }
    }

    public void limparTela() {
        grafo.removeAllVertices(listaVertices);
        listaVertices.clear();
    }
}
