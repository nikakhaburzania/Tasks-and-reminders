import model.Model;
import view.View;
import controller.Controller;

/**
 * A class to create the model, the view, and the controller. It calls a method in the view to instantiate the UI.
 * @author Nika khaburzania
 * @author Jaysen Munsami
 *
 */
public class Driver {

	
	public static void main(String[] args) {
		// Model and view objects created
		Model model = new Model();
		View view = new View(model);
		
    	Controller controller = new Controller(view,model);
    	
    	view.showGUI(view);
    	
    }
	
	
}
