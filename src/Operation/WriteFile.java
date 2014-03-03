package Operation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	public void write(ArrayList<String> arraylist, String filename) {
		FileWriter fw = null;
		File file = new File(filename);

		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		Boolean isFirstLine = true;
		for (int i = 0; i < arraylist.size(); i++) {
			try {
				String item = arraylist.get(i);
				if ((item != null) && (item.length() > 0)) {
					if (isFirstLine) {
						isFirstLine = false;
					} else {
						bw.newLine();
					}
					bw.write(item);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeString(String text, String filename) {
		FileWriter fw = null;
		File file = new File(filename);

		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);

		try {
			bw.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void append(String statement, String filename) {
		File file = new File(filename);

		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(file.getName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		try {
			bufferWritter.write("\n" + statement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeWhenEmpty(String statement, String filename) {
		File file = new File(filename);

		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(file.getName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		try {
			bufferWritter.write("\n" + statement);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
