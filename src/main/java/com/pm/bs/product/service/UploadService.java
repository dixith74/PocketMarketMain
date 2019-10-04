package com.pm.bs.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	String store(MultipartFile multipartFile, Long genId, String type);
}
