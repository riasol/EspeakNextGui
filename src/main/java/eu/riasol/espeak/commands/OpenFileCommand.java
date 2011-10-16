package eu.riasol.espeak.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenFileCommand {
	private File file;

	public OpenFileCommand(File file) {
		super();
		this.file = file;
	}

	public void execute() {
		Desktop desktop = Desktop.getDesktop();
		try {
			File f=new File(file.getCanonicalPath());
			desktop.open(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
