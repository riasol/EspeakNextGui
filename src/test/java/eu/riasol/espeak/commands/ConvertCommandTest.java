package eu.riasol.espeak.commands;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ConvertCommandTest {

	@Test
	public void testExecute() {
		File output = null;
		try {
			output = File.createTempFile("sound", ".wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConvertCommand c = new ConvertCommand("c:/Program Files/eSpeak",
				"polish", "Chrząszcz przy kocie, bo bezrobocie, stara się dorobić.", output.getPath());
		c.execute();
		assertTrue("File not exists", output.isFile());
		assertTrue("File empty", output.length() > 0);
		try {
			System.out.print("wav file " + output.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.delete();
	}


}
