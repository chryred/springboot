package com.shinsegae.smon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertVO {
    private String checkInfo;
    private String checkItem;
    private String checkSubItem;
    private String dbName;

}
