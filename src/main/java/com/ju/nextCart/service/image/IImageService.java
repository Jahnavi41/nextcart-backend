package com.ju.nextCart.service.image;

import com.ju.nextCart.dto.ImageDTO;
import com.ju.nextCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void delImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
