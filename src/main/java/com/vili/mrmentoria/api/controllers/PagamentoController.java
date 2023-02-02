package com.vili.mrmentoria.api.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vili.mrmentoria.api.domain.Pagamento;
import com.vili.mrmentoria.api.domain.dto.PagamentoDTO;
import com.vili.mrmentoria.api.services.PagamentoService;
import com.vili.mrmentoria.engine.controllers.IController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoController implements IController<Pagamento, PagamentoDTO> {

	@Autowired
	private PagamentoService pagamentoService;
	
	@Override
	public IService<Pagamento> getService() {
		return pagamentoService;
	}

	@Override
	public Class<Pagamento> getEntityType() {
		return Pagamento.class;
	}
	
	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pagamento> findById(Object id) {
		return IController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<Pagamento>> findAll() {
		return IController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<Pagamento>> findPage(HttpServletRequest request) {
		return IController.super.findPage(request);
	}
	
	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pagamento> insert(PagamentoDTO dto) {
		return IController.super.insert(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pagamento> update(PagamentoDTO dto, Object id) {
		return IController.super.update(dto, id);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> delete(Object id) {
		return IController.super.delete(id);
	}
}
