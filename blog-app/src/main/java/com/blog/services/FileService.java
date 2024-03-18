package com.blog.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	//upload and display image
	String uploadImage(String path,MultipartFile file) throws IOException;
	//download and display image
	InputStream getResource(String path,String fileName) throws IOException;

}
