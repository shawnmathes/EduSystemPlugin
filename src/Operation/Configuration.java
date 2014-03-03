package Operation;

import edusystemplugin.Activator;

public class Configuration {

	public static String getDataRoot() {
		return Activator.getDefault().getStateLocation().toString() + "\\";
	}

}
