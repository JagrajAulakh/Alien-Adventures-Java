package com.alienadventures.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
	public static String readFile(String path) throws IOException {
		String tot = "";
		Scanner file = new Scanner(new File(path));
		while (file.hasNextLine()) {
			String line = file.nextLine();
			if (!line.equals("")) tot += line + "\n";
		}
		return tot;
	}

	private static int toInt(String num) { return Integer.parseInt(num); }

	public static ArrayList<int[]> getCo(String path) throws IOException {
		ArrayList<int[]> co = new ArrayList<int[]>();
		Scanner file = new Scanner(new File(path));
		while (file.hasNextLine()) {
			String line = file.nextLine();
			if (!line.equals("")) {
				String[] parts = line.split(" ");
				co.add(new int[] {toInt(parts[0]), toInt(parts[1]), toInt(parts[2]), toInt(parts[3])});
			}
		}
		return co;
	}

}
