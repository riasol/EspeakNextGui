package eu.riasol.espeak.model;

import java.util.ArrayList;

import javaprolib.PropertiesWrapper;
import javaprolib.mvc.AbstractModel;
import eu.riasol.espeak.controller.DefaultController;

public class Model extends AbstractModel{

	public enum STATE{MAIN,CONFIG,PROCESSING,ERROR}
	private String espeakInstallDir;
	private String lastOutputFileName;
	private String inputText;
	private STATE state;
	private String textLanguage;
	private ArrayList<String> textLanguages;
	private PropertiesWrapper props;
	public STATE getState() {
		return state;
	}
	public void setState(STATE state) {
		STATE o=this.state;
		this.state = state;
		firePropertyChange(DefaultController.STATE, o, state);
	}
	public String getEspeakInstallDir() {
		return espeakInstallDir;
	}
	public void setEspeakInstallDir(String espeakInstallDir) {
		String o=this.espeakInstallDir;
		this.espeakInstallDir = espeakInstallDir;
		props.save(DefaultController.ESPEAK_INSTALL_DIR,espeakInstallDir);
		firePropertyChange(DefaultController.ESPEAK_INSTALL_DIR, o, espeakInstallDir);
	}
	public String getLastOutputFileName() {
		return lastOutputFileName;
	}
	public void setLastOutputFileName(String lastOutputFileName) {
		String o=this.lastOutputFileName;
		this.lastOutputFileName = lastOutputFileName;
		props.save(DefaultController.LAST_OUTPUT_FILE_NAME,lastOutputFileName);
		firePropertyChange(DefaultController.LAST_OUTPUT_FILE_NAME, o, lastOutputFileName);
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		String o=this.inputText;
		this.inputText = inputText;
		firePropertyChange(DefaultController.INPUT_TEXT, o, inputText);
		}
	public String getTextLanguage() {
		return textLanguage;
	}
	public void setTextLanguage(String textLanguage) {
		String o=this.textLanguage;
		this.textLanguage = textLanguage;
		props.save(DefaultController.TEXT_LANGUAGE,textLanguage);
		firePropertyChange(DefaultController.TEXT_LANGUAGE, o, textLanguage);
	}
	public ArrayList<String> getTextLanguages() {
		return textLanguages;
	}
	public void setTextLanguages(ArrayList<String> textLanguages) {
		//TODO collection is mutable
		ArrayList<String> o=this.textLanguages;
		this.textLanguages = textLanguages;
		firePropertyChange(DefaultController.TEXT_LANGUAGES, o, textLanguages);
	}

	public Model() {
	}
	@Override
	public void initDefaults() {
		props=PropertiesWrapper.get("EspeakNextGuiConfig");
		props.load();
		setState(STATE.MAIN);
		ArrayList<String> al=new ArrayList<String>();
		al.add("polish");
		al.add("english");
		setTextLanguages(al);
		setTextLanguage(props.read(DefaultController.TEXT_LANGUAGE,"polish"));
		setEspeakInstallDir(props.read(DefaultController.ESPEAK_INSTALL_DIR,""));
		setLastOutputFileName(props.read(DefaultController.LAST_OUTPUT_FILE_NAME,""));

	}

}
