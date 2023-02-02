package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoReuniao {

	PRESENCIAL(1, "Presencial"),
	VIRTUAL(2, "Virtual");
	
	private Integer code;
	private String label;
	
	private TipoReuniao(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoReuniao toEnum(Integer code) {
		for (TipoReuniao x : TipoReuniao.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoReuniao.class);
	}
	
	public static TipoReuniao toEnum(String label) {
		for (TipoReuniao x : TipoReuniao.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoReuniao.class);
	}

}
