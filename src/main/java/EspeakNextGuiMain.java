import java.awt.EventQueue;

import eu.riasol.espeak.controller.DefaultController;
import eu.riasol.espeak.model.Model;
import eu.riasol.espeak.view.EspeakNextGui;

public class EspeakNextGuiMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model model = new Model();
					DefaultController controller = new DefaultController();
					EspeakNextGui frame = new EspeakNextGui(controller);
					controller.addView(frame);
					controller.addView(frame.mainPanel);
					controller.addView(frame.processing);
					controller.addView(frame.config);
					controller.addModel(model);
					model.initDefaults();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
