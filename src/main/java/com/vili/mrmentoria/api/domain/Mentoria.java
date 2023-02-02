package com.vili.mrmentoria.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vili.mrmentoria.api.domain.enums.StatusMentoria;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.engine.IEntity;
import com.vili.mrmentoria.engine.request.annotations.FilterSetting;
import com.vili.mrmentoria.engine.request.annotations.NoFilter;

@Entity
@JsonPropertyOrder({ "id", "descricao", "status", "total", "totalPago", "custoTotal", "lucro", "mentor", "mentorados", "reunioes", "pagamentos", "descontos", "custos", "definicao" })
public class Mentoria implements IEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	private Class<?> entityType;
	
	@JsonIgnore
	private Date createdAt;
	
	@JsonIgnore
	private Date updatedAt;
	
	@JsonIgnore
	private boolean active;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String descricao;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer status;

	@OneToOne
	@JoinColumn(name = "definicao_id")
	@FilterSetting(nesting = { "id" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DefinicaoMentoria definicao;

	@OneToOne
	@JoinColumn(name = "mentor_id")
	@FilterSetting(nesting = { "id" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Pessoa mentor;

	@OneToMany
	@JoinTable(name = "mentoria_mentorados", joinColumns = @JoinColumn(name = "mentoria_id"), inverseJoinColumns = @JoinColumn(name = "mentorado_id"))
	@FilterSetting(nesting = { "id" }, alias = "mentorado")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Pessoa> mentorados = new HashSet<>();

	@OneToMany(mappedBy = "mentoria")
	@NoFilter
	@JsonIgnoreProperties({ "mentoria" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Reuniao> reunioes = new HashSet<>();

	@ManyToMany(mappedBy = "mentorias")
	@NoFilter
	@JsonIgnoreProperties({ "mentorias" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Pagamento> pagamentos = new HashSet<>();
	
	@OneToMany
	@JoinTable(name = "mentoria_descontos", joinColumns = @JoinColumn(name = "mentoria_id"), inverseJoinColumns = @JoinColumn(name = "desconto_id"))
	@NoFilter
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Desconto> descontos = new HashSet<>();
	
	@ManyToMany(mappedBy = "mentorias")
	@NoFilter
	@JsonIgnoreProperties({ "mentorias" })
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Set<Custo> custos = new HashSet<>();

	public Mentoria() {
		init();
	}

	public Mentoria(Long id) {
		init();
		setId(id);
	}

	public Mentoria(Long id, Pessoa mentor, String descricao, DefinicaoMentoria definicao, StatusMentoria status) {
		init();
		setId(id);
		setMentor(mentor);
		setDescricao(descricao);
		setDefinicao(definicao);
		setStatus(status);
	}

	public Mentoria(Long id, Pessoa mentor, String descricao, DefinicaoMentoria definicao, StatusMentoria status, Pessoa... mentorados) {
		init();
		setId(id);
		setMentor(mentor);
		setMentorados(Stream.of(mentorados).collect(Collectors.toSet()));
		setDescricao(descricao);
		setDefinicao(definicao);
		setStatus(status);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Class<?> getEntityType() {
		return entityType;
	}

	@Override
	public void setEntityType(Class<?> entityType) {
		this.entityType = entityType;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date date) {
		this.createdAt = date;
	}
	
	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date date) {
		this.updatedAt = date;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(StatusMentoria status) {
		this.status = status == null ? null : status.getCode();
	}

	public DefinicaoMentoria getDefinicao() {
		return definicao;
	}

	public void setDefinicao(DefinicaoMentoria definicao) {
		this.definicao = definicao;
	}

	public Pessoa getMentor() {
		return mentor;
	}

	public void setMentor(Pessoa mentor) {
		this.mentor = mentor;
	}

	public List<Pessoa> getMentorados() {
		return mentorados.stream().toList();
	}

	public void setMentorados(Set<Pessoa> mentorados) {
		this.mentorados = mentorados;
	}

	public void addMentorado(Pessoa mentorado) {
		this.mentorados.add(mentorado);
	}

	public List<Reuniao> getReunioes() {
		return reunioes.stream().toList();
	}

	public void setReuniaos(Set<Reuniao> reunioes) {
		this.reunioes = reunioes;
	}

	public void addReuniao(Reuniao reuniao) {
		this.reunioes.add(reuniao);
	}

	public List<Pagamento> getPagamentos() {
		return pagamentos.stream().toList();
	}

	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public void addPagamento(Pagamento pagamento) {
		this.pagamentos.add(pagamento);
	}
	
	public List<Desconto> getDescontos() {
		return descontos.stream().toList();
	}

	public void setDescontos(Set<Desconto> descontos) {
		this.descontos = descontos;
	}

	public List<Custo> getCustos() {
		return custos.stream().toList();
	}

	public void setCustos(Set<Custo> custos) {
		this.custos = custos;
	}

	public void addCusto(Custo custo) {
		this.custos.add(custo);
	}

	public void addDesconto(Desconto desconto) {
		this.descontos.add(desconto);
	}
	
	public Double getTotal() {
		Double total = getDefinicao().getValor();
		Double descontoAbsolutoMentoria = 0.0;
		Double descontoPercentualMentoria = 0.0;
		List<Double> descontosPercentuaisMentoria = new ArrayList<>();
		List<Double> descontosAbsolutosMentoria = new ArrayList<>();

		for (Desconto desconto : descontos) {
			if (desconto.isAbsoluto())
				descontosAbsolutosMentoria.add(desconto.getValor());
			else
				descontosPercentuaisMentoria.add(desconto.getValor());
		}
		
		for (Double desconto : descontosPercentuaisMentoria) {
			descontoPercentualMentoria += desconto;
		}
		
		for (Double desconto : descontosAbsolutosMentoria) {
			descontoAbsolutoMentoria += desconto;
		}
		
		return total - (total * descontoPercentualMentoria) - descontoAbsolutoMentoria;
	}
	
	public Double getTotalPago() {
		double total = 0.0;
		List<Integer> statusList = List.of(StatusPagamento.PAGO.getCode(), StatusPagamento.PAGO_COM_DESCONTO.getCode(), StatusPagamento.PAGO_MENOR.getCode(), StatusPagamento.PAGO_MAIOR.getCode());

		for (Pagamento pagamento : pagamentos) {
			if (pagamento.getClass().isAssignableFrom(PagamentoAVista.class)) {
				PagamentoAVista pagAVista = ((PagamentoAVista) pagamento);

				if (statusList.contains(pagAVista.getStatus()))
					total += pagAVista.getValor();
			} else {
				PagamentoParcelado pagParcelado = ((PagamentoParcelado) pagamento);
				total += pagParcelado.getEntrada();
				List<Parcela> parcelas = pagParcelado.getParcelas();
				
				for (Parcela parcela : parcelas) {
					if (statusList.contains(parcela.getStatus()))
						total += parcela.getValor();
				}
			}
		}
		
		return total;
	}
	
	public Double getCustoTotal() {
		double total = 0.0;
		List<Integer> statusList = List.of(StatusPagamento.PAGO.getCode(), StatusPagamento.PAGO_COM_DESCONTO.getCode(), StatusPagamento.PAGO_MENOR.getCode(), StatusPagamento.PAGO_MAIOR.getCode());

		for (Custo custo : custos) {
			if (statusList.contains(custo.getStatus()))
				total += custo.getValor();
		}
		
		return total;
	}
	
	public Double getLucro() {
		return getTotalPago() - getCustoTotal();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IEntity other = (IEntity) obj;
		return Objects.equals(getId(), other.getId());
	}
}
