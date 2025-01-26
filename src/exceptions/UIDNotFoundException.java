package exceptions;

public class UIDNotFoundException extends Exception {

	private static final long serialVersionUID = 8067292855742485201L;

	public UIDNotFoundException() {
		super();
	}

	public UIDNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UIDNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UIDNotFoundException(String message) {
		super(message);
	}

	public UIDNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
