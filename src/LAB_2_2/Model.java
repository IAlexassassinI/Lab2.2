package LAB_2_2;

import LAB_2_2.Exceptions.*;

import java.io.File;
import java.util.ArrayList;

/**
 * interface that is responsible for model methods
 */

public interface Model {

    /**
     * returns total cost of stock
     *
     * @return total cost of stock
     */

    double getTotalCost();

    /**
     * returns total cost of group
     *
     * @param groupName name of group
     * @return total cost of group
     * @throws GroupNotExistException
     */

    double getTotalCost(String groupName)throws GroupNotExistException;

    /**
     * adds new group to stock
     *
     * @param group group  which need to add
     * @throws GroupAlreadyExistException
     */

    void addGroup(Group group) throws GroupAlreadyExistException;

    /**
     * adds new product to group
     *
     * @param product new Product
     * @param group product's group
     * @throws ProductAlreadyExistException
     * @throws GroupNotExistException
     */

    void addProduct(Product product, Group group) throws ProductAlreadyExistException, GroupNotExistException;

    /**
     *returns stock by filter
     *
     * @param groupNamePart possible part of group's name
     * @param productNamePart possible part of product's name
     * @return stock by filter
     */

    ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart);

    /**
     * edits specific product
     *
     * @param product product to edit
     * @param newProduct new params of this product
     * @throws ProductNotExistException
     * @throws ProductAlreadyExistException
     */

    void editProduct(Product product, Product newProduct) throws ProductNotExistException, ProductAlreadyExistException;

    /**
     * edits specific group
     *
     * @param groupName name of group to edit
     * @param newGroupName new group name
     * @throws GroupNotExistException
     * @throws GroupAlreadyExistException
     */

    void editGroup(String groupName, String newGroupName) throws GroupNotExistException, GroupAlreadyExistException;

    /**
     * removes specific product
     *
     * @param product product to remove
     * @throws ProductNotExistException
     */

    void removeProduct(Product product) throws ProductNotExistException;

    /**
     * removes specific group
     *
     * @param groupName name of group to remove
     * @throws GroupNotExistException
     */

    void removeGroup(String groupName) throws GroupNotExistException;

    /**
     * changes quantities of products by deltas
     *
     * @param products list of products to change
     * @param deltas list of deltas of products
     * @throws ProductNotExistException
     * @throws SellMoreThenInStockException
     * @throws ProductsAndDeltasArraysNotMatchException
     */

    void delta(ArrayList<Product> products, ArrayList<Integer> deltas) throws ProductNotExistException, SellMoreThenInStockException, ProductsAndDeltasArraysNotMatchException;

    /**
     * loads stock from file or directory
     *
     * @param file directory or file to load from
     * @return is loaded or not
     */

    boolean load(File file);

    /**
     * saves stock to file or directory
     *
     * @param file directory or file to save to
     * @return is saved or not
     */

    boolean save(File file);
}
