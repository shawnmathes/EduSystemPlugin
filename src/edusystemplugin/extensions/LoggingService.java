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

	public static void writeDebug(String message) {
		for (IEduLogging logger : loggers) {
			logger.writeDebug(message);
		}
	}

	public static void writeInfo(String message) {
		for (IEduLogging logger : loggers) {
			logger.writeInfo(message);
		}
	}

	public static void writeError(String message) {
		for (IEduLogging logger : loggers) {
			logger.writeError(message);
		}
	}

	public static void writeAudit(String message) {
		for (IEduLogging logger : loggers) {
			logger.writeAudit(message);
		}
	}

	private static ArrayList<IEduLogging> create() {
		ArrayList<IEduLogging> loggers = new ArrayList<IEduLogging>();
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edusystemplugin.logging");
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEduLogging) {
					loggers.add((IEduLogging) o);
				}
			}
			
			return loggers;
		} catch (CoreException ex) {
			return null;
		}
	}
}
