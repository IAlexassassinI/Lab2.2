package LAB_2_2;

import Exceptions.*;

import java.util.ArrayList;

public interface Model {

    double getTotalCost();
    double getTotalCost(String groupName);

    void addGroup(Group group) throws GroupAlreadyExistException;
    void addProduct(Product product, Group group) throws ProductAlreadyExistException, GroupNotExistException;

    ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart);

    void editProduct(Product product, Product newProduct) throws ProductNotExistException;
    void editGroup(String groupName, String newGroup) throws GroupNotExistException;

    void removeProduct(Product product) throws ProductNotExistException;
    void removeGroup(String groupName) throws GroupNotExistException;

    void delta(ArrayList<Product> products, ArrayList<Integer> deltas) throws ProductNotExistException, SellMoreThenInStockException, ProductsAndDeltasArraysNotMatchException;

}
