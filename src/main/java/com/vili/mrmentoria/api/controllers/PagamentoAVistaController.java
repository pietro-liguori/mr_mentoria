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

import com.vili.mrmentoria.api.domain.PagamentoAVista;
import com.vili.mrmentoria.api.services.PagamentoAVistaService;
import com.vili.mrmentoria.engine.controllers.SearchController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/pagamentosAVista")
public class PagamentoAVistaController implements SearchController<PagamentoAVista> {

	@Autowired
	private PagamentoAVistaService pagamentoAVistaService;
	
	@Override
	public IService<PagamentoAVista> getService() {
		return pagamentoAVistaService;
	}

	@Override
	public Class<PagamentoAVista> getEntityType() {
		return PagamentoAVista.class;
	}

	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<PagamentoAVista> findById(@PathVariable Object id) {
		return SearchController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<PagamentoAVista>> findAll() {
		return SearchController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<PagamentoAVista>> findPage(HttpServletRequest request) {
		return SearchController.super.findPage(request);
	}
}
