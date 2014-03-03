package DomainServices;

import Operation.EduData;

public class SyllabusService {
	
	private static EduData eduData = new EduData();

	public static String get(String className) {
		return eduData.getSyllabus(className);
	}

	public static void delete(String className) {
		eduData.deleteSyllabus(className);
	}

	public static void save(String className, String syllabus) {
		eduData.updateSyllabus(className, syllabus);
	}
	
}
