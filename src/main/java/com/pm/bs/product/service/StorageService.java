package com.pm.bs.product.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	String store(MultipartFile file, Long genId, String type);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename, Long userId);

	void deleteAll();

}
