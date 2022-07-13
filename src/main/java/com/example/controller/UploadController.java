package com.example.controller;

import com.example.common.R;
import com.example.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${app.upload.path.img}")
    private String imgPath;

    @Value("${app.upload.path.video}")
    private String videoPath;


    public String upload(MultipartFile file, String rootDir) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new FileUploadException("文件上传异常");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        String dir = jarFile.getParentFile().getPath() + rootDir;
        File directory = new File(dir);
        if (!directory.exists()) {
            boolean _ = directory.mkdirs();
        }
        String paths = directory.getCanonicalPath();
        File dest = new File(paths + '/' + fileName);
        file.transferTo(dest);
        return fileName;
    }


    @PostMapping("/image")
    public R uploadImage(MultipartFile file) throws IOException {
        String fileName = upload(file, imgPath);
        return R.success("上传图片成功", fileName);
    }

    @PostMapping("/video")
    public R uploadVideo(MultipartFile file) throws IOException {
        String fileName = upload(file, videoPath);
        return R.success("上传视频成功", fileName);
    }

}