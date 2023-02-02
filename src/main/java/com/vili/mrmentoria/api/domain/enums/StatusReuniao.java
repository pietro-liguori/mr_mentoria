package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum StatusReuniao {

	AGENDAR(1, "Agendar"),
	PRE_AGENDADO(2, "Pré-agendado"),
	CONFIRMADO(3, "Confirmado"),
	REAGENDAR(4, "Reagendar"),
	REALIZADO(5, "Realizado"),
	CANCELADO(6, "Cancelado");
	
	private Integer code;
	private String label;
	
	private StatusReuniao(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static StatusReuniao toEnum(Integer code) {
		for (StatusReuniao x : StatusReuniao.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", StatusReuniao.class);
	}
	
	public static StatusReuniao toEnum(String label) {
		for (StatusReuniao x : StatusReuniao.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", StatusReuniao.class);
	}

}
