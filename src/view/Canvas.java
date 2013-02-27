package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;
import control.Controller;
import simulation.Model;


/**
 * The Canvas is the space within the view that will
 * contain a turtle and any lines drawn by it.
 * 
 * @author srwareham, Yoshi
 * 
 */
public class Canvas extends JComponent {

    /**
     * default serialization ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Frames_Per_Second for running the simulation.
     */
    public static final int FRAMES_PER_SECOND = 30;

    /**
     * 
     * Number of milliseconds in a second.
     */
    public static final int ONE_SECOND = 1000;

    /**
     * Default delay time
     */
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;

    private Dimension myBounds;
    private Model mySimulation;
    private SLogoView myView;
    private Timer myTimer;

    /**
     * Creates a default Canvas for the view.
     * 
     * @param size is the size determined by the view.
     */
    public Canvas (Dimension size) {
        myBounds = size;
        setPreferredSize(size);
        setSize(size);
    }

    /**
     * Paint the contents of the canvas.
     * 
     * @param pen used to paint shape on the screen.
     */
    @Override
    public void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // TODO: Verify if this if condition is really necessary
        if (mySimulation != null) {
            mySimulation.paint((Graphics2D) pen);
        }
    }

    /**
     * Starts the timer which is responsible for updating the model.
     * Creates the model and the controller.
     * This is within the purview of the View because at every frame the view
     * requires something to display, it gets a new snapshot from the model.
     * 
     * @param view used to create the model and controller.
     */
    public void start (SLogoView view) {
        // create a timer to animate the canvas
        myTimer = new Timer(DEFAULT_DELAY,
                            new ActionListener() {
                                @Override
                                public void actionPerformed (ActionEvent e) {
                                    step();
                                }
                            });
        mySimulation = new Model(view);
        myView.setController(new Controller(mySimulation, view));
        myTimer.start();
    }

    // TODO: we will need to add stop to the api.
    /**
     * Stops the timer animating the simulation.
     */
    public void stop () {
        myTimer.stop();
    }

    /**
     * Increments the animation one step.
     * 
     * @param simulation The model to be stepped
     */
    private void step () {
        mySimulation.update((double) FRAMES_PER_SECOND / ONE_SECOND, myBounds);
        repaint();
    }

}
