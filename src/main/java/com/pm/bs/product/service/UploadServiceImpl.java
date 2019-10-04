package com.pm.bs.product.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pm.common.aws.AmazonClientService;
import com.pm.common.utility.Utility;

@Service
public class UploadServiceImpl implements UploadService {

	private AmazonClientService amazonClientService;
	
	private static final String APP_NAME = "pocket-market";

	public UploadServiceImpl(AmazonClientService amazonClientService) {
		this.amazonClientService = amazonClientService;
	}

	@Override
	public String store(MultipartFile multipartFile, Long genId, String type) {
		File file = Utility.convertMultiPartToFile(multipartFile);
	    String fileName = Utility.generateFileName(multipartFile);
	    fileName = "assets/products/"+genId + "/" + fileName;
	    AmazonS3 amazonS3 = amazonClientService.getS3Client();
	    if (amazonS3.doesBucketExistV2(APP_NAME)) {
	    	amazonS3.putObject(new PutObjectRequest(APP_NAME,fileName, file)
	    			.withCannedAcl(CannedAccessControlList.PublicRead));
	    	return amazonS3.getUrl(APP_NAME,fileName).toString();
	    }
		return fileName;
	}
}
