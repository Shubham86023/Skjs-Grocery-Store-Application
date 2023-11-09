package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;

import java.util.List;

public interface ProductsForHomepage_ops {

    List<Object[]> gettingProductBycat2id(int cat2Id, int limit);

    List<Object[]> gettingProductByTwocat2Ids(int cat2Id1, int cat2Id2, int limit);

    List<Object[]> top5HighDiscountProducts();

    List<Object[]> top10HighRatedProducts();
}
