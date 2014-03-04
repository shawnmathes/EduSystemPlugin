package edusystemplugin.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class EduDataFactory {

	public static IEduData Create() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edusystemplugin.data");
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEduData) {
					return (IEduData) o;
				}
			}
		} catch (CoreException ex) {

		}
		
		return null;
	}
}
