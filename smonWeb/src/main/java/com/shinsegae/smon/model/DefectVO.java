 package com.shinsegae.smon.model;


public class DefectVO {
	
	String effect		;	// 업무 영향
	String effectRange	;	// 장애 범위
	String targetSystem	;	// 대상 시스템
	String effectTime	;	// 장애 시간
	String myLocalDate1 ;	// 장애 발생 시간
	String myLocalDate2 ;	// 현재 시간
	String selBox		;	
	
	public DefectVO() {

	}
	
	public DefectVO(String effect, String effectRange, String targetSystem,
			String effectTime, String myLocalDate1, String myLocalDate2, String selBox) {
		super();
		this.effect 		= effect;
		this.effectRange 	= effectRange;
		this.targetSystem 	= targetSystem;
		this.effectTime 	= effectTime;
		this.myLocalDate1 	= myLocalDate1;
		this.myLocalDate2 	= myLocalDate2;
		this.selBox 		= selBox;
	}

	public String getMyLocalDate1() {
		return myLocalDate1;
	}

	public void setMyLocalDate1(String myLocalDate1) {
		this.myLocalDate1 = myLocalDate1;
	}

	public String getMyLocalDate2() {
		return myLocalDate2;
	}

	public void setMyLocalDate2(String myLocalDate2) {
		this.myLocalDate2 = myLocalDate2;
	}
	
	public String getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getEffectRange() {
		return effectRange;
	}
	public void setEffectRange(String effectRange) {
		this.effectRange = effectRange;
	}
	public String getTargetSystem() {
		return targetSystem;
	}
	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}
	
	public String getSelBox() {
		return selBox;
	}

	public void setSelBox(String selBox) {
		this.selBox = selBox;
	}
	

	@Override
	public String toString() {
		return "DefectVO [effect=" + effect + ", effectRange=" + effectRange
				+ ", targetSystem=" + targetSystem + ", effectTime="
				+ effectTime + ", myLocalDate1=" + myLocalDate1
				+ ", myLocalDate2=" + myLocalDate2 + ", selBox=" + selBox + "]";
	}
	
}
