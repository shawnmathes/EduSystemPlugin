package DomainServices;

import java.util.ArrayList;

import Operation.StudentClassData;

public class StudentClassService {

	private static StudentClassData studentClassData = new StudentClassData();

	public static ArrayList<String> getClassList() {
		return studentClassData.getClassList();
	}
	
	public static String[] getClassList(String studentID) {
		StudentClassData studentClassData = new StudentClassData();
		ArrayList<String> stuInfo = studentClassData.getClassList();
		for (int i = 0; i < stuInfo.size(); i++) {
			String[] temp = stuInfo.get(i).split(":");
			String id = temp[0];

			if (id.equals(studentID)) {
				return temp[1].split(",");
			}
		}
		
		return new String[0];
	}

	public static String[] viewRoster(String className) {
		ArrayList<String> student = getClassList();

		String studentID = "";
		for (int i = 0; i < student.size(); i++) {
			if (student.get(i).contains(className)) {
				if (!student.get(i).equals("")) {
					String[] temp = student.get(i).split(":");
					studentID = studentID + temp[0] + ",";
				}

			}
		}

		if (studentID.equals("")) {
			return new String[0];
		} else {
			return studentID.split(",");
		}
	}

	public static void addClass(String studentID, String className)
			throws WarningException {
		// Check if ID is in correct format
		if (studentID.length() == 0) {
			throw new WarningException("ID Warning", "Please input ID");
		}

		if (studentID.length() != 8) {
			throw new WarningException("ID Warning",
					"Student ID must be 8 digit");
		}

		if (className == null || className.length() == 0) {
			throw new WarningException("No Class Warning",
					"Must choose one class to add");
		}

		int checkFlag = 0;

		// Check if such student has choose more than 4 courses
		ArrayList<String> studentEnrollList = getClassList();

		if (studentEnrollList.size() > 0) {
			for (int i = 0; i < studentEnrollList.size(); i++) {
				if (!studentEnrollList.get(i).equals("")) {
					String[] tempArray = studentEnrollList.get(i).split(":");
					String id = tempArray[0];
					// student already select classes
					if (id.equals(studentID)) {
						checkFlag = 1;
						String[] array = studentEnrollList.get(i).split(":");
						String classesString = array[1];
						String[] classesArray = classesString.split(",");

						// class # > 4
						if (classesArray.length >= 4) {
							throw new WarningException("Enroll Warning", "Can't select more than 4 classes");
						}
						
						if (classesString.contains(className)){
							throw new WarningException("Enroll Warning", "Can't select same class more than once");
						} 
						
							classesString = classesString + "," + className;
							String updateString = array[0] + ":"
									+ classesString;
							studentEnrollList.set(i, updateString);
							studentClassData.update(studentEnrollList);
							return;
					}
				}
			}

			if (checkFlag == 0) {

				String statement = studentID + ":" + className;
				studentEnrollList.add(statement);
				studentClassData.update(studentEnrollList);
			}

		} else {
			String statement = studentID + ":" + className;
			studentEnrollList.add(statement);
			studentClassData.update(studentEnrollList);
		}
	}
	
	public static void dropClass(String studentID, String className) {
		StudentClassData studentClassData = new StudentClassData();
		ArrayList<String> stuInfo2 = studentClassData.getClassList();
		for (int j = 0; j < stuInfo2.size(); j++) {
			String[] temp = stuInfo2.get(j).split(":");
			String id = temp[0];

			if (id.equals(studentID)) {

				String newList = "";
				// Construct a new course list with the selected course
				// removed.
				String selectedCourses = temp[1];
				String[] splitList = selectedCourses.split(className);
				for (String s : splitList) {
					if (s.trim().endsWith(",")) {
						newList += s.substring(0, s.lastIndexOf(','));
					} else if (s.trim().startsWith(",")) {
						newList += s.substring(s.indexOf(',') + 1);
					} else {
						newList += s;
					}
				}
				
				if (newList.trim().length() == 0) {
					// If this student does not have any other courses,
					// remove the record
					stuInfo2.set(j, null);
				} else {
					// Update the course list of this student
					stuInfo2.set(j, id + ":" + newList);
				}
				
				break;
			}
		}

		// Update the record file.
		studentClassData.update(stuInfo2);
	}
	
	public static void dropClassForAllStudents(String className) {
		ArrayList<String> stuInfo2 = studentClassData.getClassList();
		for (int j = 0; j < stuInfo2.size(); j++) {
			String id = "";
			if (!stuInfo2.get(j).equals("")) {
				String[] temptemp = stuInfo2.get(j).split(":");
				id = temptemp[0];
				String[] myClassArray = temptemp[1].split(",");

				for (int u = 0; u < myClassArray.length; u++) {

					if (myClassArray[u].equals(className)) {
						myClassArray[u] = "delete";
						break;

					}

				}

				String update = "";
				for (int p = 0; p < myClassArray.length; p++) {
					if (!myClassArray[p].equals("delete")) {
						update = update + myClassArray[p] + ",";
					}
				}

				String[] elimilate = update.split(",");
				update = "";
				for (int n = 0; n < elimilate.length; n++) {
					if (!elimilate[n].equals("")) {
						if (n != elimilate.length - 1)
							update = update + elimilate[n] + ",";
						else if (n == elimilate.length - 1)
							update = update + elimilate[n];
					}
				}

				if (!update.equals("")) {
					update = id + ":" + update;
				}

				stuInfo2.set(j, update);
				studentClassData.update(stuInfo2);
			}
		}
	}
}
