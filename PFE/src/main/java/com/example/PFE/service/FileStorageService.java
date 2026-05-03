package com.example.PFE.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public FileStorageService() throws IOException { Files.createDirectories(root); }

    public String save(MultipartFile file, String prefix) throws IOException {
        String safe = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path p = root.resolve(prefix + "_" + safe);
        Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
        return p.toString();
    }

    public Resource loadAsResource(String path) {
        return new FileSystemResource(path);
    }
}
