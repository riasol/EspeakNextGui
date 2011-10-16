package eu.riasol.espeak.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javaprolib.mvc.IView;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import eu.riasol.espeak.commands.ConvertCommand;
import eu.riasol.espeak.controller.DefaultController;
import eu.riasol.espeak.model.Model;

@SuppressWarnings("serial")
public class Processing extends JPanel implements IView {
	private DefaultController controller;
	private ConvertCommand command;

	public Processing(final DefaultController controller) {
		super();
		this.controller = controller;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 134,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 10,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -10,
				SpringLayout.EAST, this);
		add(progressBar);

		JButton btnBreak = new JButton("break");
		springLayout.putConstraint(SpringLayout.NORTH, btnBreak, 10,
				SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, btnBreak, 0,
				SpringLayout.EAST, progressBar);
		btnBreak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandThread.interrupt();
			}
		});
		add(btnBreak);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnBreak}));

	}

	private Thread commandThread;

	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(DefaultController.STATE)) {
			if (e.getNewValue().equals(Model.STATE.PROCESSING)) {

				commandThread = new Thread() {
					@Override
					public void run() {
						Model model = (Model) controller.getModel(Model.class);
						command = new ConvertCommand(
								model.getEspeakInstallDir(),
								model.getTextLanguage(), model.getInputText(),
								model.getLastOutputFileName());
						command.execute();
						controller.changeState(Model.STATE.MAIN);
					};

					@Override
					public void interrupt() {
						command.interrupt();
						controller.changeState(Model.STATE.MAIN);
					};

				};
				commandThread.start();
			}

		}

	}
}
