package br.com.dev1risjc;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import javax.swing.*;
import java.awt.*;

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

        ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);


        g.addVertex(traAtual);
        g.addVertex(traAnterior1);
        g.addVertex(traAnterior2);
        g.addVertex(traPosterior1);
        g.addVertex(traPosterior2);

        g.addEdge(traAnterior1, traAtual);
        g.addEdge(traAnterior2, traAtual);
        g.addEdge(traAtual, traPosterior1);
        g.addEdge(traAtual, traPosterior2);


        mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);

        layout.setHorizontal(false);
        layout.setEdgeRouting(false);
        layout.setNodeDistance(100);

        layout.execute(jgxAdapter.getDefaultParent());
    }
}
