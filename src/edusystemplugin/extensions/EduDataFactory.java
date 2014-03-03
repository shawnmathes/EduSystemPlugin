package edusystemplugin.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class EduDataFactory {

	public static IEduData Create() {
		System.out.println("Evaluating extension");
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edusystemplugin.data");
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEduData) {
					return (IEduData) o;
				}
			}
			
			System.out.println("Couldn't find data extension.");
			return null;
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
