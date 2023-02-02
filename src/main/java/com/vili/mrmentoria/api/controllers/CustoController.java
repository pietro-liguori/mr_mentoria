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

import com.vili.mrmentoria.api.domain.Custo;
import com.vili.mrmentoria.api.domain.dto.CustoDTO;
import com.vili.mrmentoria.api.services.CustoService;
import com.vili.mrmentoria.engine.controllers.IController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/custos")
public class CustoController implements IController<Custo, CustoDTO> {

	@Autowired
	private CustoService custoService;
	
	@Override
	public IService<Custo> getService() {
		return custoService;
	}

	@Override
	public Class<Custo> getEntityType() {
		return Custo.class;
	}
	
	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Custo> findById(Object id) {
		return IController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<Custo>> findAll() {
		return IController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<Custo>> findPage(HttpServletRequest request) {
		return IController.super.findPage(request);
	}
	
	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Custo> insert(CustoDTO dto) {
		return IController.super.insert(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Custo> update(CustoDTO dto, Object id) {
		return IController.super.update(dto, id);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> delete(Object id) {
		return IController.super.delete(id);
	}
}
