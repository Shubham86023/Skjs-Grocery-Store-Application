package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImagesRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.*;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductImage_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductImpl implements Product_ops {

    private final ProductImage_ops productImageOps;
    private final ProductCategory_ops productCategoryOps;

    private final HttpSession session;
    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<List<Object[]>> responseType
            = new ParameterizedTypeReference<>() {
    };

    private List<Object[]> data = new ArrayList<>();

    @Override
    public Map<String, Object> commonData() {
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("bis_login", true);
        attributes.put("is_login", false);
        attributes.put("buser_name", session.getAttribute("buser_name"));
        attributes.put("categories1", productCategoryOps.cat2List());
        attributes.put("buserId", session.getAttribute("buser_id"));

        return attributes;
    }

    @Override
    public ProductDetailRequest getProduct(int id) {

        ProductDetailRequest product = new ProductDetailRequest();
        try {
            //1.
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<ProductDetailRequest> pro = restTemplate.exchange("http://product-service/operations/getProduct/" + id,
                    HttpMethod.GET, entity, ProductDetailRequest.class);

            //2. ProductDetail pro  = restTemplate.getForObject("http://product-service/operations/getProduct/"+ id, ProductDetail.class);

            if (pro.getStatusCode() == HttpStatus.OK) {
                product = pro.getBody();
            }

        } catch (Exception ex) {
            System.out.println("Unable to get product");
            System.out.println(ex);
        }

        return product;
    }


    @Override
    public void updateProduct(ProductDetailRequest product) {
        if (product.getId() != 0) {

            //1. EXCHANGE METHOD RETURNS RESPONSEENTITY< class which we mention >
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            HttpEntity<ProductDetail> requestEntity = new HttpEntity<>(product, headers);
//
//            ResponseEntity<ProductDetail> pro = restTemplate.exchange("http://product-service/operations/updateProduct/" + product.getId(),
//                    HttpMethod.PUT, requestEntity, ProductDetail.class);


            //2. PUT METHOD RETURNS VOID
            restTemplate.put("http://product-service/operations/updateProduct/" + product.getId(), product);

        }
    }

    @Override
    public String getProductDescription(int id) {
        ProductDetailRequest pro = getProduct(id);
        return pro.getDescription();
    }


    @Override
    public int saveProductStep1(ProductStep1Request step1Data) {

        Integer buserId = (Integer) session.getAttribute("buser_id");

        Integer pro_id = getProductId(buserId, step1Data.getCat1Id(), step1Data.getCat2Id(), step1Data.getCat3Id());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        step1Data.setProductId(pro_id);
        step1Data.setBuserId(buserId);
        step1Data.setStatus(0);
        step1Data.setDateTimeModified(now.format(formatter));
        step1Data.setDateTimeCreated(now.format(formatter));
        Integer id = 0;
        try {
            id = restTemplate.postForObject("http://product-service/operations/saveProductStep1", step1Data, Integer.class);
        } catch (Exception ex) {
            System.out.println("Product Step 1 Data Not Saved.");
            System.out.println(ex);
        }
        return id;
    }


    @Override
    public boolean updateProductStep1(int id, ProductStep1Request step1Data) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        step1Data.setDateTimeModified(now.format(formatter));
        step1Data.setId(id);

        boolean status = false;
        try {
            restTemplate.put("http://product-service/operations/updateProductStep1/" + id, step1Data);
            status = true;
        } catch (Exception ex) {
            System.out.println("Product Step 1 Data Not Updated.");
            System.out.println(ex);
        }
        return status;
    }


    @Override
    public boolean saveProductStep2(int id, ProductStep2Request step2Data) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        step2Data.setDateTimeModified(now.format(formatter));
        step2Data.setId(id);

        boolean status = false;
        try {
            restTemplate.put("http://product-service/operations/updateProductStep2/" + id, step2Data);
            status = true;
        } catch (Exception ex) {
            System.out.println("Product Step 2 Data Not Updated.");
            System.out.println(ex);
        }
        return status;

    }


    @Override
    public boolean saveProductStep3(MultipartFile[] images, int id) {

        ProductImagesRequest img = new ProductImagesRequest();
        ProductImagesRequest img_real = productImageOps.getProductImages(id);
        if (img_real != null) {
            img = img_real;
        }
        img.setProId(id);
        int i = 1;
        for (MultipartFile file : images) {
            if (!file.isEmpty() && file.getOriginalFilename() != null) {
                String extension = i + ".";
                String onefile = StringUtils.cleanPath(file.getOriginalFilename());

                int j = onefile.lastIndexOf('.');
                if (j > 0) {
                    extension += onefile.substring(j + 1);
                }
                if (i == 1) {
                    img.setMainImage(extension);
                } else if (i == 2) {
                    img.setImage2(extension);
                } else if (i == 3) {
                    img.setImage3(extension);
                } else if (i == 4) {
                    img.setImage4(extension);
                } else if (i == 5) {
                    img.setImage5(extension);
                }
                i++;
            }
        }
        productImageOps.saveImagesToDB(img);
        return productImageOps.saveImagesToFolder(images, id);
    }

    @Override
    public boolean saveProductStep4(ProductStep4Request step4Data, int id) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        step4Data.setDateTimeModified(now.format(formatter));
        step4Data.setId(id);

        boolean status = false;
        try {
            restTemplate.put("http://product-service/operations/updateProductStep4/" + id, step4Data);
            status = true;
        } catch (Exception ex) {
            System.out.println("Product Step 4 Data Not Updated.");
            System.out.println(ex);
        }
        return status;

    }


    @Override
    public Integer getProductId(Integer buserId, Integer cat1Id, Integer cat2Id, Integer cat3Id) {

        ProductIdRequest idRequest = ProductIdRequest.builder()
                .buserId(buserId)
                .cat1Id(cat1Id)
                .cat2Id(cat2Id)
                .cat3Id(cat3Id).build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<ProductIdRequest> requestEntity = new HttpEntity<>(idRequest, headers);

        Integer id = restTemplate.exchange("http://product-service/operations/getLatestProductId",
                HttpMethod.POST, requestEntity, Integer.class).getBody();

        if (id != null && id != -1) {
            id += 1;
        } else {
            String concatenated_id = String.valueOf(buserId) + cat1Id + cat2Id
                    + cat3Id + "1";
            id = Integer.parseInt(concatenated_id);
        }
        return id;
    }


    @Override
    public List<Object[]> getProductsByCategory3Id(int cat3id, int minLimit, int maxLimit) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/getProductsByCategory3Id/" + cat3id + "/" + minLimit + "/" + maxLimit,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getProductsByCategory3Id");
            System.out.println(ex);
        }
        return data;

    }


    @Override
    public List<Object[]> getProductsBySearchKeywordOrCategory2Id(ProductSearchRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<ProductSearchRequest> requestEntity = new HttpEntity<>(request, headers);

            data = restTemplate.exchange(
                    "http://product-service/operations/getProductsBySearch",
                    HttpMethod.POST, requestEntity, responseType).getBody();

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Unable to get Data :- getProductsBySearchKeywordOrCategory2Id");
        }
        return data;
    }


    @Override
    public List<Object[]> getProductsByCategory2Id(int cat2Id, int limit) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/getProductsByCategory2Id/" + cat2Id + "/" + limit,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getProductsByCategory2Id");
            System.out.println(ex);
        }
        return data;
    }


    @Override
    public List<Object[]> getUniqueProductsByCategory3Id() {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/getUniqueProductsByCategory3Id",
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getUniqueProductsByCategory3Id");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public List<Object[]> getProductDetailWithImage(int productId) {

        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/getProductDetailWithImage/" + productId,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getProductDetailWithImage");
            System.out.println(ex);
        }
        return data;
    }

}


