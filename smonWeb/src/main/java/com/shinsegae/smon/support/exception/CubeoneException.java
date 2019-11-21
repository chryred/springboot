package com.shinsegae.smon.support.exception;

public class CubeoneException extends Exception {

	private static final long serialVersionUID = 1L;

	public CubeoneException(String msgCd, String msg) {
		super("[RET_CODE:" + msgCd + ", MSG:" + msg + "]");
	}

}
