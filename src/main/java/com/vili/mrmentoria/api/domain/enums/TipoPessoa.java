package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoPessoa {

	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private Integer code;
	private String label;
	
	private TipoPessoa(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoPessoa toEnum(Integer code) {
		for (TipoPessoa x : TipoPessoa.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoPessoa.class);
	}
	
	public static TipoPessoa toEnum(String label) {
		for (TipoPessoa x : TipoPessoa.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoPessoa.class);
	}

}
