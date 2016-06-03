package org.mei.core.module.handler;

/**
 * registered date 2015-01-10
 * programmed by Seok Kyun. Choi. 최석균
 * http://syaku.tistory.com
 */

public class SuccessHandler {

	private String message;
	private boolean error = false;
	private Object data;
	private String work;
	
	public SuccessHandler() {
		
	}
	
	public SuccessHandler(String message) {
		this.message = message;
	}
	
	public SuccessHandler(String message, boolean error) {
		this.message = message;
		this.error = error;
	}
	
	public SuccessHandler(String message, boolean error, Object data) {
		this.message = message;
		this.error = error;
		this.data = data;
	}
	
	public SuccessHandler(String message, boolean error, Object data, String work) {
		this.message = message;
		this.error = error;
		this.data = data;
		this.work = work;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	@Override
	public String toString() {
		return "SuccessHandler{" +
				"message='" + message + '\'' +
				", error=" + error +
				", data=" + data +
				", work='" + work + '\'' +
				'}';
	}
}
