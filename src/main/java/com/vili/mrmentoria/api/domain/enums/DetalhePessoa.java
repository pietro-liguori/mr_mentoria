package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum DetalhePessoa {

	ADIMPLENTE(1, "Adimplente"),
	INADIMPLENTE(2, "Inadimplente"),
	NENHUM(3, null);
	
	private Integer code;
	private String label;
	
	private DetalhePessoa(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static DetalhePessoa toEnum(Integer code) {
		for (DetalhePessoa value : DetalhePessoa.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", DetalhePessoa.class);
	}
	
	public static DetalhePessoa toEnum(String label) {
		for (DetalhePessoa value : DetalhePessoa.values()) {
			if (value.getLabel() == label) {
				return value;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", DetalhePessoa.class);
	}

}
