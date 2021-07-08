package com.kr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;

@Controller
public class FileController {
    @Value("${upload.file.path}")
    private String uploadPathStr;
    @Value("${download.file.path}")
    private String downloadPathStr;
    public FileController(){}

    @PostMapping("/upload")
    public @ResponseBody boolean upload(@RequestParam MultipartFile file,@RequestParam String filename)
    {
        if(file == null || file.isEmpty() || filename == null || filename.isEmpty())
            return false;
        try(InputStream inputStream = file.getInputStream())
        {
            Path uploadPath = Paths.get(uploadPathStr);
            if(!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, Paths.get(uploadPathStr).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("upload file , filename is "+ filename + "      " + "uploadPathStr:" +uploadPathStr );
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/download")
    public ResponseEntity<FileSystemResource> download(@RequestParam String filename)
    {
//        System.out.println("download file , filename is "+filename);
        if(filename == null || filename.isEmpty())
            return null;
        File file = Paths.get(downloadPathStr).resolve(filename).toFile();
        System.out.println("file: "+ file);
        if(file.exists() && file.canRead())
        {
            System.out.println("download file , filename is "+filename + "      " + "downloadPathStr:" +downloadPathStr);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        }
        else
            return null;
    }
}
