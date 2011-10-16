package eu.riasol.espeak.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javaprolib.mvc.IView;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import eu.riasol.espeak.controller.DefaultController;
import eu.riasol.espeak.model.Model.STATE;

@SuppressWarnings("serial")
public class Config extends JPanel implements IView {
	private JTextField textFieldEspeakDir;
	private DefaultController controller;

	/**
	 * Create the panel.
	 *
	 * @param controller
	 */
	public Config(final DefaultController controller) {
		this.controller = controller;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblEspeakInstallDirectory = new JLabel(
				"espeak install directory");
		springLayout.putConstraint(SpringLayout.NORTH,
				lblEspeakInstallDirectory, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST,
				lblEspeakInstallDirectory, 10, SpringLayout.WEST, this);
		add(lblEspeakInstallDirectory);

		textFieldEspeakDir = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, lblEspeakInstallDirectory, 0, SpringLayout.SOUTH, textFieldEspeakDir);
		textFieldEspeakDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeEspeakInstallDir(textFieldEspeakDir.getText());
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, textFieldEspeakDir, 0,
				SpringLayout.NORTH, lblEspeakInstallDirectory);
		springLayout.putConstraint(SpringLayout.WEST, textFieldEspeakDir, 18,
				SpringLayout.EAST, lblEspeakInstallDirectory);
		add(textFieldEspeakDir);
		textFieldEspeakDir.setColumns(10);

		JButton btnSelectEspeakDir = new JButton("select");
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldEspeakDir, 0, SpringLayout.SOUTH, btnSelectEspeakDir);
		btnSelectEspeakDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(textFieldEspeakDir.getText());
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fc.showDialog(Config.this, "select direcory");
				if (result == JFileChooser.APPROVE_OPTION) {
					controller.changeEspeakInstallDir(fc.getSelectedFile()
							.getPath());
				}
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, textFieldEspeakDir, -6,
				SpringLayout.WEST, btnSelectEspeakDir);
		springLayout.putConstraint(SpringLayout.NORTH, btnSelectEspeakDir, 0,
				SpringLayout.NORTH, lblEspeakInstallDirectory);
		springLayout.putConstraint(SpringLayout.EAST, btnSelectEspeakDir, -10,
				SpringLayout.EAST, this);
		add(btnSelectEspeakDir);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeEspeakInstallDir(textFieldEspeakDir.getText());
				controller.changeState(STATE.MAIN);

			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -10,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -10,
				SpringLayout.EAST, this);
		add(btnOk);

		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeState(STATE.MAIN);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 0,
				SpringLayout.NORTH, btnOk);
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, -6,
				SpringLayout.WEST, btnOk);
		add(btnCancel);

	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(DefaultController.ESPEAK_INSTALL_DIR)) {
			textFieldEspeakDir.setText((String) e.getNewValue());
		}

	}
}
