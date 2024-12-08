package com.example.lab8;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    @Value("${upload.dir}") // Путь к директории из application.properties
    private String uploadDir;

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo uploadPhoto(MultipartFile file) throws IOException {
        // Получаем имя файла
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Сохраняем файл в файловую систему
        Files.createDirectories(filePath.getParent()); // Создаем папки, если их нет
        Files.write(filePath, file.getBytes()); // Записываем файл

        // Сохраняем запись о фото в базу данных
        Photo photo = new Photo();
        photo.setImageLink("/" + fileName); // Сохраняем относительный путь
        return photoRepository.save(photo);
    }
}