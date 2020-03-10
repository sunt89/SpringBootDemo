package com.opfly.demo.result;

public class ResultMsg {
	private int errcode;
	private String errmsg;
	private Object data;
	
	public ResultMsg(int errcode, String errmsg, Object data)
	{
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.data = data;
	}
	
	public static ResultMsg createResultMsg(int errcode, String errmsg, Object data) {
		return new ResultMsg(errcode, errmsg, data);
	}

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
