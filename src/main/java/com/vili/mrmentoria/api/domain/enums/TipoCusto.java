package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum TipoCusto {

	ALUGUEL(1, "Aluguel"),
	ENCONTRO_MENTORADOS(2, "Encontro mentorados"),
	TAXA_DE_SERVICO(3, "Taxa de serviço"),
	OUTROS(4, "Outros");
	
	private Integer code;
	private String label;
	
	private TipoCusto(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoCusto toEnum(Integer code) {
		for (TipoCusto x : TipoCusto.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", TipoCusto.class);
	}
	
	public static TipoCusto toEnum(String label) {
		for (TipoCusto x : TipoCusto.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", TipoCusto.class);
	}

}
