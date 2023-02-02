package com.vili.mrmentoria.api.controllers;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.dto.PessoaDTO;
import com.vili.mrmentoria.api.services.PessoaService;
import com.vili.mrmentoria.engine.controllers.IController;
import com.vili.mrmentoria.engine.services.IService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController implements IController<Pessoa, PessoaDTO> {

	@Autowired
	private PessoaService pessoaService;
	
	@Override
	public IService<Pessoa> getService() {
		return pessoaService;
	}

	@Override
	public Class<Pessoa> getEntityType() {
		return Pessoa.class;
	}
	
	@Override
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pessoa> findById(Object id) {
		return IController.super.findById(id);
	}
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<List<Pessoa>> findAll() {
		return IController.super.findAll();
	}
	
	@Override
	@GetMapping("/page/{page}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Page<Pessoa>> findPage(HttpServletRequest request) {
		return IController.super.findPage(request);
	}
	
	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pessoa> insert(PessoaDTO dto) {
		return IController.super.insert(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Pessoa> update(PessoaDTO dto, Object id) {
		return IController.super.update(dto, id);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> delete(Object id) {
		return IController.super.delete(id);
	}
	
	@PostMapping("/imagem")
	@PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
	public ResponseEntity<Void> uploadFotoPessoa(@RequestParam(name="file") MultipartFile file) {
		URI uri = ((PessoaService) getService()).uploadFotoPessoa(file);
		return ResponseEntity.created(uri).build();
	}
}
