package br.com.mbci.dao.exceptions;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataException() {
	}

	public DataException(String s) {
		super(s);
	}

	public DataException(Throwable throwable) {
		super(throwable);
	}

}
