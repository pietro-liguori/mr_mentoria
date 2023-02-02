package com.vili.mrmentoria.api.services;

import java.awt.image.BufferedImage;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vili.mrmentoria.api.domain.Pessoa;
import com.vili.mrmentoria.api.domain.enums.PerfilPessoa;
import com.vili.mrmentoria.api.repositories.PessoaRepository;
import com.vili.mrmentoria.api.security.UserSS;
import com.vili.mrmentoria.engine.exceptions.custom.AuthorizationException;
import com.vili.mrmentoria.engine.repositories.IRepository;
import com.vili.mrmentoria.engine.services.IService;

@Service
public class PessoaService implements IService<Pessoa> {

	@Value("${img.prefix.pessoa.imagem}")
	private String prefix;

	@Value("${img.pessoa.size}")
	private Integer size;

	@Autowired
	S3Service s3Service;
	
	@Autowired
	ImageService imageService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public IRepository<Pessoa> getRepository() {
		return pessoaRepository;
	}

	@Override
	public Pessoa findById(Long id) {
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(PerfilPessoa.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Access denied");
		}
		
		return IService.super.findById(id);
	}

	public URI uploadFotoPessoa(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		
		if (user == null)
			throw new AuthorizationException("Access denied");
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, ".jpg"), fileName, "image");
	}
}
