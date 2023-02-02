package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum StatusPessoa {

	ATIVO(1, "Ativo"),
	INATIVO(2, "Inativo"),
	SUSPENSO(3, "Suspenso");
	
	private Integer code;
	private String label;
	
	private StatusPessoa(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static StatusPessoa toEnum(Integer code) {
		for (StatusPessoa x : StatusPessoa.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", StatusPessoa.class);
	}
	
	public static StatusPessoa toEnum(String label) {
		for (StatusPessoa x : StatusPessoa.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", StatusPessoa.class);
	}

}
