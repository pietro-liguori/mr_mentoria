package com.vili.mrmentoria.api.domain.enums;

import com.vili.mrmentoria.engine.exceptions.custom.EnumValueNotFoundException;

public enum StatusPagamento {

	AGUARDANDO_PAGAMENTO(1, "Aguardando pagamento"),
	PAGO_MENOR(2, "Pago a menor"),
	PAGO_MAIOR(3, "Pago a maior"),
	PAGO(4, "Em atraso"),
	AGUARDANDO_CONFIRMACAO(5, "Aguardando confirmação"),
	EM_ATRASO(6, "Em atraso"),
	CANCELADO(7, "Cancelado"),
	PAGO_COM_DESCONTO(8, "Pago com desconto");
	
	private Integer code;
	private String label;
	
	private StatusPagamento(Integer code, String label) {
		this.code = code;
		this.label = label;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static StatusPagamento toEnum(Integer code) {
		for (StatusPagamento x : StatusPagamento.values()) {
			if (x.getCode() == code) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(code, "code", StatusPagamento.class);
	}
	
	public static StatusPagamento toEnum(String label) {
		for (StatusPagamento x : StatusPagamento.values()) {
			if (x.getLabel() == label) {
				return x;
			}
		}
		
		throw new EnumValueNotFoundException(label, "label", StatusPagamento.class);
	}

}
