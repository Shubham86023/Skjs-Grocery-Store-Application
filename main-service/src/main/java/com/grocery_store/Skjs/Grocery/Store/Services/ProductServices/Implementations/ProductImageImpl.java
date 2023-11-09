package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImageRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImagesRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductImage_ops;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ProductImageImpl implements ProductImage_ops {

    private final RestTemplate restTemplate;

    @Override
    public ProductImagesRequest getProductImages(int id) {

        ProductImagesRequest data = new ProductImagesRequest();
        try {
            ResponseEntity<ProductImagesRequest> response = restTemplate.getForEntity("http://product-service/images/getImagesDB/" + id, ProductImagesRequest.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                data = response.getBody();
            }

        } catch (Exception ex) {
            System.out.println("Unable to get Images");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public void saveImagesToDB(ProductImagesRequest img) {
        try {
            restTemplate.postForObject("http://product-service/images/saveImagesToDB", img, Integer.class);
        } catch (Exception ex) {
            System.out.println("Images not saved in DB");
            System.out.println(ex);
        }
    }

    @Override
    public boolean saveImagesToFolder(MultipartFile[] files, int proId) {

        MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<>();

        // Add each file to the request as a ByteArrayResource.
        for (MultipartFile file : files) {
            try {
                // Convert the MultipartFile to a ByteArrayResource.
                ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };

                // Create HttpHeaders with the necessary content type.
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                // Create an HttpEntity with the ByteArrayResource and headers.
                HttpEntity<ByteArrayResource> fileEntity = new HttpEntity<>(fileResource, headers);

                // Add the file as a part of the MultiValueMap.
                requestParams.add("files", fileEntity);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception as needed.
            }
        }

        // Send the multipart request using RestTemplate.
        restTemplate.postForLocation("http://product-service/images/saveImagesToFolder/" + proId,
                requestParams);

        return true;
    }


    @Override
    public boolean updateImages(ProductImageRequest images) {

        int no = images.getImageNo();
        ProductImagesRequest img_real = getProductImages(images.getProductId());
        if (no == 1) {
            img_real.setMainImage(null);
        } else if (no == 2) {
            img_real.setImage2(null);
        } else if (no == 3) {
            img_real.setImage3(null);
        } else if (no == 4) {
            img_real.setImage4(null);
        } else if (no == 5) {
            img_real.setImage5(null);
        }

        boolean status = true;
        try {
            restTemplate.put("http://product-service/images/updateImages/" + images.getProductId() + "/" + images.getImageName(), img_real);
        } catch (Exception ex) {
            System.out.println("Images not Updated.");
            System.out.println(ex);
            status = false;
        }
        return status;
    }

}
