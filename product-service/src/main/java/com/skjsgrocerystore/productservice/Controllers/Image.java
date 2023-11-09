package com.skjsgrocerystore.productservice.Controllers;

import com.skjsgrocerystore.productservice.DTO.Images.ImagesResponse;
import com.skjsgrocerystore.productservice.Entities.Images;
import com.skjsgrocerystore.productservice.Exceptions.ResourceNotFoundException;
import com.skjsgrocerystore.productservice.Services.Images_ops;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class Image {

    private final Images_ops imagesOps;

    @GetMapping("/getImagesDB/{id}")
    public ResponseEntity<ImagesResponse> getImagesDB(@PathVariable int id) {
        ImagesResponse images = imagesOps.getImagesDB(id);

        if (images != null) {
            return ResponseEntity.status(HttpStatus.OK).body(images);
        } else {
            throw new ResourceNotFoundException("No Images found for ID : " + id);
        }
    }

    @PostMapping("/saveImagesToDB")
    public ResponseEntity<Integer> saveImagesToDB(@RequestBody @Valid ImagesResponse images) {
        int id = imagesOps.saveImagesToDB(images);

        if (id != 0) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/saveImagesToFolder/{proId}")
    public ResponseEntity<String> saveImagesToFolder(@PathVariable int proId,
                                                     @RequestParam("files") List<MultipartFile> files) {

        try {
            imagesOps.saveImagesToFolder(files, proId); // Implement this method in your service.
            return ResponseEntity.ok("Files uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("File upload failed");
        }
    }


    @PutMapping("/updateImages/{proId}/{imageName}")
    public ResponseEntity<?> updateImages(@PathVariable int proId, @PathVariable String imageName,
                                          @RequestBody Images images) {

        if (imagesOps.updateImages(proId, imageName, images)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.internalServerError().build();
        }

    }


}
