package Operation;

import java.io.File;
import java.util.ArrayList;

public class EduData {
	
	private static String commonfilelist = Configuration.getDataRoot()
			+ "commonClassList";
	private static String studentfile = Configuration.getDataRoot()
			+ "studentManageFile.txt";

	public ArrayList<String> getClassList() {
		ReadFile readfile = new ReadFile();
		return readfile.read(commonfilelist);
	}

	public void updateClassList(ArrayList<String> classList) {
		WriteFile writefile = new WriteFile();
		writefile.write(classList, commonfilelist);
	}

	public void updateStudentEnrollList(ArrayList<String> studentEnrollList) {
		WriteFile writeobj = new WriteFile();
		writeobj.write(studentEnrollList, studentfile);
	}

	public ArrayList<String> getStudentEnrollList() {
		ReadFile readobj = new ReadFile();
		return readobj.read(studentfile);
	}
	
	public String getSyllabus(String className) {
		String filename = Configuration.getDataRoot() + className;
		File fileSyllabus = new File(filename);
		if (fileSyllabus.exists()) {
			ReadFile readfile = new ReadFile();
			ArrayList<String> syllabusInfo = readfile.read(filename);

			String set = "";
			for (int i = 0; i < syllabusInfo.size(); i++) {
				set = set + syllabusInfo.get(i) + "\n";
			}

			return set;
		} else {
			return "";
		}
	}

	public void deleteSyllabus(String className) {
		File syllabus = new File(className);
		if (syllabus.exists()) {
			syllabus.delete();
		}
	}

	public void updateSyllabus(String className, String syllabus) {
		String filename = Configuration.getDataRoot() + className;
		WriteFile writefile = new WriteFile();
		writefile.writeString(syllabus, filename);
	}
}
