package com.example.lab8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    // Отображение страницы с формой загрузки
    @GetMapping("/")
    public String showUploadForm() {
        return "upload-form"; // upload-form.html в папке templates
    }

    // Обработка загрузки фотографии
    @PostMapping("/upload")
    public String uploadPhoto(MultipartFile file, Model model) {
        try {
            // Загрузка файла через сервис
            Photo photo = photoService.uploadPhoto(file);

            // Передаем данные в модель
            model.addAttribute("message", "Фото успешно загружено!");
            model.addAttribute("photo", photo);
        } catch (IOException e) {
            model.addAttribute("error", "Ошибка загрузки фото: " + e.getMessage());
        }
        return "upload-form";
    }
}