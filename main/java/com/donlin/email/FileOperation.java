package com.donlin.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileOperation {

	public String filePath;
	//init the file factory with a file path that given by the system
	public FileOperation(String path) {
		filePath = path;
	}

	//store each element in a string array to many files
	public void saveFile(String[] info) {
		for (int i = 0; i < info.length; i++) {
			String path = filePath + "/" + i + ".txt";
			File file = new File(path);
			try {
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				bw.write(info[i]);
				bw.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	//read the information that has already been stored in the files
	public String[] readFile() {
		String[] result = new String[6];
		try {
			for (int i = 0; i < 6; i++) {
				String path = filePath + "/" + i + ".txt";
				File file = new File(path);
				try {
					String str = "";
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					for (String currentLine = br.readLine(); currentLine != null; currentLine = br.readLine())
						str += currentLine;
					br.close();
					result[i] = str;
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
			return result;
		} catch (Exception e) {
			return result;
		}
	}
}
