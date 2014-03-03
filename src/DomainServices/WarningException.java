package DomainServices;

@SuppressWarnings("serial")
public class WarningException extends Exception {

	private String title;

	public String getTitle() {
		return title;
	}
	
	public WarningException(String title, String message) {
		super(message);
		this.title = title;
	}
}
