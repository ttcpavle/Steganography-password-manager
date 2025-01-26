package exceptions;

public class ImageTooSmallException extends Exception {

	private static final long serialVersionUID = 830416696741931986L;
	
	public ImageTooSmallException(String message) {
		super(message);
	}

	public ImageTooSmallException() {
		super();
	}

	public ImageTooSmallException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ImageTooSmallException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageTooSmallException(Throwable cause) {
		super(cause);
	}
	
	
}
