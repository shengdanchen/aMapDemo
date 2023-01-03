package com.example.commonlibrary.netty;



public class BaseJson<T> {

	private boolean success;
	private String msg;
	private int code = 0;
	private T data;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
		if(success == true){
			this.code = 0;
		}
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
		//如果插入数据，默认就成功
		this.code = 0;
		this.success = true;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	

}
