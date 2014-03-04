package edusystemplugin.extensions;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class LoggingService {
	
	private static ArrayList<IEduLogging> loggers = create();

	public static void writeLog(String message) {
		for (IEduLogging logger : loggers) {
			logger.writeLog(message);
		}
	}

	private static ArrayList<IEduLogging> create() {
		ArrayList<IEduLogging> loggers = new ArrayList<IEduLogging>();
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edusystemplugin.logging");
		try {
			for (IConfigurationElement e : config) {
				System.out.println("Evaluating extension");
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEduLogging) {
					loggers.add((IEduLogging) o);
				}
			}
			
			return loggers;
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
