package com.shinsegae.smon.model.tool;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EncDecVO {
	String chkGubn;
	String chkGubn2;
	String beforeData;
	String afterData;
	String charsetName;
	String password;
	String algorithm;
	String salt;
}
