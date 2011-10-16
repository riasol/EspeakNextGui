package eu.riasol.espeak.view;

import java.beans.PropertyChangeEvent;

import javaprolib.mvc.IView;

import javax.swing.JLabel;
import javax.swing.JPanel;

import eu.riasol.espeak.controller.DefaultController;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements IView {
	public InfoPanel(DefaultController controller) {
		super();
		this.controller = controller;

		lblInfo = new JLabel("info");
		add(lblInfo);
	}
	private DefaultController controller;
	private JLabel lblInfo;
	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(DefaultController.STATE)) {

		}
	}
}
