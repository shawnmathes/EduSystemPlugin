package DomainServices;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edusystemplugin.extensions.EduDataFactory;
import edusystemplugin.extensions.IEduData;
import edusystemplugin.extensions.LoggingService;

public class CommonClassService {

	private static IEduData eduData = EduDataFactory.Create();

	public static ArrayList<String> getList() {
		return eduData.getClassList();
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
		eduData.updateClassList(currentClassList);
		LoggingService.writeAudit("Added class " + className);
	}

	public static void deleteClass(String className) throws WarningException {
		if ((className == null) || (className.length() == 0)) {
			throw new WarningException("No Class Warning",
					"You have no class to delete");
		}

		// if such class has syllabus, delete it
		SyllabusService.delete(className);

		// Update info into current common class list
		ArrayList<String> currentList = eduData.getClassList();

		for (int k = 0; k < currentList.size(); k++) {
			if (className.equals(currentList.get(k))) {
				currentList.set(k, "");

			}
		}

		eduData.updateClassList(currentList);
		LoggingService.writeAudit("Deleted class " + className);

		// Update info to student info file
		StudentClassService.dropClassForAllStudents(className);
	}
}
