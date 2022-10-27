package service;

import entity.Addresses;
import entity.Analysis;
import entity.Products;
import entity.User;

public interface Service {
    boolean registerUser(User user);
    byte loginUser(User user);
    Object getProducts ();
    Object getUsers();
    Object getAlternatives();
    Object getCategories();
    String getUserInfo(String login);
    boolean setCategory(String category);
    boolean setProduct(Products product);
    boolean addAnalysis(Analysis analysis);
    boolean deleteProduct(String productName);
    boolean updateProduct(Products product);
    boolean deleteUser(String userLogin);
    Object getAnalysis();
    Object getAddresses();
    boolean setAddress(Addresses address);
    boolean deleteAddress(String addressDel);
    Object getFavouriteProducts(String login);
    boolean deleteFavouriteProduct(String login);
    boolean setFavProduct(Products product);
}

