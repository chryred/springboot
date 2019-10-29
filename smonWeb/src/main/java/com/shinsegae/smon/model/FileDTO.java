package com.shinsegae.smon.util;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDTO {
	private String name;
	private String pwd;
	private String title;
	private String content;
	private String fileName;
	private String seq;
	private String imageGubn;
	private MultipartFile uploadfile;
}