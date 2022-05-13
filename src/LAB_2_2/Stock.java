package LAB_2_2;

import LAB_2_2.Exceptions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 */

public class Stock implements Model {

    private ArrayList<Group> groups;

    public Stock() {
        this.groups = new ArrayList<>();
    }

    @Override
    public double getTotalCost() {
        double res = 0;
        for(Group group : this.groups) {
            res += group.getTotalCost();
        }
        return res;
    }

    @Override
    public double getTotalCost(String groupName) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName))return group.getTotalCost();
        }
        throw new GroupNotExistException();
    }

    @Override
    public void addGroup(Group group) throws GroupAlreadyExistException {
        if(this.groups.contains(group)) throw new GroupAlreadyExistException();
        this.groups.add(group);
        sortGroups();
    }

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

    @Override
    public ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart) {
        for(Group group : this.groups) {
            if(group.getName().matches(".*" + groupNamePart + ".*")) {
                group.setVisible(true);
            }
            else {
                group.setVisible(false);
            }
            for(Product product : group) {
                if(product.getName().matches(".*" + productNamePart + ".*")) {
                    product.setVisible(true);
                }
                else {
                    product.setVisible(false);
                }
            }
        }
        return this.groups;
    }

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

    @Override
    public void editGroup(String groupName, String newGroupName) throws GroupNotExistException, GroupAlreadyExistException {
        if(!groupName.equals(newGroupName) && this.groups.contains(new Group(newGroupName))) throw new GroupAlreadyExistException();
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) {
                group.setName(newGroupName);
                sortGroups();
                return;
            }
        }
        throw new GroupNotExistException();
    }

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

    @Override
    public boolean load(File file) {
        DataFile dataFile = new DataFile(file, this);
        return dataFile.loadAll();
    }

    @Override
    public boolean save(File file) {
        DataFile dataFile = new DataFile(file, this);
        return dataFile.saveAll();
    }

    public void sort() {
        sortGroups();
        for(Group group : this.groups) {
            sortProducts(group);
        }
    }

    public void sortGroups() {
        Collections.sort(this.groups, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return Stock.this.compare(o1.getName(), o2.getName());
            }
        });
    }

    public void sortProducts(Group group) {
        Collections.sort(group, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Stock.this.compare(o1.getName(), o2.getName());
            }
        });
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

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
