package com.shinsegae.smon.model;


public class DefectMessageVO {
	
	String defectGrade		;
	String defectMessage	;
	
	
	
	public DefectMessageVO() {
		
	}
	
	public DefectMessageVO(String defectGrade, String defectMessage) {
		super();
		this.defectGrade = defectGrade;
		this.defectMessage = defectMessage;
	}


	public String getDefectGrade() {
		return defectGrade;
	}
	public void setDefectGrade(String defectGrade) {
		this.defectGrade = defectGrade;
	}
	public String getDefectMessage() {
		return defectMessage;
	}
	public void setDefectMessage(String defectMessage) {
		this.defectMessage = defectMessage;
	}
	
	@Override
	public String toString() {
		return "DefectMessageVO [defectGrade=" + defectGrade
				+ ", defectMessage=" + defectMessage + "]";
	}
	
	

	
}
