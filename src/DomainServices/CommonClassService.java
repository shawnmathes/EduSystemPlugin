package DomainServices;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Operation.CommonClassData;

public class CommonClassService {

	private static CommonClassData commonClassData = new CommonClassData();

	public static ArrayList<String> getList() {
		return commonClassData.getList();
	}

	public static void addClass(String className) throws WarningException {
		// Check if you select class or not
		if (className.length() == 0) {
			throw new WarningException("No Class Warning",
					"Please provide a class name");
		}

		// Check if name contains invalid value
		String regEx = "[`~!@#$%^&*()+=|{}':;'\\[\\],.<>/?~]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(className);
		if (m.find()) {
			throw new WarningException("Invalid Class name",
					"Please use only number and letter as name");
		}

		// Check if selected class already there
		ArrayList<String> currentClassList = getList();
		for (int i = 0; i < currentClassList.size(); i++) {
			if (!currentClassList.get(i).equals("")) {

				if ((currentClassList.get(i).equals(className))
						|| (currentClassList.get(i).toLowerCase()
								.equals(className))
						|| (currentClassList.get(i).toUpperCase()
								.equals(className))) {
					throw new WarningException("Repeat Class Warning",
							"This Class already exist");
				}
			}

		}

		currentClassList.add(className);
		commonClassData.update(currentClassList);
	}

	public static void deleteClass(String className) throws WarningException {
		if ((className == null) || (className.length() == 0)) {
			throw new WarningException("No Class Warning",
					"You have no class to delete");
		}

		// if such class has syllabus, delete it
		SyllabusService.delete(className);

		// Update info into current common class list
		ArrayList<String> currentList = commonClassData.getList();

		for (int k = 0; k < currentList.size(); k++) {
			if (className.equals(currentList.get(k))) {
				currentList.set(k, "");

			}
		}

		commonClassData.update(currentList);

		// Update info to student info file
		StudentClassService.dropClassForAllStudents(className);
	}
}
