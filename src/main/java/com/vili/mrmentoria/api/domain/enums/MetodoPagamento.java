package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum MetodoPagamento {

	DINHEIRO(1, "Dinheiro"),
	PIX(2, "PIX"),
	CRÉDITO(3, "Crédito"),
	DÉBITO(4, "Débito");

	private Integer code;
	private String label;
	
	private MetodoPagamento(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static MetodoPagamento toEnum(Integer code) {
		for (MetodoPagamento x : MetodoPagamento.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", MetodoPagamento.class);
	}
	
	public static MetodoPagamento toEnum(String label) {
		for (MetodoPagamento x : MetodoPagamento.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", MetodoPagamento.class);
	}

}
