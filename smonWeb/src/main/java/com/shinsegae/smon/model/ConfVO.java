 package com.shinsegae.smon.model;

import oracle.sql.DATE;


public class ConfVO {
	
	private String	SEQ;
	private String	YMD;
	private String	SBJ;
	private String 	LOGINID;
	private String	STDID;
	private String 	REGID;
	private String	ATTNDYN;
	private String	HALFYEAR;
	
	

	public String getHALFYEAR() {
		return HALFYEAR;
	}


	public void setHALFYEAR(String hALFYEAR) {
		HALFYEAR = hALFYEAR;
	}

	public String getATTNDYN() {
		return ATTNDYN;
	}


	public ConfVO(String sEQ, String yMD, String sBJ, String lOGINID,
			String sTDID, String rEGID, String aTTNDYN, String hALFYEAR) {
		super();
		SEQ = sEQ;
		YMD = yMD;
		SBJ = sBJ;
		LOGINID = lOGINID;
		STDID = sTDID;
		REGID = rEGID;
		ATTNDYN = aTTNDYN;
		HALFYEAR = hALFYEAR;
	}


	public void setATTNDYN(String aTTNDYN) {
		ATTNDYN = aTTNDYN;
	}


	public String getREGID() {
		return REGID;
	}


	public void setREGID(String rEGID) {
		REGID = rEGID;
	}


	public String getYMD() {
		return YMD;
	}


	public void setYMD(String yMD) {
		YMD = yMD;
	}


	public String getSBJ() {
		return SBJ;
	}


	public void setSBJ(String sBJ) {
		SBJ = sBJ;
	}


	public String getLOGINID() {
		return LOGINID;
	}


	public void setLOGINID(String lOGINID) {
		LOGINID = lOGINID;
	}


	@Override
	public String toString() {
		return "ConfVO [SEQ=" + SEQ + ", YMD=" + YMD + ", SBJ=" + SBJ
				+ ", LOGINID=" + LOGINID + ", STDID=" + STDID + ", REGID="
				+ REGID + ", ATTNDYN=" + ATTNDYN + ", HALFYEAR=" + HALFYEAR
				+ "]";
	}


	public String getSEQ() {
		return SEQ;
	}


	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}


	public String getSTDID() {
		return STDID;
	}


	public void setSTDID(String sTDID) {
		STDID = sTDID;
	}


	public ConfVO() {

	}
	
}
