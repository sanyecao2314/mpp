package com.citsamex.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class Test2 {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		System.out.println("loading...");

		FileInputStream fis = new FileInputStream("D:\\1.txt");
		BufferedReader dr = new BufferedReader(new InputStreamReader(fis));

		// check the date
		Date d1 = new Date();
		System.out.println(d1);

		String line = null;
		while ((line = dr.readLine()) != null) {
			System.out.println(line);

		}

		Date d2 = new Date();
		System.out.println(d2);
		System.out.println((d2.getTime() - d1.getTime()) / 1000);

	}
}
