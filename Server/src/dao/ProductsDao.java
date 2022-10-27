package dao;

import entity.Products;

public interface ProductsDao {
    Object getProductsObject();
    Object getAlternativesObject();
    Object getCategoriesObject();
    boolean addCategory(String category);
    boolean addProduct(Products product);
    boolean deleteProductName(String productName);
    boolean updateProductInfo(Products product);
    Object getFavouriteProductsObject(String login);
    boolean deleteFavProducts(String login);
    boolean addProdToFavourites(Products product);
}
