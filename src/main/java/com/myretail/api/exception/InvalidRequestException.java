package com.myretail.api.exception;

public class InvalidRequestException extends RuntimeException
{

	private static final long serialVersionUID = -4687830083842182067L;

	public InvalidRequestException()
    {
        super();
    }

    public InvalidRequestException(final String message)
    {
        super(message);
    }

    public InvalidRequestException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public InvalidRequestException(final Throwable cause)
    {
        super(cause);

    }
    
}
