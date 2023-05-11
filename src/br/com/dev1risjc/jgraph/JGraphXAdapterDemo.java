package br.com.dev1risjc.jgraph;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JGraphXAdapterDemo extends JFrame {

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 330);

    public static void main(String[] args) {
        JGraphXAdapterDemo frame = new JGraphXAdapterDemo();
        frame.init();
        frame.setLocationRelativeTo(null);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void init() {

        String traAtual = "traAtual";

        String traAnterior1 = "traAnterior1";
        String traAnterior2 = "traAnterior2";
        String traPosterior1 = "traPosterior1";
        String traPosterior2 = "traPosterior2";

        ListenableGraph<INode, DefaultEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        JGraphXAdapter<INode, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter){
            @Override
            protected void installDoubleClickHandler(){
                this.graphControl.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent var1) {
                        System.out.println("teste");
                        mxCell cell = (mxCell) getGraph().getSelectionModel().getCell();
                        System.out.println(cell.getValue());

                        INode livro = new Livro("10");
                        livro.AcaoCliqueDuplo();

                    }
                });
            }
        };
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);


        INode n1 = new Livro("10");
        INode n2 = new Livro("20");


        g.addVertex(n1);
        g.addVertex(n2);

        g.addEdge(n1, n2);


        mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);

        layout.setHorizontal(false);
        layout.setEdgeRouting(false);
        layout.setNodeDistance(100);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}
