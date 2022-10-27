package dao;
import entity.Addresses;

public interface AddressesDao {
    Object getAddressesObject();
    boolean addAddress(Addresses address);
    boolean deleteAddress(String addressDel);
}
