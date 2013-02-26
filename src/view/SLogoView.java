package view;

import java.awt.Dimension;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JFrame;

import simulation.Model;

import control.Controller;

/**
 * The Abstract that all implementations of a view for this SLogo implementaiton will use.
 * @author srwareham
 *
 */
public abstract class SLogoView extends JFrame {

	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.";
	private static final String ENGLISH = "English";
/**
 * Preferred Dimensions of the Canvas.
 */
    public static final Dimension PREFERRED_CANVAS_SIZE = new Dimension(400, 400);

    protected ResourceBundle myResources;
    private Canvas myCanvas;
    protected Controller myController;
    
    
    /**
     * Creates a SLogoView.
     * @param title The title of this View
     * @param language The desired language for the View
     */
    public SLogoView(String title, String language) {
        try {
            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);    
        }
        catch (MissingResourceException e) {
            myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + ENGLISH);
        }
        
    }
    
   
    /**
     * Method to display a text to the user in a display Box.
     * @param text Text to display
     */    
    public abstract void displayText(String text);
    
    /**
     * Sets the Controller for this View.
     * @param controller Controller that will watch over all View Actions
     *  / will create listeners
     */
    public void setController(Controller controller) {
        myController = controller;
    }
    /**
     * Sets the canvas for the view.  Will be called from the model or controller.
     * Called with command within controller such as myView.super.setCanvas(model)
     * @param model Model that should be displayed within the Canvas
     */
    public void setCanvas (Model model) {
        myCanvas = new Canvas(PREFERRED_CANVAS_SIZE, this, model);
    }
    
    /**
     * Method to send commands to the controller.
     * @param command String that needs to be parsed (may be multiple lines)
     */
    
    protected void sendCommand(String command) {
        myController.sendString(command);
    }
    
}
