package com.alienadventures.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Reader {

	public static Object readBinaryFile(String path) throws IOException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
		try {
			return in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
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

	public static Document readXMLFile(String path) {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Document map0 = readXMLFile("maps/map0.tmx");
		map0.getDocumentElement().normalize();
		System.out.println("Root element: " + map0.getDocumentElement().getNodeName());

		NodeList layers = map0.getElementsByTagName("layer");
		NodeList tilesets = map0.getElementsByTagName("tileset");
		System.out.println(tilesets);

		for (int layer = 0; layer < layers.getLength(); layer++) {
			Node n = layers.item(layer);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element)n;
				String dataString = ((Element)e.getElementsByTagName("data").item(0)).getTextContent();
				int width = toInt(e.getAttribute("width"));
				int height = toInt(e.getAttribute("height"));
				int[][] data = new int[height][width];
				String[] rowStrings = dataString.split("\n");
				for (int j = 0; j < rowStrings.length; j++) {
					if (rowStrings[j].equals("")) continue;
					String[] rowString = rowStrings[j].split(",");
					for (int i = 0; i < rowString.length; i++) {
						int d = toInt(rowString[i]);
//						data
					}
				}
			}
		}
	}

}
