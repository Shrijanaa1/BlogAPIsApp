package com.example.blogapplicationbackend.Service;

import com.example.blogapplicationbackend.BlogApplicationBackendApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
//        get name
        String name=file.getOriginalFilename();

//        random name generate
        String randomId= UUID.randomUUID().toString();
        String newname=randomId.concat(name.substring(name.lastIndexOf('.')));
//full path
        String filePath=path+File.separator +newname;
//create folder if not present
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
//        copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newname.toUpperCase();
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
}
