package eu.riasol.espeak.controller;

import javaprolib.mvc.AbstractController;
import eu.riasol.espeak.model.Model;

public class DefaultController extends AbstractController{
	public static final String ESPEAK_INSTALL_DIR = "espeakInstallDir";
	public static final String LAST_OUTPUT_FILE_NAME = "lastOutputFileName";
	public static final String INPUT_TEXT = "inputText";
	public static final String STATE = "state";
	public static final String TEXT_LANGUAGE = "textLanguage";
	 public static final String TEXT_LANGUAGES = "textLanguages";
	 public void changeLastOutputFileName(String v) {
		 setModelProperty(LAST_OUTPUT_FILE_NAME, v);
	 }
	 public void changeInputText(String v) {
		 setModelProperty(INPUT_TEXT, v);
	 }
	 public void changeState(Model.STATE v) {
		 setModelProperty(STATE, v);
	 }
	 public void changeEspeakInstallDir(String v) {
		 setModelProperty(ESPEAK_INSTALL_DIR, v);
	 }
}
