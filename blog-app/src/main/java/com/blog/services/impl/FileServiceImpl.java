package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//file name
		String name=file.getOriginalFilename();
		//abc.png
		
		//generate random file name to avoid duplicate file name from user
		
		String randomId=UUID.randomUUID().toString();
		String fileName=randomId.concat(name.substring(name.lastIndexOf("."))); //adds .png on generated id
		
		//fullpath
		String filePath=path+File.separator+fileName;
		
		
		
		//if folder is not created
		File f=new File(path);
		if(!(f.exists())) {
			f.mkdir();
		}
		
		//file copy
		
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return fileName;
		
	}

	@Override
	public InputStream getResource(String path, String fileName) throws IOException {
		
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		return is;
		
	}

}
