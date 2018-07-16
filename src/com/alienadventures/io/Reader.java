package com.alienadventures.io;

import java.io.File;
import java.io.IOException;
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

	public static void main(String[] args) {
		String output;
		try {
			output = readFile("assets/data/story_cos/1_cos.txt");
			System.out.println(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
