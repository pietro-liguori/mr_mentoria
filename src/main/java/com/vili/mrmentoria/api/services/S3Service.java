package com.vili.mrmentoria.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vili.mrmentoria.engine.exceptions.custom.FileException;

@Service
public class S3Service {

	private static Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucket;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			LOG.info(e.getClass().getSimpleName() + ": " + e.getMessage());
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}
	
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload");
			s3client.putObject(new PutObjectRequest(bucket, fileName, is, meta));
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucket, fileName).toURI();
		} catch (URISyntaxException e) {
			LOG.info(e.getClass().getSimpleName() + ": " + e.getMessage());
			throw new RuntimeException("Erro ao converter URL para URI");
		}
	}
}
