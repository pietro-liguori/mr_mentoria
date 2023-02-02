package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum PerfilPessoa {

	MENTOR(1, "ROLE_MENTOR"),
	MENTORADO(2, "ROLE_MENTORADO"),
	ADMIN(3, "ROLE_ADMIN");
	
	private Integer code;
	private String label;
	
	private PerfilPessoa(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static PerfilPessoa toEnum(Integer code) {
		for (PerfilPessoa x : PerfilPessoa.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", PerfilPessoa.class);
	}
	
	public static PerfilPessoa toEnum(String label) {
		for (PerfilPessoa x : PerfilPessoa.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", PerfilPessoa.class);
	}

}
