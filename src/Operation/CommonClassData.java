package Operation;

import java.util.ArrayList;


public class CommonClassData {

	private String commonfilelist = Configuration.getDataRoot()
			+ "commonClassList";
	
	public ArrayList<String> getList() {
		ReadFile readfile = new ReadFile();
		return readfile.ReadFile(commonfilelist);
	}
	
	public void update(ArrayList<String> classList) {
		WriteFile writefile = new WriteFile();
		writefile.write(classList, commonfilelist);
	}
}
