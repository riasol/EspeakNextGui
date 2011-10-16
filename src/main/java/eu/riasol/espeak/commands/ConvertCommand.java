package eu.riasol.espeak.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConvertCommand {
	public ConvertCommand(String espeakDir, String language, String inputText,
			String outputFilePath) {
		super();
		this.espeakDir = espeakDir;
		this.language = language;
		this.inputText = inputText;
		this.outputFilePath = outputFilePath;
	}

	private String espeakDir;
	private String language;
	private String inputText;
	private String outputFilePath;
	private Process process;

	public void execute() {
		String inputTextFileName=null;
		File inputTextFile=null;
		try {
			inputTextFile= File.createTempFile("input", ".txt");
			inputTextFileName = inputTextFile.getPath();
			FileWriter fw = new FileWriter(inputTextFileName);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(inputText);
			bw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<String> cmd = new ArrayList<String>();
		// espeak -v polish -b 1 -f in.txt -w out.wav
		cmd.add(espeakDir + "/command_line/espeak.exe");
		cmd.add("-v");
		cmd.add(language);
		cmd.add("-b 1");// utf8
		cmd.add("-f");
		cmd.add(inputTextFileName);
		cmd.add("-w");
		cmd.add(outputFilePath);
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);
		try {
			process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer lines = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				lines.append(line);
				lines.append("\n");
			}
			System.out.print(lines);
			inputTextFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void interrupt() {
		process.destroy();
	}

}
