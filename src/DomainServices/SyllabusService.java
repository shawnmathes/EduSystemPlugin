package DomainServices;

import edusystemplugin.extensions.EduDataFactory;
import edusystemplugin.extensions.IEduData;

public class SyllabusService {
	
	private static IEduData eduData = EduDataFactory.Create();

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
