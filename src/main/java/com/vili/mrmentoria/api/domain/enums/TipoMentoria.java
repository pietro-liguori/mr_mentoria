package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoMentoria {

	VIP(1, "VIP"),
	EM_GRUPO(2, "Em Grupo"),
	AVULSA(3, "Avulsa");
	
	private Integer code;
	private String label;
	
	private TipoMentoria(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoMentoria toEnum(Integer code) {
		for (TipoMentoria x : TipoMentoria.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoMentoria.class);
	}
	
	public static TipoMentoria toEnum(String label) {
		for (TipoMentoria x : TipoMentoria.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoMentoria.class);
	}

}
