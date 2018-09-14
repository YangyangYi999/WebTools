package com.me.share.exception;

public class ContentException extends Exception{

	
	 public ContentException(String message)
	    {
	        super("ContentException-"+message);
	    }
	    
	    public ContentException(String message, Throwable cause)
	    {
	        super("ContentException-"+message,cause);
	    }

}
