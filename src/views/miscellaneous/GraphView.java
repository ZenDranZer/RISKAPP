package views.miscellaneous;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import controllers.GameEngine;
import models.GameCountry;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import javax.swing.*;
import java.awt.*;

/**The GraphView is an Applet for visualizing the whole graph
 * It will generate a new frame in which the graph will be displayed.*/
public class GraphView extends JApplet {

    /**A Serializable ID*/
    private static final long serialVersionUID = 2202072534703043194L;
    /**Dimension object for the default frame dimension.*/
    private static final Dimension DEFAULT_SIZE = new Dimension(700, 500);
    /**An adapter for graph object to */
    private JGraphXAdapter<GameCountry, DefaultEdge> jgxAdapter;
    /***/
    private Graph<GameCountry, DefaultEdge> graph;

    public GraphView(GameEngine gameEngine){
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setTitle("JGraphT Adapter to JGraphX Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        graph = gameEngine.getMapGenerator().getGraphUtilObject().getCountryGraph();
    }

    public void init(){
        ListenableGraph<GameCountry, DefaultEdge> g = new DefaultListenableGraph<>(graph);
        jgxAdapter = new JGraphXAdapter<>(g);
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        int radius = 315;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}

/*GraphView graphView = new GraphView(gameEngine);
* graphView.init();*/
