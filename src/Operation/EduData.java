package Operation;

import java.io.File;
import java.util.ArrayList;

import edusystemplugin.extensions.IEduData;

public class EduData implements IEduData {
	
	private static String commonfilelist = Configuration.getDataRoot()
			+ "commonClassList";
	private static String studentfile = Configuration.getDataRoot()
			+ "studentManageFile.txt";

	/* (non-Javadoc)
	 * @see Operation.IEduData#getClassList()
	 */
	@Override
	public ArrayList<String> getClassList() {
		ReadFile readfile = new ReadFile();
		return readfile.read(commonfilelist);
	}

	/* (non-Javadoc)
	 * @see Operation.IEduData#updateClassList(java.util.ArrayList)
	 */
	@Override
	public void updateClassList(ArrayList<String> classList) {
		WriteFile writefile = new WriteFile();
		writefile.write(classList, commonfilelist);
	}

	/* (non-Javadoc)
	 * @see Operation.IEduData#updateStudentEnrollList(java.util.ArrayList)
	 */
	@Override
	public void updateStudentEnrollList(ArrayList<String> studentEnrollList) {
		WriteFile writeobj = new WriteFile();
		writeobj.write(studentEnrollList, studentfile);
	}

	/* (non-Javadoc)
	 * @see Operation.IEduData#getStudentEnrollList()
	 */
	@Override
	public ArrayList<String> getStudentEnrollList() {
		ReadFile readobj = new ReadFile();
		return readobj.read(studentfile);
	}
	
	/* (non-Javadoc)
	 * @see Operation.IEduData#getSyllabus(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see Operation.IEduData#deleteSyllabus(java.lang.String)
	 */
	@Override
	public void deleteSyllabus(String className) {
		File syllabus = new File(className);
		if (syllabus.exists()) {
			syllabus.delete();
		}
	}

	/* (non-Javadoc)
	 * @see Operation.IEduData#updateSyllabus(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateSyllabus(String className, String syllabus) {
		String filename = Configuration.getDataRoot() + className;
		WriteFile writefile = new WriteFile();
		writefile.writeString(syllabus, filename);
	}
}
