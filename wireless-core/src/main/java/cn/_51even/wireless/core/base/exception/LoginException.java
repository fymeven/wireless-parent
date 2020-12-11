package cn._51even.wireless.core.base.exception;

public class LoginException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	private int errorCode;

	public LoginException(String errorMessage)
	{
		super(errorMessage);
	}

	public LoginException(int errorCode, String errorMessage)
	{
		super(errorMessage);
		this.setErrorCode(errorCode);
	}

	public LoginException(int errorCode, String errorMessage, Throwable e) {
		super(errorMessage, e);
		this.setErrorCode(errorCode);
	}


	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}



}
