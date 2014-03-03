package Operation;

import java.io.File;
import java.util.ArrayList;

public class SyllabusData {

	public String get(String className) {
		String filename = Configuration.getDataRoot() + className;
		File fileSyllabus = new File(filename);
		if (fileSyllabus.exists()) {
			ReadFile readfile = new ReadFile();
			ArrayList<String> syllabusInfo = readfile.ReadFile(filename);

			String set = "";
			for (int i = 0; i < syllabusInfo.size(); i++) {
				set = set + syllabusInfo.get(i) + "\n";
			}

			return set;
		}
		else {
			return "";
		}
	}
	
	public void delete(String className) {
		File syllabus = new File(className);
	    if(syllabus.exists()){
	    	syllabus.delete();
	    }
	}

	public void save(String className, String syllabus) {
		String filename = Configuration.getDataRoot() + className;
		WriteFile writefile = new WriteFile();
		writefile.writeString(syllabus, filename);
	}
}
