package com.citsamex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File dir = new File("D:\\1");
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader(files[i]));
			while (br.ready()) {
				String x = br.readLine();
				if (x.toLowerCase().indexOf("corning") != -1) {
					System.out.println(files[i].getAbsolutePath());
				}
			}
			br.close();
			
			
			
		}

	}
}
