package edusystemplugin.extensions;

import java.util.ArrayList;

public interface IEduData {

	public abstract ArrayList<String> getClassList();

	public abstract void updateClassList(ArrayList<String> classList);

	public abstract void updateStudentEnrollList(
			ArrayList<String> studentEnrollList);

	public abstract ArrayList<String> getStudentEnrollList();

	public abstract String getSyllabus(String className);

	public abstract void deleteSyllabus(String className);

	public abstract void updateSyllabus(String className, String syllabus);

}