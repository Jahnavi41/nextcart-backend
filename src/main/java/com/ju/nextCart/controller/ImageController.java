package com.ju.nextCart.controller;

import com.ju.nextCart.dto.ImageDTO;
import com.ju.nextCart.exceptions.ResourceNotFoundException;
import com.ju.nextCart.model.Image;
import com.ju.nextCart.response.ApiResponse;
import com.ju.nextCart.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    public static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private final IImageService imageService;

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam  List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDTO> images = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success!", images));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName()+ "\"").body(resource);
    }

    @PutMapping("image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image!=null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image!=null) {
                imageService.delImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed", INTERNAL_SERVER_ERROR));
    }
}
