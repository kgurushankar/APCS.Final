package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * FileIO class from old APCS project
 * @author kgurushankar
 *@version 18.5.21
 */
public class FileIO {

	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String lineSeparator = System.getProperty("line.separator");

	public static ArrayList<String> readFile(String filename) {
		try (Scanner s = new Scanner(new FileReader(filename));) {
			ArrayList<String> output = new ArrayList<String>();
			while (s.hasNextLine()) {
				output.add(s.nextLine());
			}
			return output;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeFile(String filename, ArrayList<String> fileData) {
		try (FileWriter writer = new FileWriter(filename)) {
			for (String s : fileData) {
				writer.write(s);
				writer.write(lineSeparator);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}