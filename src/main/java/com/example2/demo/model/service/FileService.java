package com.example2.demo.model.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public String getUniqueName(String originalName) {
        if (originalName == null || originalName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 비어있습니다.");
        }

        // 파일 확장자 추출
        String extension = "";
        int lastDotIndex = originalName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            extension = originalName.substring(lastDotIndex + 1);
            String baseName = originalName.substring(0, lastDotIndex);
        }

        // UUID를 사용하여 고유한 파일명 생성
        String uniqueName = UUID.randomUUID().toString();
        
        // 확장자가 있는 경우에만 추가
        if (!extension.isEmpty()) {
            uniqueName += "." + extension;
        }

        // 파일 경로 검증
        Path fullPath = Paths.get(uploadPath, uniqueName);
        File file = fullPath.toFile();
        
        // 파일이 이미 존재하는 경우 재시도
        while (file.exists()) {
            uniqueName = UUID.randomUUID().toString();
            if (!extension.isEmpty()) {
                uniqueName += "." + extension;
            }
            fullPath = Paths.get(uploadPath, uniqueName);
            file = fullPath.toFile();
        }

        return uniqueName;
    }
    
    private String getUploadPath(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 비어있습니다.");
        }
        return Paths.get(uploadPath, filename).toString();
    }
}