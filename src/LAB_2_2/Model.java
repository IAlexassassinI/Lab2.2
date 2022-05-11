package LAB_2_2;

import LAB_2_2.Exceptions.*;

import java.io.File;
import java.util.ArrayList;

public interface Model {

    double getTotalCost();
    double getTotalCost(String groupName)throws GroupNotExistException;

    void addGroup(Group group) throws GroupAlreadyExistException;
    void addProduct(Product product, Group group) throws ProductAlreadyExistException, GroupNotExistException;

    ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart);

    void editProduct(Product product, Product newProductName) throws ProductNotExistException, ProductAlreadyExistException;
    void editGroup(String groupName, String newGroupName) throws GroupNotExistException, GroupAlreadyExistException;

    void removeProduct(Product product) throws ProductNotExistException;
    void removeGroup(String groupName) throws GroupNotExistException;

    void delta(ArrayList<Product> products, ArrayList<Integer> deltas) throws ProductNotExistException, SellMoreThenInStockException, ProductsAndDeltasArraysNotMatchException;

    boolean load(File file);
    boolean save(File file);
}
