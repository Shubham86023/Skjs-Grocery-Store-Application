package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImageRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImagesRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImage_ops {

    ProductImagesRequest getProductImages(int id);

    boolean saveImagesToFolder(MultipartFile[] file, int pro);

    boolean updateImages(ProductImageRequest productImage);

    void saveImagesToDB(ProductImagesRequest img);
}
