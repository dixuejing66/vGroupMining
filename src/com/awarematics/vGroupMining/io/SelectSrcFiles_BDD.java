package com.awarematics.vGroupMining.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SelectSrcFiles_BDD {

	public static void main(String[] args) throws IOException {

		HashSet<File> result = new HashSet<File>();
		SelectSrcFiles_BDD tf = new SelectSrcFiles_BDD();
		String filePath = "D:\\datasets\\BDD\\bdd100k_info\\bdd100k\\info\\100k\\train";
		String desFolder = "D:\\PaperExperimentalData\\srcBDD(50000)";
		result = tf.getFiles(filePath);
		tf.writeJoson(result, desFolder);

	}

	public HashSet<File> getFiles(String filePath) {
		ArrayList<File> fileList = new ArrayList<File>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}

		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(".json")) {
				fileList.add(f);
			} else if (f.isDirectory()) {
				getFiles(f.getAbsolutePath());
			}
		}
		HashSet<File> result = new HashSet<File>();
		Random rand = new Random();

		int len = 50000;

		for (int i = 0; i < fileList.size(); i++) {
			int myRand = rand.nextInt(fileList.size());
			result.add(fileList.get(myRand));
			if (result.size() >= len) {
				break;
			}
		}
		return result;
	}

	public void writeJoson(HashSet<File> result, String desFolder) throws IOException {

		SelectSrcFiles_BDD tf = new SelectSrcFiles_BDD();

		for (File f : result) {
			String srcFileName = f.getName();
			File desFile = new File(desFolder, srcFileName);
			tf.copyFile(f, desFile);
		}
	}

	public void copyFile(File srcFile, File desFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(srcFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(desFile));
		String line;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		bw.close();
		br.close();
	}
}
