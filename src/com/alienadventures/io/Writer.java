package com.alienadventures.io;

import com.alienadventures.Resources;
import com.alienadventures.entity.Player;
import com.alienadventures.util.QuadTree;
import com.alienadventures.util.Rectangle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

class Person implements Serializable {
	private String name;

	public Person(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{" + name + "}";
	}
}

public class Writer {
	public static void writeBinaryFile(Object objects, String path) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (out != null) {
			try {
				out.writeObject(objects);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		HashMap<String, Object> objects = new HashMap<>();
		QuadTree t = new QuadTree();
		t.insert(new Rectangle(50, 50, 2, 2));
		objects.put("tree", t);
		writeBinaryFile(objects, "test.aadf");
		try {
			HashMap<String, Object> input = (HashMap<String, Object>)Reader.readBinaryFile("test.aadf");
			QuadTree tree = (QuadTree)input.get("tree");
			System.out.println(tree.query(new Rectangle(0, 0, 100, 100)));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
