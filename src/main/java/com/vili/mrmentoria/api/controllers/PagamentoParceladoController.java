package com.vili.mrmentoria.api.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vili.mrmentoria.api.domain.PagamentoParcelado;
import com.vili.mrmentoria.api.services.PagamentoParceladoService;
import com.vili.mrmentoria.engine.controllers.SearchController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/pagamentosParcelados")
public class PagamentoParceladoController implements SearchController<PagamentoParcelado> {

	@Autowired
	private PagamentoParceladoService pagamentoParceladoService;
	
	@Override
	public IService<PagamentoParcelado> getService() {
		return pagamentoParceladoService;
	}

	@Override
	public Class<PagamentoParcelado> getEntityType() {
		return PagamentoParcelado.class;
	}

	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<PagamentoParcelado> findById(@PathVariable Object id) {
		return SearchController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<PagamentoParcelado>> findAll() {
		return SearchController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<PagamentoParcelado>> findPage(HttpServletRequest request) {
		return SearchController.super.findPage(request);
	}
}
