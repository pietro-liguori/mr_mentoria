package com.vili.mrmentoria.api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.domain.DefinicaoMentoria;
import com.vili.mrmentoria.api.domain.Desconto;
import com.vili.mrmentoria.api.domain.Mentoria;
import com.vili.mrmentoria.api.domain.PagamentoAVista;
import com.vili.mrmentoria.api.domain.PagamentoParcelado;
import com.vili.mrmentoria.api.domain.Parcela;
import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.Reuniao;
import com.vili.mrmentoria.api.domain.enums.DetalhePessoa;
import com.vili.mrmentoria.api.domain.enums.MetodoPagamento;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusMentoria;
import com.vili.mrmentoria.api.domain.enums.StatusPagamento;
import com.vili.mrmentoria.api.domain.enums.StatusPessoa;
import com.vili.mrmentoria.api.domain.enums.StatusReuniao;
import com.vili.mrmentoria.api.domain.enums.TipoCusto;
import com.vili.mrmentoria.api.domain.enums.TipoDesconto;
import com.vili.mrmentoria.api.domain.enums.TipoMentoria;
import com.vili.mrmentoria.api.domain.enums.TipoPagamento;
import com.vili.mrmentoria.api.domain.enums.TipoPessoa;
import com.vili.mrmentoria.api.domain.enums.TipoReuniao;
import com.vili.mrmentoria.api.repositories.CustoRepository;
import com.vili.mrmentoria.api.repositories.DefinicaoMentoriaRepository;
import com.vili.mrmentoria.api.repositories.DescontoRepository;
import com.vili.mrmentoria.api.repositories.MentoriaRepository;
import com.vili.mrmentoria.api.repositories.PagamentoRepository;
import com.vili.mrmentoria.api.repositories.ParcelaRepository;
import com.vili.mrmentoria.api.repositories.PessoaRepository;
import com.vili.mrmentoria.api.repositories.ReuniaoRepository;

@Service
public class DBService {

	@Autowired
	PessoaRepository pessoaRepo;
	
	@Autowired
	MentoriaRepository mentoriaRepo;

	@Autowired
	DefinicaoMentoriaRepository definicaoMentoriaRepo;
	
	@Autowired
	DescontoRepository descontoRepo;

	@Autowired
	PagamentoRepository pagamentoRepo;

	@Autowired
	ParcelaRepository parcelaRepo;

	@Autowired
	CustoRepository custoRepo;

	@Autowired
	ReuniaoRepository reuniaoRepo;

	@Autowired
	BCryptPasswordEncoder be;

	public void instanciateDatabase() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Pessoa mentor = new Pessoa(null, "Marina", "dos Santos Rotella", "contato.mr.mentoria@gmail.com", be.encode("12345678901234567890"), TipoPessoa.PESSOA_FISICA, StatusPessoa.ATIVO, DetalhePessoa.NENHUM, PerfilPessoa.MENTOR, PerfilPessoa.ADMIN);
		Pessoa mentorado1 = new Pessoa(null, "Carolina", "Guizo", "carolina@gmail.com", be.encode("12345678901234567890"), TipoPessoa.PESSOA_FISICA, StatusPessoa.ATIVO, DetalhePessoa.ADIMPLENTE, PerfilPessoa.MENTORADO);
		Pessoa mentorado2 = new Pessoa(null, "Paola", "Guaratini", "paola@gmail.com", be.encode("12345678901234567890"), TipoPessoa.PESSOA_FISICA, StatusPessoa.ATIVO, DetalhePessoa.ADIMPLENTE, PerfilPessoa.MENTORADO);
		Pessoa mentorado3 = new Pessoa(null, "Fernanda", "Empório Alme", "fernanda@gmail.com", be.encode("12345678901234567890"), TipoPessoa.PESSOA_FISICA, StatusPessoa.ATIVO, DetalhePessoa.ADIMPLENTE, PerfilPessoa.MENTORADO);
		
		pessoaRepo.saveAll(List.of(mentor, mentorado1, mentorado2, mentorado3));
		
		DefinicaoMentoria mentoriaVIP = new DefinicaoMentoria(null, "Mentoria VIP", "Descrição Mentoria VIP", 6, 60, Calendar.MINUTE, 2, 3000.0, TipoMentoria.VIP, true, true, true);
		DefinicaoMentoria mentoriaEmGrupo = new DefinicaoMentoria(null, "Mentoria em Grupo", "Descrição Mentoria em Grupo", 6, 60, Calendar.MINUTE, 25, 1500.0, TipoMentoria.EM_GRUPO, true, true, true);

		definicaoMentoriaRepo.saveAll(List.of(mentoriaVIP, mentoriaEmGrupo));

		Mentoria mentoriaCarol = new Mentoria(null, mentor, "Mentoria Carol Guizo", mentoriaVIP, StatusMentoria.EM_ANDAMENTO);
		Mentoria mentoriaPaola = new Mentoria(null, mentor, "Mentoria Paola", mentoriaVIP, StatusMentoria.EM_ANDAMENTO);
		Mentoria mentoriaFernanda = new Mentoria(null, mentor, "Mentoria Fernanda Empório Alme", mentoriaVIP, StatusMentoria.EM_ANDAMENTO);
		
		mentoriaCarol.addMentorado(mentorado1);
		mentoriaPaola.addMentorado(mentorado2);
		mentoriaFernanda.addMentorado(mentorado3);

		mentoriaRepo.saveAll(List.of(mentoriaCarol, mentoriaPaola, mentoriaFernanda));

		Desconto cupom10 = new Desconto(null, mentoriaCarol, "Cupom de 10% de desconto", 0.1, false, TipoDesconto.CUPOM);
		Desconto avulso100 = new Desconto(null, mentoriaFernanda, "Desconto de R$ 100,00", 100.0, true, TipoDesconto.AVULSO);
		Desconto permuta = new Desconto(null, mentoriaPaola, "Permuta", 1.0, false, TipoDesconto.PERMUTA);

		descontoRepo.saveAll(List.of(cupom10, avulso100, permuta));

		PagamentoAVista pagamentoCarol = new PagamentoAVista(null, "Pagamento Carol", 2700.0, sdf.parse("12/10/2022 14:00:00"), null, MetodoPagamento.PIX, TipoPagamento.A_VISTA, StatusPagamento.PAGO_COM_DESCONTO, mentoriaCarol);
		PagamentoParcelado pagamentoFernanda = new PagamentoParcelado(null, "Pagamento Fernanda", 2900.0, 3, 500.0, sdf.parse("20/09/2022 14:00:00"), null, MetodoPagamento.PIX, TipoPagamento.PARCELADO, StatusPagamento.PAGO, mentoriaFernanda);
		PagamentoAVista pagamentoPaola = new PagamentoAVista(null, "Pagamento Paola", 0.0, sdf.parse("15/11/2022 14:00:00"), "Permuta", MetodoPagamento.PIX, TipoPagamento.A_VISTA, StatusPagamento.PAGO_COM_DESCONTO, mentoriaPaola);

		pagamentoRepo.saveAll(List.of(pagamentoCarol, pagamentoFernanda, pagamentoPaola));
		
		Parcela parcelaFernanda1 = new Parcela(null, pagamentoFernanda, 800.0, sdf.parse("20/10/2022 22:00:00"), StatusPagamento.PAGO);
		Parcela parcelaFernanda2 = new Parcela(null, pagamentoFernanda, 800.0, sdf.parse("20/11/2022 22:00:00"), StatusPagamento.PAGO);
		Parcela parcelaFernanda3 = new Parcela(null, pagamentoFernanda, 800.0, sdf.parse("20/12/2022 22:00:00"), StatusPagamento.PAGO);
		
		parcelaRepo.saveAll(List.of(parcelaFernanda1, parcelaFernanda2, parcelaFernanda3));
		
		Custo custoCarol1 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("15/10/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);
		Custo custoCarol2 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("29/10/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);
		Custo custoCarol3 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("05/11/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);
		Custo custoCarol4 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("19/11/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);
		Custo custoCarol5 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("03/12/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);
		Custo custoCarol6 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("17/12/2022 09:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaCarol);

		Custo custoPaola1 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("16/11/2022 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);
		Custo custoPaola2 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("30/11/2022 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);
		Custo custoPaola3 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("14/12/2022 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);
		Custo custoPaola4 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("28/12/2022 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);
		Custo custoPaola5 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("11/01/2023 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);
		Custo custoPaola6 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("25/01/2023 10:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaPaola);

		Custo custoFernanda1 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("21/09/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);
		Custo custoFernanda2 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("03/10/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);
		Custo custoFernanda3 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("17/10/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);
		Custo custoFernanda4 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("31/10/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);
		Custo custoFernanda5 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("13/11/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);
		Custo custoFernanda6 = new Custo(null, "Aluguel Smart CEO", 25.0, sdf.parse("27/11/2022 15:00:00"), null, TipoCusto.ALUGUEL, StatusPagamento.PAGO, mentoriaFernanda);

		custoRepo.saveAll(List.of(custoCarol1, custoCarol2, custoCarol3, custoCarol4, custoCarol5, custoCarol6, custoPaola1, custoPaola2, custoPaola3, custoPaola4, custoPaola5, custoPaola6, custoFernanda1, custoFernanda2, custoFernanda3, custoFernanda4, custoFernanda5, custoFernanda6));

		Reuniao aulaCarol1 = new Reuniao(null, mentoriaCarol, "Aula 1 Carol", sdf.parse("15/10/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaCarol2 = new Reuniao(null, mentoriaCarol, "Aula 2 Carol", sdf.parse("29/10/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaCarol3 = new Reuniao(null, mentoriaCarol, "Aula 3 Carol", sdf.parse("05/11/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaCarol4 = new Reuniao(null, mentoriaCarol, "Aula 4 Carol", sdf.parse("19/11/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaCarol5 = new Reuniao(null, mentoriaCarol, "Aula 5 Carol", sdf.parse("03/12/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaCarol6 = new Reuniao(null, mentoriaCarol, "Aula 6 Carol", sdf.parse("17/12/2022 09:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);

		Reuniao aulaPaola1 = new Reuniao(null, mentoriaPaola, "Aula 1 Paola", sdf.parse("16/11/2022 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaPaola2 = new Reuniao(null, mentoriaPaola, "Aula 2 Paola", sdf.parse("30/11/2022 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaPaola3 = new Reuniao(null, mentoriaPaola, "Aula 3 Paola", sdf.parse("14/12/2022 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaPaola4 = new Reuniao(null, mentoriaPaola, "Aula 4 Paola", sdf.parse("28/12/2022 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaPaola5 = new Reuniao(null, mentoriaPaola, "Aula 5 Paola", sdf.parse("11/01/2023 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.CONFIRMADO);
		Reuniao aulaPaola6 = new Reuniao(null, mentoriaPaola, "Aula 6 Paola", sdf.parse("25/01/2023 10:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.PRE_AGENDADO);

		Reuniao aulaFernanda1 = new Reuniao(null, mentoriaFernanda, "Aula 1 Fernanda", sdf.parse("21/09/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaFernanda2 = new Reuniao(null, mentoriaFernanda, "Aula 2 Fernanda", sdf.parse("03/10/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaFernanda3 = new Reuniao(null, mentoriaFernanda, "Aula 3 Fernanda", sdf.parse("17/10/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaFernanda4 = new Reuniao(null, mentoriaFernanda, "Aula 4 Fernanda", sdf.parse("31/10/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaFernanda5 = new Reuniao(null, mentoriaFernanda, "Aula 5 Fernanda", sdf.parse("13/11/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);
		Reuniao aulaFernanda6 = new Reuniao(null, mentoriaFernanda, "Aula 6 Fernanda", sdf.parse("27/11/2022 15:00:00"), "Smart CEO", TipoReuniao.PRESENCIAL, StatusReuniao.REALIZADO);

		reuniaoRepo.saveAll(List.of(aulaCarol1, aulaCarol2, aulaCarol3, aulaCarol4, aulaCarol5, aulaCarol6, aulaPaola1, aulaPaola2, aulaPaola3, aulaPaola4, aulaPaola5, aulaPaola6, aulaFernanda1, aulaFernanda2, aulaFernanda3, aulaFernanda4, aulaFernanda5, aulaFernanda6));
		
//		emailService.sendBoasVindas(mentoriaPaola);
	}
}
