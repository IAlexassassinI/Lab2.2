package LAB_2_2;

import LAB_2_2.Exceptions.*;
import LAB_2_2.Model.Group;
import LAB_2_2.Model.Model;
import LAB_2_2.Model.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * class that is responsible for Stock and implements interface Model
 */

public class Stock implements Model {

    private ArrayList<Group> groups;

    /**
     * constructor
     */

    public Stock() {
        this.groups = new ArrayList<>();
    }

    /**
     * returns total cost of stock
     *
     * @return total cost of stock
     */

    @Override
    public double getTotalCost() {
        double res = 0;
        for(Group group : this.groups) {
            res += group.getTotalCost();
        }
        return res;
    }

    /**
     * returns total cost of group
     *
     * @param groupName name of group
     * @return total cost of group
     * @throws GroupNotExistException
     */

    @Override
    public double getTotalCost(String groupName) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName))return group.getTotalCost();
        }
        throw new GroupNotExistException();
    }

    /**
     * adds new group to stock
     *
     * @param group group  which need to add
     * @throws GroupAlreadyExistException
     */

    @Override
    public void addGroup(Group group) throws GroupAlreadyExistException {
        if(this.groups.contains(group)) throw new GroupAlreadyExistException();
        this.groups.add(group);
        sortGroups();
    }

    /**
     * adds new product to group
     *
     * @param product new Product
     * @param group product's group
     * @throws ProductAlreadyExistException
     * @throws GroupNotExistException
     */

    @Override
    public void addProduct(Product product, Group group) throws ProductAlreadyExistException, GroupNotExistException {
        for(Group existedGroup : this.groups) {
            if(existedGroup.contains(product)) throw new ProductAlreadyExistException();
        }
        for(Group existedGroup : this.groups) {
            if(existedGroup.equals(group)) {
                existedGroup.add(product);
                sortProducts(existedGroup);
                return;
            }
        }
        throw new GroupNotExistException();
    }

    /**
     *returns stock by filter
     *
     * @param groupNamePart possible part of group's name
     * @param productNamePart possible part of product's name
     * @return stock by filter
     */

    @Override
    public ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart) {

        for(Group group : this.groups) {
            if(groupNamePart == null || groupNamePart.isEmpty()) group.setVisible(true);
            else if(group.getName().matches(".*" + groupNamePart + ".*")) {
                group.setVisible(true);
            }
            else {
                group.setVisible(false);
            }
            boolean isProductsVisible = false;
            for(Product product : group) {
                if(productNamePart == null || productNamePart.isEmpty()) {
                    isProductsVisible = true;
                    product.setVisible(true);
                    continue;
                }
                if(product.getName().matches(".*" + productNamePart + ".*")) {
                    isProductsVisible = true;
                    product.setVisible(true);
                }
                else {
                    product.setVisible(false);
                }
            }
            if(!isProductsVisible && !(productNamePart == null || productNamePart.isEmpty()))group.setVisible(false);
        }
        return this.groups;
    }

    /**
     * edits specific product
     *
     * @param product product to edit
     * @param newProduct new params of this product
     * @throws ProductNotExistException
     * @throws ProductAlreadyExistException
     */

    @Override
    public void editProduct(Product product, Product newProduct) throws ProductNotExistException, ProductAlreadyExistException {
        if(!product.equals(newProduct)) {
            for(Group group : this.groups) {
                if(group.contains(newProduct)) throw new ProductAlreadyExistException();
            }
        }
        for(Group group : this.groups) {
            for(Product existedProduct : group) {
                if(existedProduct.equals(product)) {
                    group.remove(existedProduct);
                    break;
                }
            }
        }
        for(Group group : this.groups) {
            if(group.equals(newProduct.getGroup())) {
                group.add(newProduct);
                sortProducts(group);
                return;
            }
        }
        throw new ProductNotExistException();
    }

    /**
     * edits specific group
     *
     * @param groupName name of group to edit
     * @param newGroupName new group name
     * @param newGroupDescription new description of group
     * @throws GroupNotExistException
     * @throws GroupAlreadyExistException
     */

    @Override
    public void editGroup(String groupName, String newGroupName, String newGroupDescription) throws GroupNotExistException, GroupAlreadyExistException {
        if(!groupName.equals(newGroupName) && this.groups.contains(new Group(newGroupName, newGroupDescription))) throw new GroupAlreadyExistException();
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) {
                group.setName(newGroupName);
                group.setDescription(newGroupDescription);
                sortGroups();
                return;
            }
        }
        throw new GroupNotExistException();
    }

    /**
     * removes specific product
     *
     * @param product product to remove
     * @throws ProductNotExistException
     */

    @Override
    public void removeProduct(Product product) throws ProductNotExistException {
        for(Group group : this.groups) {
            if(group.contains(product)) {
                group.remove(product);
                return;
            }
        }
        throw new ProductNotExistException();
    }

    /**
     * removes specific group
     *
     * @param groupName name of group to remove
     * @throws GroupNotExistException
     */

    @Override
    public void removeGroup(String groupName) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) {
                this.groups.remove(group);
                return;
            }
        }
        throw new GroupNotExistException();
    }

    /**
     * changes quantities of products by deltas
     *
     * @param products list of products to change
     * @param deltas list of deltas of products
     * @throws ProductNotExistException
     * @throws SellMoreThenInStockException
     * @throws ProductsAndDeltasArraysNotMatchException
     */

    @Override
    public void delta(ArrayList<Product> products, ArrayList<Integer> deltas) throws ProductNotExistException, SellMoreThenInStockException, ProductsAndDeltasArraysNotMatchException {
        if(products == null || deltas == null || products.size() != deltas.size()) throw new ProductsAndDeltasArraysNotMatchException();
        for(int i = 0; i < products.size(); i++) {
            boolean isProductExist = false;
            for(Group group : this.groups) {
                if(group.contains(products.get(i))) {
                    products.get(i).delta(deltas.get((i)));
                    isProductExist = true;
                    break;
                }
            }
            if(!isProductExist) throw new ProductNotExistException();
        }
    }

    /**
     * loads stock from file or directory
     *
     * @param file directory or file to load from
     * @return is loaded or not
     */

    @Override
    public boolean load(File file) {
        DataFile dataFile = new DataFile(file, this);
        return dataFile.loadAll();
    }

    /**
     * saves stock to file or directory
     *
     * @param file directory or file to save to
     * @return is saved or not
     */

    @Override
    public boolean save(File file) {
        DataFile dataFile = new DataFile(file, this);
        return dataFile.saveAll();
    }

    /**
     * sorts stock
     */

    public void sort() {
        sortGroups();
        for(Group group : this.groups) {
            sortProducts(group);
        }
    }

    /**
     * sort groups by names
     */

    public void sortGroups() {
        Collections.sort(this.groups, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return Stock.this.compare(o1.getName(), o2.getName());
            }
        });
    }

    /**
     * sorts products by names in specific group
     *
     * @param group group in which sort products
     */

    public void sortProducts(Group group) {
        Collections.sort(group, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Stock.this.compare(o1.getName(), o2.getName());
            }
        });
    }

    /**
     * returns list of groups
     *
     * @return list of groups
     */

    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * sets specific group
     *
     * @param groups list of groups
     */

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    /**
     * compares two strings
     *
     * @param s1 first string
     * @param s2 second strings
     * @return result of comparing
     */

    private int compare(String s1, String s2) {
        s1 = s1.replaceAll("\'", "");
        s2 = s2.replaceAll("\'", "");
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int minLength;
        if(chars1.length > chars2.length)minLength = chars2.length;
        else minLength = chars1.length;
        for(int i = 0; i < minLength; i++){
            if(Character.toLowerCase(chars1[i]) == Character.toLowerCase(chars2[i])){
                continue;
            }
            if(Character.toLowerCase(chars1[i]) == 'ґ') {
                if (Character.toLowerCase(chars2[i]) != 1075) chars1[i] = 1075;
                else return 1;
            }
            else if(Character.toLowerCase(chars1[i]) == 'є'){
                if (Character.toLowerCase(chars2[i]) != 1077) chars1[i] = 1077;
                else return 1;
            }
            else if(Character.toLowerCase(chars1[i]) == 'і'){
                if (Character.toLowerCase(chars2[i]) != 1080) chars1[i] = 1080;
                else return 1;
            }
            else if(Character.toLowerCase(chars1[i]) == 'ї'){
                if (Character.toLowerCase(chars2[i]) != 1081) chars1[i] = 1081;
                else return -1;
            }
            if(Character.toLowerCase(chars2[i]) == 'ґ') {
                if (Character.toLowerCase(chars1[i]) != 1075) chars2[i] = 1075;
                else return -1;
            }
            else if(Character.toLowerCase(chars2[i]) == 'є'){
                if (Character.toLowerCase(chars1[i]) != 1077) chars2[i] = 1077;
                else return -1;
            }
            else if(Character.toLowerCase(chars2[i]) == 'і'){
                if (Character.toLowerCase(chars1[i]) != 1080) chars2[i] = 1080;
                else return -1;
            }
            else if(Character.toLowerCase(chars2[i]) == 'ї'){
                if (Character.toLowerCase(chars1[i]) != 1081) chars2[i] = 1081;
                else return 1;
            }
            return Character.toLowerCase(chars1[i]) - Character.toLowerCase(chars2[i]);
        }
        if(chars1.length > chars2.length)return 1;
        if(chars1.length < chars2.length)return -1;
        return 0;
    }

}
