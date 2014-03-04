package edusystemplugin.extensions;

public interface IEduLogging {

	public abstract void writeLog(String message);
	public abstract void writeDebug(String message);
	public abstract void writeInfo(String message);
	public abstract void writeError(String message);
	public abstract void writeAudit(String message);
}
