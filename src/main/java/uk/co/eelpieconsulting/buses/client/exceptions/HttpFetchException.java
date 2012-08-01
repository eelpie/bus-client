package uk.co.eelpieconsulting.buses.client.exceptions;

public class HttpFetchException extends CountdownException {
	
	private static final long serialVersionUID = 1L;
	
	private final Exception cause;

	public HttpFetchException(Exception cause) {
		super();
		this.cause = cause;
	}

	public Exception getCause() {
		return cause;
	}
	
}
