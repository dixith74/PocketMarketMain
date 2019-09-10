package com.pm.bs.product.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pm.bs.aws.AmazonClientService;
import com.pm.bs.product.controller.StorageException;
import com.pm.common.utility.Utility;

@Service
public class FileStorageServiceImpl implements StorageService {

	private final Path rootLocation;
	private AmazonClientService amazonClientService;
	
	private static final String APP_NAME = "pocket-market";

	public FileStorageServiceImpl(StorageProperties properties, AmazonClientService amazonClientService) {
		this.rootLocation = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
		this.amazonClientService = amazonClientService;
	}

	@Override
	public String store(MultipartFile multipartFile, Long genId, String type) {
		File file = Utility.convertMultiPartToFile(multipartFile);
	    String fileName = Utility.generateFileName(multipartFile);
	    if (type.equalsIgnoreCase("products")) {
	    	fileName = "assets/products/"+genId + "/" + fileName;
	    } else {
	    	fileName = "assets/users/"+genId + "/" + fileName;
	    }
	    AmazonS3 amazonS3 = amazonClientService.getS3Client();
	    if (amazonS3.doesBucketExistV2(APP_NAME)) {
	    	amazonS3.putObject(new PutObjectRequest(APP_NAME,fileName, file)
	    			.withCannedAcl(CannedAccessControlList.PublicRead));
	    	return amazonS3.getUrl(APP_NAME,fileName).toString();
	    }
		return fileName;
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return Paths.get(this.rootLocation +filename);
	}

	@Override
	public Resource loadAsResource(String filename, Long userId) {
		try {
			Path file = load("\\products\\"+userId+"\\"+filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
