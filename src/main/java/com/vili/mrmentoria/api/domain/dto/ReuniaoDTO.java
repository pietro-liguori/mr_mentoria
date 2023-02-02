package com.vili.mrmentoria.api.domain.dto;

import java.util.Date;

import com.vili.mrmentoria.api.domain.Reuniao;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.enums.StatusReuniao;
import com.vili.mrmentoria.api.domain.enums.TipoReuniao;
import com.vili.mrmentoria.engine.IEntity;

public class ReuniaoDTO extends DataTransferObject {

	private String descricao;
	private Date data;
	private String local;
	private Integer status;
	private Integer tipo;
	private Long mentoriaId;

	public ReuniaoDTO() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		StatusReuniao status = StatusReuniao.toEnum(getStatus());
		TipoReuniao tipo = TipoReuniao.toEnum(getStatus());
		Reuniao aula = new Reuniao(getId(), new Mentoria(getMentoriaId()), getDescricao(), getData(), getLocal(), tipo, status);
		return aula;
	}
}
