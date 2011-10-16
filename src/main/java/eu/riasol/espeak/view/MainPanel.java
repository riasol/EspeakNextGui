package eu.riasol.espeak.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javaprolib.mvc.IView;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import eu.riasol.espeak.commands.OpenFileCommand;
import eu.riasol.espeak.controller.DefaultController;
import eu.riasol.espeak.model.Model;
import eu.riasol.espeak.model.Model.STATE;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements IView {
	private JTextField output;
	private DefaultController controller;
	private JComboBox<String> languages;
	private JButton btnOpen;
	private JLabel lblTextStat;
	/**
	 * Create the panel.
	 */
	public MainPanel(final DefaultController controller) {
		this.controller = controller;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		final JTextArea textArea = new JTextArea() {
			private Image image;
			@Override
			protected void paintComponent(Graphics g) {
				if (image == null) {
					try {
						URL url = EspeakNextGui.class.getResource("/assets/word.png");
						image = ImageIO.read(url);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int cols = (int) Math.ceil(getWidth() / image.getWidth(null)) + 1;
				int rows = (int) Math.ceil(getHeight() / image.getHeight(null)) + 1;
				for (int i = 0; i < cols; i++) {
					for (int j = 0; j < rows; j++) {
						g.drawImage(image, image.getWidth(null) * i, image.getHeight(null) * j, image.getWidth(null), image.getHeight(null), null);
					}
				}
				super.paintComponent(g);
			}
		};
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateText();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateText();
			}
			private void updateText() {
				String t=textArea.getText();
				lblTextStat.setVisible(true);
				lblTextStat.setText(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.lblTextStat.text")+t.split("\\s+").length );
				controller.changeInputText(textArea.getText());
			}
		});

		lblTextStat = new JLabel(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.lblTextStat.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblTextStat.setVisible(false);
		lblTextStat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTextStat.setForeground(UIManager.getColor("Button.darkShadow"));
		add(lblTextStat);
		JScrollPane scrollPane = new JScrollPane(textArea);
		springLayout.putConstraint(SpringLayout.NORTH, lblTextStat, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, lblTextStat, 0, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -75, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);
		languages = new JComboBox();
		languages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Model) controller.getModel(Model.class)).setTextLanguage((String)languages.getSelectedItem());
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, languages, 200, SpringLayout.WEST, scrollPane);
		languages.setMinimumSize(new Dimension(180, 18));
		springLayout.putConstraint(SpringLayout.NORTH, languages, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, languages, 10, SpringLayout.WEST, this);
		add(languages);
		JButton btnConvert = new JButton(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnConvert.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnConvert.setToolTipText(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnConvert.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeState(STATE.PROCESSING);// TODO validation
			}
		});
		output = new JTextField();
		output.setToolTipText(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.output.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
		springLayout.putConstraint(SpringLayout.NORTH, btnConvert, 0, SpringLayout.NORTH, output);
		output.setSize(new Dimension(0, 23));
		springLayout.putConstraint(SpringLayout.NORTH, output, 6, SpringLayout.SOUTH, languages);
		springLayout.putConstraint(SpringLayout.WEST, output, 10, SpringLayout.WEST, this);
		output.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				controller.changeLastOutputFileName(output.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				controller.changeLastOutputFileName(output.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				controller.changeLastOutputFileName(output.getText());
			}
		});
		add(output);
		output.setColumns(10);
		JButton btnSelect = new JButton(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnSelect.text")); //$NON-NLS-1$ //$NON-NLS-2$
		springLayout.putConstraint(SpringLayout.NORTH, btnSelect, 0, SpringLayout.NORTH, output);
		btnSelect.setToolTipText(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnSelect.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
		springLayout.putConstraint(SpringLayout.EAST, output, -6, SpringLayout.WEST, btnSelect);
		btnSelect.setSize(new Dimension(100, 0));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lo = ((Model) controller.getModel(Model.class)).getLastOutputFileName();
				JFileChooser fc = null;
				if (lo.equals("")) {
					fc = new JFileChooser();
				} else {
					fc = new JFileChooser(new File(lo).getParent());
				}
				int ret = fc.showSaveDialog(MainPanel.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					boolean work = true;
					if (f.isFile()) {
						Object[] options = { "Cancel", "Yes, overwrite" };
						int dlgRes = JOptionPane.showOptionDialog(MainPanel.this, "Overwrite that file?", "File exist", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, null, options, options[0]);
						work = dlgRes == 1;
					}
					if (work) {
						controller.changeLastOutputFileName(f.getPath());
					}
				}
			}
		});
		add(btnSelect);
		btnOpen = new JButton(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnOpen.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnOpen.setToolTipText(ResourceBundle.getBundle("eu.riasol.espeak.view.messages").getString("MainPanel.btnOpen.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = new File(((Model) controller.getModel(Model.class)).getLastOutputFileName());
				new OpenFileCommand(f).execute();
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, btnOpen, -70, SpringLayout.WEST, btnConvert);
		springLayout.putConstraint(SpringLayout.EAST, btnSelect, -10, SpringLayout.WEST, btnOpen);
		springLayout.putConstraint(SpringLayout.NORTH, btnOpen, 0, SpringLayout.NORTH, output);
		add(btnOpen);
		springLayout.putConstraint(SpringLayout.EAST, btnConvert, 0, SpringLayout.EAST, scrollPane);
		add(btnConvert);
	}
	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(DefaultController.TEXT_LANGUAGES)) {
			@SuppressWarnings("unchecked")
			ArrayList<String> data = (ArrayList<String>) e.getNewValue();
			languages.setModel(new DefaultComboBoxModel(data.toArray()));
		} else if (e.getPropertyName().equals(DefaultController.TEXT_LANGUAGE)) {
			String tL = (String) e.getNewValue();
			languages.getModel().setSelectedItem(tL);
		} else if (e.getPropertyName().equals(DefaultController.LAST_OUTPUT_FILE_NAME)) {
			String t = (String) e.getNewValue();
			if (!t.equals(output.getText())) {
				output.setText(t);
			}
			output.setToolTipText(t);
			btnOpen.setVisible(new File(((Model) controller.getModel(Model.class)).getLastOutputFileName()).isFile());
			btnOpen.setVisible(false);//TODO
		} else if (e.getPropertyName().equals(DefaultController.STATE) && e.getNewValue().equals(Model.STATE.MAIN)) {
			String lo = ((Model) controller.getModel(Model.class)).getLastOutputFileName();
			if (lo != null) {
				btnOpen.setVisible(new File(lo).isFile());
			}
		}
	}
}
