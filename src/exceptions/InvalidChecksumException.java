package exceptions;

public class InvalidChecksumException extends Exception{

	private static final long serialVersionUID = -2437507842482456673L;

	public InvalidChecksumException(String message) {
		super(message);
	}

	public InvalidChecksumException() {
		super();
	}

	public InvalidChecksumException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidChecksumException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidChecksumException(Throwable cause) {
		super(cause);
	}
}
