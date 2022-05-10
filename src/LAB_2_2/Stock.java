package LAB_2_2;

import LAB_2_2.Exceptions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
            if(existedGroup.equals(group)) {
                for(Product existedProduct : existedGroup) {
                    if(existedProduct.equals(product)) throw new ProductAlreadyExistException();
                }
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
            if(group.getName().matches("\\.*" + groupNamePart + "\\.*")) {
                group.setVisible(true);
            }
            else {
                group.setVisible(false);
            }
            for(Product product : group) {
                if(product.getName().matches("\\.*" + productNamePart + "\\.*")) {
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
    public void editProduct(Product product, Product newProduct) throws ProductNotExistException {
        for(Group group : this.groups) {
            for(Product existedProduct : group) {
                if(existedProduct.equals(product)) {
                    group.remove(existedProduct);
                    group.add(newProduct);
                    sortProducts(group);
                    return;
                }
            }
        }
        throw new ProductNotExistException();
    }

    @Override
    public void editGroup(String groupName, String newGroup) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) {
                group.setName(newGroup);
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
            boolean isProductExist = true;
            for(Group group : this.groups) {
                if(group.contains(products.get(i))) {
                    products.get(i).delta(deltas.get((i)));
                    isProductExist = false;
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

    /*@Override
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
            if(existedGroup.equals(group)) {
                for(Product existedProduct : existedGroup.getAllProducts()) {
                    if(existedProduct.equals(product)) throw new ProductAlreadyExistException();
                }
                existedGroup.getAllProducts().add(product);
                sortProducts(existedGroup);
                return;
            }
        }
        throw new GroupNotExistException();
    }

    @Override
    public ArrayList<Group> getStockByFilter(String groupNamePart, String productNamePart) {
        for(Group group : this.groups) {
            if(group.getName().matches("\\." + groupNamePart + "\\.")) {
                group.setVisible(true);
            }
            else {
                group.setVisible(false);
            }
            for(Product product : group.getAllProducts()) {
                if(product.getName().matches("\\." + productNamePart + "\\.")) {
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
    public void editProduct(Product product, Product newProduct) throws ProductNotExistException {
        for(Group group : this.groups) {
            for(Product existedProduct : group.getAllProducts()) {
                if(existedProduct.equals(product)) {
                    group.getAllProducts().remove(existedProduct);
                    group.getAllProducts().add(newProduct);
                    sortProducts(group);
                    return;
                }
            }
        }
        throw new ProductNotExistException();
    }

    @Override
    public void editGroup(String groupName, String newGroup) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) {
                group.setName(newGroup);
                sortGroups();
                return;
            }
        }
        throw new GroupNotExistException();
    }

    @Override
    public void removeProduct(Product product) throws ProductNotExistException {
        for(Group group : this.groups) {
            if(group.getAllProducts().contains(product)) {
                group.getAllProducts().remove(product);
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
            boolean isProductExist = true;
            for(Group group : this.groups) {
                if(group.getAllProducts().contains(products.get(i))) {
                    products.get(i).delta(deltas.get((i)));
                    isProductExist = false;
                    break;
                }
            }
            if(!isProductExist) throw new ProductNotExistException();
        }
    }

    @Override
    public boolean load(File file) {
        return false;
    }

    @Override
    public boolean save(File file) {
        return false;
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
        Collections.sort(group.getAllProducts(), new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Stock.this.compare(o1.getName(), o2.getName());
            }
        });
    }*/

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



    /*public double getTotalCost() {
        double totalCost = 0;
        for(Group group : this.groups) {
            totalCost += group.getTotalCost();
        }
        return totalCost;
    }

    public ArrayList<Group> getAllGroups() {
        return this.groups;
    }

    public ArrayList<Group> getGroups(String groupNamePart) {
        ArrayList<Group> res = new ArrayList<>();
        for(Group group : this.groups) {
            if(group.getName().matches("\\." + groupNamePart + "\\.")) {
                res.add(group);
            }
        }
        return res;
    }

    public Group getGroup(String groupName) throws GroupNotExistException {
        for(Group group : this.groups) {
            if(group.getName().equals(groupName)) return group;
        }
        throw new GroupNotExistException();
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> res = new ArrayList<>();
        for(Group group : this.groups) {
            res.addAll(group.getAllProducts());
        }
        return res;
    }

    public ArrayList<Product> getProducts(String productNamePart) {
        ArrayList<Product> res = new ArrayList<>();
        for(Group group : this.groups) {
            res.addAll(group.getProducts(productNamePart));
        }
        return res;
    }

    public Product getProduct(String productName) throws ProductNotExistException {
        Product res = null;
        for(Group group : this.groups) {
            try {
                res = group.getProduct(productName)
            }
            catch (ProductNotExistException e){

            }
        }
        if(res == null) throw new ProductNotExistException();
        return res;
    }

    public void addProduct(Product product, Group group) throws ProductAlreadyExistException {

    }

    //TODO

    public void editProduct(String productName, String newProductName, String newProductDescription, String newProductProducer, double newProductPrice, int newProductQuantity) throws ProductNotExistException, NegativePriceException, NegativeQuantityException {
        Product product = getProduct(productName);
        if(!(newProductName == null)) product.setName(newProductName);
        if(!(newProductDescription == null)) product.setDescription(newProductDescription);
        if(!(newProductProducer == null)) product.setProducer(newProductProducer);
        if(newProductPrice < 0) product.setPrice(newProductPrice);
        if(newProductQuantity < 0) product.setQuantity(newProductQuantity);
    }

    public void editProduct(Product product, Product newProduct) throws ProductNotExistException {
        Product res = null;
        for(Group group : this.groups) {
            try {
                res = group.getProduct(product.getName());
                group.editProduct(product, newProduct);
            }
            catch (ProductNotExistException e){

            }
        }
        if(res == null) throw new ProductNotExistException();
    }

    public void removeProduct(Product product) throws ProductNotExistException {
        //if(!this.products.contains(product))throw new ProductNotExistException();
        //this.products.remove(product);
    }*/

}
