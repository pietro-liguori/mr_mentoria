package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoPagamento {

	A_VISTA(1, "A Vista"),
	PARCELADO(2, "Parcelado");
	
	private Integer code;
	private String label;
	
	private TipoPagamento(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoPagamento toEnum(Integer code) {
		for (TipoPagamento x : TipoPagamento.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoPagamento.class);
	}
	
	public static TipoPagamento toEnum(String label) {
		for (TipoPagamento x : TipoPagamento.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoPagamento.class);
	}

}
