package DomainServices;

import Operation.SyllabusData;

public class SyllabusService {
	
	private static SyllabusData syllabusData = new SyllabusData();

	public static String get(String className) {
		return syllabusData.get(className);
	}

	public static void delete(String className) {
		syllabusData.delete(className);
	}

	public static void save(String className, String syllabus) {
		syllabusData.save(className, syllabus);
	}
	
}
