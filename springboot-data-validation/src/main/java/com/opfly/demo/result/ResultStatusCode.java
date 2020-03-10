package com.opfly.demo.result;

public enum ResultStatusCode {

	OK(0, "ok"),
	SYSTEM_ERR(30001, "system error"),
	PARAMETER_ERR(30002, "parameter error");
	
	private int errcode;
	private String errmsg;
	public int getErrcode() {
		return errcode;
	}
 
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
 
	public String getErrmsg() {
		return errmsg;
	}
 
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	private ResultStatusCode(int Errode, String ErrMsg)
	{
		this.errcode = Errode;
		this.errmsg = ErrMsg;
	}
}
