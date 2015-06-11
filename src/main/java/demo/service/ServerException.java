package demo.service;

public class ServerException extends Exception{

	private Exception exception;
	private String additionalMessage = null;

	public ServerException(){
		
	}
	
	public ServerException(Exception ex){
		super();
		this.exception = ex;
		
	}
	
	public ServerException(String string, Exception ex) {
		this.additionalMessage = string;
	}

	public String getMessage() {
		if(null != additionalMessage)
			return additionalMessage;
		return exception.getMessage();	
	}
}
