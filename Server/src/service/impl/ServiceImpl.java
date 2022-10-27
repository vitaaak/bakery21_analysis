package service.impl;

import dao.AddressesDao;
import dao.AnalysisDao;
import dao.ProductsDao;
import dao.UserDao;
import dao.impl.AddressesDaoImpl;
import dao.impl.AnalysisDaoImpl;
import dao.impl.ProductsDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Addresses;
import entity.Analysis;
import entity.Products;
import entity.User;
import service.Service;

public class ServiceImpl implements Service {
    private static final ServiceImpl INSTANCE = new ServiceImpl();

    public static ServiceImpl getInstance() {
        return INSTANCE;
    }

    private UserDao userDao = UserDaoImpl.getInstance();
    private ProductsDao productsDao = ProductsDaoImpl.getInstance();
    private AnalysisDao analysisDao = AnalysisDaoImpl.getInstance();
    private AddressesDao addressesDao = AddressesDaoImpl.getInstance();


    @Override
    public boolean registerUser(User user) {
        return userDao.add(user);
    }

    @Override
    public byte loginUser(User user) {
        return userDao.loginSuccess(user);
    }

    @Override
    public Object getProducts() {
        return productsDao.getProductsObject();
    }

    @Override
    public Object getUsers() {
        return userDao.getUsersObject();
    }

    @Override
    public Object getAlternatives() {
        return productsDao.getAlternativesObject();
    }

    @Override
    public Object getCategories() {
        return productsDao.getCategoriesObject();
    }

    @Override
    public String getUserInfo(String login) {
        return userDao.getUser(login);
    }

    @Override
    public boolean setCategory(String category) {
        return productsDao.addCategory(category);
    }

    @Override
    public boolean setProduct(Products product) {
        return productsDao.addProduct(product);
    }

    @Override
    public boolean addAnalysis(Analysis analysis) {
        return analysisDao.addAnalysisResults(analysis);
    }

    @Override
    public boolean deleteProduct(String productName) {
        return productsDao.deleteProductName(productName);
    }

    @Override
    public boolean updateProduct(Products product) {
        return productsDao.updateProductInfo(product);
    }

    @Override
    public boolean deleteUser(String userLogin) {
        return userDao.deleteUserName(userLogin);
    }

    @Override
    public Object getAnalysis() { return analysisDao.getAnalysisObject(); }

    @Override
    public Object getAddresses() { return addressesDao.getAddressesObject(); }

    @Override
    public boolean setAddress(Addresses address) {
        return addressesDao.addAddress(address);
    }

    @Override
    public boolean deleteAddress(String addressDel) { return  addressesDao.deleteAddress (addressDel);}

    @Override
    public Object getFavouriteProducts(String login) { return productsDao.getFavouriteProductsObject(login);  }

    @Override
    public boolean deleteFavouriteProduct(String login) { return  productsDao.deleteFavProducts(login);}

    @Override
    public boolean setFavProduct(Products product) { return productsDao.addProdToFavourites(product); }


}