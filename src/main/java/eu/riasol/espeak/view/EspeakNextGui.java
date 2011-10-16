package eu.riasol.espeak.view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javaprolib.mvc.IView;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import eu.riasol.espeak.controller.DefaultController;
import eu.riasol.espeak.model.Model;
import eu.riasol.espeak.model.Model.STATE;


@SuppressWarnings("serial")
public class EspeakNextGui extends JFrame implements IView{
public static  EspeakNextGui instance;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	private CardLayout cartLayout=new CardLayout(5, 0);
	public Config config;
	public MainPanel mainPanel;
	public Processing processing;
	private DefaultController controller;
	public EspeakNextGui(final DefaultController controller) {
		this.controller=controller;
		instance=this;
		 UIManager.put("swing.boldMetal", Boolean.FALSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 386);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmConfig = new JMenuItem("config");
		mntmConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeState(STATE.CONFIG);
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cartLayout);
		mainPanel = new MainPanel(controller);
		contentPane.add(mainPanel,"mainPanel");
		config = new Config(controller);
		contentPane.add(config,"config");
		processing = new Processing(controller);
		contentPane.add(processing,"processing");
		menuBar.add(mntmConfig);
	}
	public void setCard(String name) {
		cartLayout.show( contentPane, name);
	}
	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(DefaultController.STATE)) {
			Model.STATE state=(Model.STATE)e.getNewValue();
			switch (state){
			case CONFIG:
				setCard("config");
				break;
			case PROCESSING:
				setCard("processing");
				break;
			case MAIN:
				setCard("mainPanel");
				break;
			}
		}
	}

}
