package blackboard.sonar.plugins.har.exception;

public class HarPluginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HarPluginException(String message, Throwable cause) {
		super(message, cause);
	}

	public HarPluginException(String message) {
		super(message);
	}

	public HarPluginException(Throwable cause) {
		super(cause);
	}

}
