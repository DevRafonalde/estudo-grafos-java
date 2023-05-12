package br.com.dev1risjc.jgraph;

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

//    LEMBRAR DE PEDIR ISSO AQUI
//    public void init(JPopupMenu popupMenu) {
    public void init() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setFont(new java.awt.Font("Trebuchet MS", Font.PLAIN, 12)); // NOI18N

        JMenuItem menuItem = new JMenuItem();
        menuItem.setFont(new java.awt.Font("Trebuchet MS", Font.PLAIN, 12)); // NOI18N
        menuItem.setText("Abrir Pasta");
        popupMenu.add(menuItem);

        ListenableGraph<INode, DefaultEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        JGraphXAdapter<INode, DefaultEdge> jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter){
            @Override
            protected void installDoubleClickHandler(){
                this.graphControl.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                            mxCell cell = (mxCell) getGraph().getSelectionModel().getCell();
                            System.out.println(cell.getValue());

                            INode livro = new Livro(cell.getValue().toString());
                            livro.AcaoCliqueDuplo();
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
