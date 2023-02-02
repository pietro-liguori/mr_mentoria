package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoDesconto {

	AVULSO(1, "Avulso"),
	CUPOM(2, "Cupom"),
	PERMUTA(3, "Permuta");
	
	private Integer code;
	private String label;
	
	private TipoDesconto(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoDesconto toEnum(Integer code) {
		for (TipoDesconto x : TipoDesconto.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoDesconto.class);
	}
	
	public static TipoDesconto toEnum(String label) {
		for (TipoDesconto x : TipoDesconto.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoDesconto.class);
	}

}
