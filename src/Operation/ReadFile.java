package Operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

	ArrayList<String> StudentEnrollList = new ArrayList<String>();

	public ArrayList<String> ReadFile(String filename) {

		File file = new File(filename);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String line;

		try {
			while (((line = br.readLine()) != null)) {
				line = line.trim();
				if (line.length() > 0) {
					StudentEnrollList.add(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return StudentEnrollList;

	}

}
