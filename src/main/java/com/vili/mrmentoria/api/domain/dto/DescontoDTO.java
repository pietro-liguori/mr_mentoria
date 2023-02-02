package com.vili.mrmentoria.api.domain.dto;

import com.vili.mrmentoria.api.domain.Desconto;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.enums.TipoDesconto;
import com.vili.mrmentoria.api.validation.constraints.ValidDesconto;
import com.vili.mrmentoria.engine.IEntity;

@ValidDesconto
public class DescontoDTO extends DataTransferObject {

	private String descricao;
	private Double valor;
	private Boolean absoluto;
	private Integer tipo;
	private Long mentoriaId;

	public DescontoDTO() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean isAbsoluto() {
		return absoluto;
	}

	public void setAbsoluto(Boolean absoluto) {
		this.absoluto = absoluto;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Long getMentoriaId() {
		return mentoriaId;
	}

	public void setMentoriaId(Long mentoriaId) {
		this.mentoriaId = mentoriaId;
	}

	@Override
	public IEntity toEntity() {
		TipoDesconto tipo = TipoDesconto.toEnum(getTipo());
		Desconto desconto = new Desconto(getId(), new Mentoria(getMentoriaId()), getDescricao(), getValor(), isAbsoluto(), tipo);
		return desconto;
	}
}
