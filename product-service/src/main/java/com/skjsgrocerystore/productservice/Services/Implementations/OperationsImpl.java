package com.skjsgrocerystore.productservice.Services.Implementations;

import com.skjsgrocerystore.productservice.DAO.ProductDetailRepository;
import com.skjsgrocerystore.productservice.DTO.Operations.*;
import com.skjsgrocerystore.productservice.Entities.ProductDetail;
import com.skjsgrocerystore.productservice.Services.Operations_ops;
import com.skjsgrocerystore.productservice.Services.Points_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OperationsImpl implements Operations_ops {

    private final ProductDetailRepository productDetailRepo;
    private final Points_ops pointsOps;

    @Override
    public ProductDetailResponse getProduct(int id) {
        Optional<ProductDetail> data = productDetailRepo.findById(id);
        ProductDetailResponse response = null;
        if (data.isPresent()) {
            response = PrdctDtlToPrdctDtlRspns(data.get());
        }
        return response;
    }

    @Override
    public void updateProduct(int id, ProductDetailResponse response) {

        ProductDetail pro = prdctDtlRspnsToPrdctDtl(response);
        pro.setId(id);
        productDetailRepo.save(pro);
    }

    @Override
    public Integer getLatestProductId(ProductIdResponse response) {
        ProductDetail pro = productDetailRepo.findTopByBuserIdAndCat1IdAndCat2IdAndCat3IdOrderByProductIdDesc(
                response.getBuserId(), response.getCat1Id(), response.getCat2Id(), response.getCat3Id());
        return pro == null ? -1 : pro.getProductId();
    }

    @Override
    public Integer saveProductStep1(ProductStep1Response pro) {

        ProductDetail product = ProductDetail.builder()
                .productId(pro.getProductId())
                .buserId(pro.getBuserId())
                .cat1Id(pro.getCat1Id())
                .cat2Id(pro.getCat2Id())
                .cat3Id(pro.getCat3Id())
                .productName(pro.getProductName())
                .brandName(pro.getBrandName())
                .status(pro.getStatus())
                .dateTimeCreated(pro.getDateTimeCreated())
                .dateTimeModified(pro.getDateTimeModified()).build();

        ProductDetail response = productDetailRepo.save(product);
        return response.getId();
    }

    @Override
    public Integer updateProductStep1(ProductStep1Response pro) {

        Optional<ProductDetail> data = productDetailRepo.findById(pro.getId());
        if (data.isPresent()) {
            ProductDetail product = data.get();

            product.setProductName(pro.getProductName());
            product.setBrandName(pro.getBrandName());
            product.setDateTimeModified(pro.getDateTimeModified());

            ProductDetail response = productDetailRepo.save(product);
            return response.getId();
        }
        return -1;
    }

    @Override
    public Integer updateProductStep2(ProductStep2Response product) {
        Optional<ProductDetail> data = productDetailRepo.findById(product.getId());
        if (data.isPresent()) {
            ProductDetail updated_product = data.get();

            updated_product.setSellerSku(product.getSellerSku());
            updated_product.setYourPrice(product.getYourPrice());
            updated_product.setListPrice(product.getListPrice());
            updated_product.setSaleQty(product.getSaleQty());
            updated_product.setSaleUnit(product.getSaleUnit());
            updated_product.setOrderableQty(product.getOrderableQty());
            updated_product.setOrderableUnit(product.getOrderableUnit());
            updated_product.setMrp(product.getMrp());

            ProductDetail response = productDetailRepo.save(updated_product);
            return response.getId();
        }
        return -1;
    }

    @Override
    public Integer updateProductStep4(ProductStep4Response product) {

        Optional<ProductDetail> data = productDetailRepo.findById(product.getId());
        if (data.isPresent()) {
            ProductDetail updated_product = data.get();

            updated_product.setDescription(product.getDescription());
            updated_product.setStatus(1);

            ProductDetail response = productDetailRepo.save(updated_product);
            pointsOps.saveProductPoints(product.getPoints(), product.getId(), product.getPointIds());
            return response.getId();
        }
        return -1;
    }

    @Override
    public List<Object[]> getProductsByCategory3Id(int cat3id, int minLimit, int maxLimit) {
        return productDetailRepo.gettingProductBycat3id(cat3id, minLimit, maxLimit);
    }

    @Override
    public List<Object[]> getProductsBySearch(ProductSearchResponse response) {

        if (response.getSrchWithCtgry2() != null) {
            return productDetailRepo.getProductsbySearchAndcat2Id(response.getSrchWithKywrd()
                    , response.getSrchWithCtgry2(), response.getMinLimit(), response.getMaxLimit());
        } else {
            return productDetailRepo.getProductsbySearch(response.getSrchWithKywrd()
                    , response.getMinLimit(), response.getMaxLimit());
        }

    }

    @Override
    public List<Object[]> getProductsByCategory2Id(int cat2Id, int limit) {
        return productDetailRepo.gettingProductBycat2id(cat2Id, limit);
    }

    @Override
    public List<Object[]> getUniqueProductsByCategory3Id() {
        return productDetailRepo.getUniqueProductsOfcat3id();
    }

    @Override
    public List<Object[]> getProductDetailWithImage(int id) {
        return productDetailRepo.gettingProductDetailWithImage(id);
    }

    @Override
    public List<Object[]> gettingProductsData(int id) {
        return productDetailRepo.gettingProductsdata(id);
    }


    @Override
    public List<Object[]> gettingProductBycat2id(int cat2Id, int limit) {
        return productDetailRepo.gettingProductBycat2id(cat2Id, limit);
    }

    @Override
    public List<Object[]> gettingProductByTwocat2Ids(int cat2Id1, int cat2Id2, int limit) {
        return productDetailRepo.gettingProductBy2cat2id(cat2Id1, cat2Id2, limit);
    }

    @Override
    public List<Object[]> top5HighDiscountProducts() {
        return productDetailRepo.top5HighDiscountProducts();
    }

    public ProductDetailResponse PrdctDtlToPrdctDtlRspns(ProductDetail pro) {
        return ProductDetailResponse.builder()
                .id(pro.getId())
                .productId(pro.getProductId())
                .buserId(pro.getBuserId())
                .cat1Id(pro.getCat1Id())
                .cat2Id(pro.getCat2Id())
                .cat3Id(pro.getCat3Id())
                .productName(pro.getProductName())
                .brandName(pro.getBrandName())
                .status(pro.getStatus())
                .sellerSku(pro.getSellerSku())
                .yourPrice(pro.getYourPrice())
                .listPrice(pro.getListPrice())
                .saleQty(pro.getSaleQty())
                .saleUnit(pro.getSaleUnit())
                .orderableQty(pro.getOrderableQty())
                .orderableUnit(pro.getOrderableUnit())
                .mrp(pro.getMrp())
                .description(pro.getDescription())
                .dateTimeCreated(pro.getDateTimeCreated())
                .dateTimeModified(pro.getDateTimeModified()).build();
    }

    public ProductDetail prdctDtlRspnsToPrdctDtl(ProductDetailResponse pro) {
        return ProductDetail.builder()
                .id(pro.getId())
                .productId(pro.getProductId())
                .buserId(pro.getBuserId())
                .cat1Id(pro.getCat1Id())
                .cat2Id(pro.getCat2Id())
                .cat3Id(pro.getCat3Id())
                .productName(pro.getProductName())
                .brandName(pro.getBrandName())
                .status(pro.getStatus())
                .sellerSku(pro.getSellerSku())
                .yourPrice(pro.getYourPrice())
                .listPrice(pro.getListPrice())
                .saleQty(pro.getSaleQty())
                .saleUnit(pro.getSaleUnit())
                .orderableQty(pro.getOrderableQty())
                .orderableUnit(pro.getOrderableUnit())
                .mrp(pro.getMrp())
                .description(pro.getDescription())
                .dateTimeCreated(pro.getDateTimeCreated())
                .dateTimeModified(pro.getDateTimeModified()).build();
    }

}
