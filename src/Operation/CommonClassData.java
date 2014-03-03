package Operation;

import java.util.ArrayList;


public class CommonClassData {

	private String commonfilelist = Configuration.getDataRoot()
			+ "commonClassList";

	public void addClass(String className) {

	}

	public void deleteClass(String className) {

	}
	
	public ArrayList<String> getList() {
		ReadFile readfile = new ReadFile();
		return readfile.ReadFile(commonfilelist);
	}

	public String[] getListAsArray() {
		ArrayList<String> commonfilelistArray = getList();
		return commonfilelistArray.toArray(new String[commonfilelistArray.size()]);
	}
	
	public void update(ArrayList<String> classList) {
		WriteFile writefile = new WriteFile();
		writefile.write(classList, commonfilelist);
	}
}
