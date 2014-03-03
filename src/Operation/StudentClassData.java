package Operation;

import java.util.ArrayList;

public class StudentClassData {

	private String studentfile = Configuration.getDataRoot()
			+ "studentManageFile.txt";

	public void update(ArrayList<String> studentEnrollList) {
		WriteFile writeobj = new WriteFile();
		writeobj.write(studentEnrollList, studentfile);
	}

	public ArrayList<String> getClassList(String studentID) {
		return new ArrayList<String>();
	}

	public ArrayList<String> getClassList() {
		ReadFile readobj = new ReadFile();
		return readobj.read(studentfile);
	}

	public ArrayList<String> viewRoster(String className) {
		return new ArrayList<String>();
	}
}
