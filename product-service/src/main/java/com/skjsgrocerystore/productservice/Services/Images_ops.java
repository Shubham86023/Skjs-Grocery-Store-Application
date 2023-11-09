package com.skjsgrocerystore.productservice.Services;

import com.skjsgrocerystore.productservice.DTO.Images.ImagesResponse;
import com.skjsgrocerystore.productservice.Entities.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Images_ops {

    ImagesResponse getImagesDB(int productId);

    Integer saveImagesToDB(ImagesResponse images);

    void saveImagesToFolder(List<MultipartFile> files, int proId) throws IOException;

    boolean updateImages(int proId, String imageName, Images images);

}
