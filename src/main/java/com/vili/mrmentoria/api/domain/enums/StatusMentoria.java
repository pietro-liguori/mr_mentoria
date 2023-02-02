package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum StatusMentoria {

	EM_ANDAMENTO(1, "Em Andamento"),
	CONCLUIDA(2, "Concluída"),
	CANCELADA(3, "Cancelada"),
	PAUSADA(4, "Pausada"),
	EM_NEGOCIACAO(5, "Em Negociação"),
	OFERTA_RECUSADA(6, "Oferta Recusada");
	
	private Integer code;
	private String label;
	
	private StatusMentoria(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static StatusMentoria toEnum(Integer code) {
		for (StatusMentoria x : StatusMentoria.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", StatusMentoria.class);
	}
	
	public static StatusMentoria toEnum(String label) {
		for (StatusMentoria x : StatusMentoria.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", StatusMentoria.class);
	}

}
