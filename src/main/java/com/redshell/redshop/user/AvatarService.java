package com.redshell.redshop.user;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class AvatarService {

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            "jpg",
            "jpeg",
            "png"
    );

    private final Path uploadDirectory =
            Paths.get("/app/uploads/avatars")
                    .toAbsolutePath()
                    .normalize();

    public String saveAvatar(MultipartFile file) {

        validate(file);

        String extension = getExtension(file.getOriginalFilename());

        String filename =
                UUID.randomUUID() + "." + extension;

        Path target =
                uploadDirectory
                        .resolve(filename)
                        .normalize();

        if (!target.startsWith(uploadDirectory)) {
            throw new IllegalArgumentException(
                    "Invalid file path"
            );
        }

        try {
            Files.createDirectories(uploadDirectory);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(
                        inputStream,
                        target
                );
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not save avatar",
                    e
            );
        }

        return filename;
    }

    private void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Avatar file is required"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "Avatar file is too large"
            );
        }

        String contentType =
                file.getContentType();

        if (contentType == null
                || !ALLOWED_CONTENT_TYPES.contains(contentType)) {

            throw new IllegalArgumentException(
                    "Invalid avatar content type"
            );
        }

        String extension =
                getExtension(file.getOriginalFilename());

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Invalid avatar file extension"
            );
        }
    }

    private String getExtension(String filename) {

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid avatar filename"
            );
        }

        int lastDot = filename.lastIndexOf('.');

        if (lastDot < 0
                || lastDot == filename.length() - 1) {

            throw new IllegalArgumentException(
                    "Avatar file must have an extension"
            );
        }

        return filename
                .substring(lastDot + 1)
                .toLowerCase();
    }
}